package a1.server;

import java.nio.channels.SocketChannel;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.CTS_Proposal;
import a1.common.message.CTS_ProposalResponse;
import a1.common.message.Message;
import a1.common.message.STC_ProposalAcceptRequest;
import a1.common.message.STC_ProposalExecute;
import a1.util.Util;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import a1.common.Communicator;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;

public class ServerCommunicator {
	
	public static boolean waitingForAccepts = false; 
	public static int numAcceptsReceived = 0;
	public static int numTargetAccepts = 0;  
	
	private NIOServerCommunicator	nioCommunicator; 
	private RMIServerCommunicator 	rmiCommunicator;  
	private GIPCServerCommunicator   gipcCommunicator; 

	public ServerCommunicator(SimulationServer server, int nioPort, int gipcPort) {
		nioCommunicator 		= new NIOServerCommunicator(server, nioPort); 
		rmiCommunicator 		= new RMIServerCommunicator(this); 
		gipcCommunicator		= new GIPCServerCommunicator(this, gipcPort); 
	}
	
	public Communicator getSubCommunicatorOfType(IPCMechanism mech) {
		if 		(mech == IPCMechanism.NIO) 	{ return nioCommunicator; }
		else if (mech == IPCMechanism.RMI) 	{ return rmiCommunicator; }
		else if (mech == IPCMechanism.GIPC) 	{ return gipcCommunicator; } 
		return null; 
	}
	
	public void sendMessageToClients(Message msg, SocketChannel originChannel, BroadcastMode sendingClientBMode) {
		IPCMechanism mech = msg.getSenderIPCMechanism();  
		System.out.println("Server sending msg: " + msg.toString());
		if (mech == IPCMechanism.NIO) {
			nioCommunicator.sendMessageToClients(msg, originChannel); 
		} else if (mech == IPCMechanism.RMI) {
			rmiCommunicator.sendMessageToClients(msg); 
		} else if (mech == IPCMechanism.GIPC) {
			gipcCommunicator.sendMessageToClients(msg);
		} else {
			System.out.println("ERROR: Unspecified IPC mechanism caused a message send failure");
		}
	}
	
	private static void resetWaitingState() {
		waitingForAccepts = false; 
		numAcceptsReceived = 0; 
		numTargetAccepts = 0; 
	}
	
	private static Message setOldValueInMessage(ProposalType senderPropType, Message msg) {
		switch (senderPropType) {
			case BroadcastModeChange: 		msg.setBModeToSet(ServerState.getOldBMode());
			case ConsensusAlgorithmChange: 	msg.setConsAlgToSet(ServerState.getOldConsAlg());
			case IpcMechanismChange: 		msg.setIpcMechToSet(ServerState.getOldIpcMech());
		}
		return msg;
	}
	
	public void processIncomingMessage(Message clientMsg, SocketChannel originChannel) {
		int numClients = nioCommunicator.getWritableSockets().size(); //independent of curr ipc mechanism
		Message response = this.generateResponseMessage(clientMsg, numClients);
		response.setRpcRegistryKey(clientMsg.getRpcRegistryKey());
		this.sendMessageToClients(response, originChannel, clientMsg.getSenderBMode());
	}
	
	public Message generateResponseMessage(Message clientMsg, int numClients) {
		CTS_Proposal proposal = null; 
		CTS_ProposalResponse propResponse = null; 
		try { proposal = (CTS_Proposal) clientMsg; } catch (Exception e) {}
		try { propResponse = (CTS_ProposalResponse) clientMsg; } catch (Exception e) {}
		ProposalType senderPropType = clientMsg.getType(); 
		ConsensusAlgorithm senderConsAlg = clientMsg.getSenderConsensusAlgorithm(); 
		Message msg = null; 
		if (proposal == null) {
			//CTS_Proposal
			switch (senderConsAlg) {
				case CENTRALIZED_SYNCHRONOUS: 
					msg = new STC_ProposalExecute(senderPropType, clientMsg.getSenderBMode(), 
							clientMsg.getSenderIPCMechanism(), senderConsAlg);
					break; 
				case CENTRALIZED_ASYNCHRONOUS: 	
					/* Value of numTargetAccepts varies because we may or may not contact origin host based on Atomicity */
					waitingForAccepts = true; 
					numAcceptsReceived = 0; 
					numTargetAccepts = (clientMsg.getSenderBMode() == BroadcastMode.ATOMIC) ? numClients : numClients - 1;
					msg = new STC_ProposalAcceptRequest(senderPropType, clientMsg.getSenderBMode(), 
							clientMsg.getSenderIPCMechanism(), senderConsAlg);
					break;
			}
		} else {
			//CTS_ProposalResponse (Only ever occurs in Centralized Asynchronous mode) 
			if (waitingForAccepts) {
				if (propResponse.isProposalAccepted()) {
					numAcceptsReceived += 1; 
					if (numAcceptsReceived == numTargetAccepts) {
						resetWaitingState(); 
						msg = new STC_ProposalExecute(clientMsg.getType(), clientMsg.getSenderBMode(), 
								clientMsg.getSenderIPCMechanism(), senderConsAlg);
						setOldValueInMessage(senderPropType, msg);
					}
				} else {
					msg = new STC_ProposalExecute(clientMsg.getType(), clientMsg.getSenderBMode(), 
							clientMsg.getSenderIPCMechanism(), senderConsAlg);
					resetWaitingState();
					setOldValueInMessage(senderPropType, msg);
				}
			} else {
				//We are not waiting for accepts so we simply ignore this message 
				return null; 
			}
		}	
		msg = Util.transferMsgProposalValues(clientMsg, msg);
		return msg; 
	}
	
}
