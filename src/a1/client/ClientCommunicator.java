package a1.client;

import a1.common.Communicator;
import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import a1.common.rmi.ADistributedClientManager;
import a1.common.rmi.DistributedClientManager;
import stringProcessors.HalloweenCommandProcessor;
import util.interactiveMethodInvocation.IPCMechanism;

public class ClientCommunicator {

	protected NIOClientCommunicator 		nioCommunicator; 
	protected RMIClientCommunicator 		rmiCommunicator; 
	protected GIPCClientCommunicator  	gipcCommunicator; 
	protected DistributedClientManager 	clientManager; 
	
	public ClientCommunicator(SimulationClient client, HalloweenCommandProcessor simulation, String serverHost, 
			int serverPort, String registryHost, int registryPort, int gipcPort) {
		nioCommunicator 		= new NIOClientCommunicator(client, serverHost, serverPort); 
		rmiCommunicator 		= new RMIClientCommunicator(registryHost, registryPort);
		gipcCommunicator		= new GIPCClientCommunicator(gipcPort, serverHost);
	}	
	
	public String getRpcId() {
		return rmiCommunicator.getServerSpecifiedId(); 
	}
	
	public void createDistributedObjects(HalloweenCommandProcessor simulation) {
		clientManager = new ADistributedClientManager(ClientStateFactory.getState(), simulation);
	}
	
	public void notifyRpcCommunicatorsOfDistributedObjects() {
		rmiCommunicator.setClientManager(clientManager);
		gipcCommunicator.setClientManager(clientManager);
	}
	
	public void sendMessageToServer(Message msg) {
		System.out.println("Client sending msg: \n" + msg.toString());
		if (msg.getMsgType() == MsgType.CTS_Proposal) {
			IPCMechanism mech = ClientStateFactory.getState().getIPCMechanism(); 
			switch (mech) {
				case NIO: nioCommunicator.sendMessageToServer(msg); break; 
				case RMI: rmiCommunicator.sendMessageToServer(msg); break; 
				case GIPC: gipcCommunicator.sendMessageToServer(msg); break; 
			}
		} else {
			//MsgType.CTS_ProposalResponse
			rmiCommunicator.sendMessageToServer(msg);	
		}
	}
	
	public Communicator getSubCommunicatorOfType(IPCMechanism mech) {
		Communicator comm; 
		switch (mech) {
			case NIO: comm = nioCommunicator; break;  
			case RMI: comm = rmiCommunicator; break; 
			case GIPC: comm = gipcCommunicator; break; 
			default: return null; 
		}
		return comm; 
	}
	
}
