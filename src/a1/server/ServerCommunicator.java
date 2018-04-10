package a1.server;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.CTS_Proposal;
import a1.common.message.CTS_ProposalResponse;
import a1.common.message.Message;
import a1.common.message.STC_ProposalAcceptRequest;
import a1.common.message.STC_ProposalExecute;
import a1.common.rmi.DistributedClientManager;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import a1.common.Communicator;
import a1.common.Util;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;
import util.trace.port.consensus.ProposalAcceptRequestSent;
import util.trace.port.consensus.RemoteProposeRequestReceived;
import util.trace.port.consensus.communication.CommunicationStateNames;

public class ServerCommunicator {
	
	public static boolean isWaiting = false; 
	public static int numAcceptsReceived = 0;
	public static int numTargetAccepts = 0;  
	
	private NIOServerCommunicator	nioCommunicator; 
	private RMIServerCommunicator 	rmiCommunicator;  
	private GIPCServerCommunicator   gipcCommunicator;
	private HashMap<String, DistributedClientManager> rmiClientManagers; 
	private HashMap<String, DistributedClientManager> gipcClientManagers; 

	public ServerCommunicator(SimulationServer server, int nioPort, int gipcPort, 
			String registryHost, int registryPort) {
		nioCommunicator 		= new NIOServerCommunicator(server, nioPort); 
		rmiCommunicator 		= new RMIServerCommunicator(this, registryHost, registryPort); 
		gipcCommunicator		= new GIPCServerCommunicator(this, gipcPort); 
		rmiClientManagers 	= new HashMap<String, DistributedClientManager>();
		gipcClientManagers 	= new HashMap<String, DistributedClientManager>();
	}
	
	public HashMap<String, DistributedClientManager> getRmiClientManagers() {
		return rmiClientManagers; 
	}
	
	public HashMap<String, DistributedClientManager> getGipcClientManagers() {
		return gipcClientManagers; 
	}
	
	public void rmiJoinClient(String id, DistributedClientManager clientManager) {
		rmiClientManagers.put(id, clientManager);
	}
	
	public void gipcJoinClient(String id, DistributedClientManager clientManager) {
		gipcClientManagers.put(id, clientManager);
	}
	
	public Communicator getSubCommunicatorOfType(IPCMechanism mech) {
		switch (mech) {
			case NIO: return nioCommunicator;
			case RMI: return rmiCommunicator; 
			case GIPC: return gipcCommunicator; 
			default: System.out.println("ERROR: Unspecified IPC mechanism error in ServerCommunicator"); return null; 
		}
	}
	
	public void sendMessageToClients(Message msg, SocketChannel originChannel, BroadcastMode sendingClientBMode) {
		IPCMechanism mech = msg.getSenderIPCMechanism();  
		System.out.println("Server sending msg: " + msg.toString());
		switch (mech) {
			case NIO: nioCommunicator.sendMessageToClients(msg, originChannel); break; 
			case RMI: rmiCommunicator.sendMessageToClients(msg); break; 
			case GIPC: gipcCommunicator.sendMessageToClients(msg); break; 
			default: System.out.println("ERROR: Unspecified IPC mechanism error in ServerCommunicator");
		}
	}
	
	private static void exitWaitState() {
		ServerCommunicator.isWaiting = false; 
		ServerCommunicator.numAcceptsReceived = 0; 
		ServerCommunicator.numTargetAccepts = 0; 
	}
	
	private void traceRemoteProposeRequestReceived(Message clientMsg) {
		if (clientMsg.getSenderIPCMechanism() == IPCMechanism.RMI) {
			switch (clientMsg.getType()) {
				case MetaStateChange: 
					RemoteProposeRequestReceived.newCase(this, CommunicationStateNames.BROADCAST_MODE, -1, clientMsg.getBModeToSet() == BroadcastMode.ATOMIC);
					RemoteProposeRequestReceived.newCase(this, CommunicationStateNames.IPC_MECHANISM, -1, clientMsg.getIpcMechToSet());
					break; 
				case SimulationCommand:
					RemoteProposeRequestReceived.newCase(this, CommunicationStateNames.COMMAND, -1, clientMsg.getCommandToExecute());
					break; 
				default: 
			}
		}
	}
	
	public void processIncomingMessage(Message clientMsg, SocketChannel originChannel) {
		traceRemoteProposeRequestReceived(clientMsg);
		int numClients = nioCommunicator.getWritableSockets().size(); //independent of curr ipc mechanism
		Message response = this.generateResponseMessage(clientMsg, numClients);
		if (response == null) {
			//we are not accepting messages at this time 
			return; 
		}
		
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
		boolean transferValues = true; 
		if (proposal == null) {
			//CTS_Proposal
			switch (senderConsAlg) {
				case CENTRALIZED_ASYNCHRONOUS: 
					msg = new STC_ProposalExecute(senderPropType, null, null, senderConsAlg);
					break; 
				case CENTRALIZED_SYNCHRONOUS: 	
					ServerCommunicator.isWaiting = true; 
					ServerCommunicator.numAcceptsReceived = 0; 
					ServerCommunicator.numTargetAccepts = numClients;
					//RemoteProposeRequestReceived.newCase(this, , -1, clientMsg.getBModeToSet() == BroadcastMode.ATOMIC);
					if (senderPropType == ProposalType.MetaStateChange) {
						ProposalAcceptRequestSent.newCase(this, 
								CommunicationStateNames.BROADCAST_MODE, -1, msg.getBModeToSet() == BroadcastMode.ATOMIC);
						ProposalAcceptRequestSent.newCase(this, 
								CommunicationStateNames.IPC_MECHANISM, -1, msg.getIpcMechToSet()); 
					} else {
						ProposalAcceptRequestSent.newCase(this, 
								CommunicationStateNames.COMMAND, -1, msg.getCommandToExecute()); 
					}
					msg = new STC_ProposalAcceptRequest(senderPropType, null, null, senderConsAlg);
					break;
			}
		} else {
			//CTS_ProposalResponse (Only ever occurs in Centralized Asynchronous mode with Atomic Broadcast) 
			if (ServerCommunicator.isWaiting) {
				if (propResponse.isProposalAccepted()) {
					numAcceptsReceived += 1; 
					if (numAcceptsReceived == numTargetAccepts) {
						exitWaitState(); 
						msg = new STC_ProposalExecute(clientMsg.getType(), null, null, senderConsAlg);
					}
				} else {
					//Proposal denied so we send old values back (or defaults if we lack old values) 
					msg = new STC_ProposalExecute(clientMsg.getType(), null, null, senderConsAlg);
					exitWaitState();
					msg.setBModeToSet(ServerState.getOldBMode());
					msg.setIpcMechToSet(ServerState.getOldIpcMech());
					transferValues = false; 
				}
			} else {
				//We are not waiting for accepts so we simply ignore this message 
				return null; 
			}
		}	
		return !transferValues ? msg : Util.transferMsgProposalValues(clientMsg, msg);
	}
	
}
