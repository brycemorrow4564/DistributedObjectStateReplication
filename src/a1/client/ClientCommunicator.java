package a1.client;

import a1.common.Communicator;
import a1.common.message.Message;
import a1.common.rmi.ADistributedClientManager;
import a1.common.rmi.DistributedClientManager;
import stringProcessors.HalloweenCommandProcessor;
import util.interactiveMethodInvocation.IPCMechanism;

public class ClientCommunicator {

	//Communicator references
	protected NIOClientCommunicator 		nioCommunicator; 
	protected RMIClientCommunicator 		rmiCommunicator; 
	protected GIPCClientCommunicator  	gipcCommunicator; 
	
	//Distributed object references 
	protected DistributedClientManager 	clientManager; 
	
	public ClientCommunicator(SimulationClient client, String aServerHost, int aServerPort, int gipcPort, HalloweenCommandProcessor simulation) {
		nioCommunicator 		= new NIOClientCommunicator(client, aServerHost, aServerPort); 
		rmiCommunicator 		= new RMIClientCommunicator();
		gipcCommunicator		= new GIPCClientCommunicator(gipcPort, rmiCommunicator.getServerSpecifiedId());
	}	
	
	public String getRpcId() {
		return rmiCommunicator.getServerSpecifiedId(); 
	}
	
	public void createDistributedObjects(HalloweenCommandProcessor simulation) {
		clientManager = new ADistributedClientManager(ClientStateFactory.getState(), simulation);
	}
	
	public void notifySubCommunicatorsOfDistributedObjects() {
		rmiCommunicator.setClientManager(clientManager);
		gipcCommunicator.setClientManager(clientManager);
	}
	
	public void sendMessageToServer(Message msg) {
		IPCMechanism mech = ClientStateFactory.getState().getIPCMechanism(); 
		System.out.println("Client sending msg: \n" + msg.toString());
		switch (mech) {
			case NIO: 
				nioCommunicator.sendMessageToServer(msg); 
			case RMI: 
				rmiCommunicator.sendMessageToServer(msg); 
			case GIPC: 
				gipcCommunicator.sendMessageToServer(msg); 
		}
	}
	
	public Communicator getSubCommunicatorOfType(IPCMechanism mech) {
		if 		(mech == IPCMechanism.NIO) 	{ return nioCommunicator; }
		else if (mech == IPCMechanism.RMI) 	{ return rmiCommunicator; }
		else if (mech == IPCMechanism.GIPC) 	{ return gipcCommunicator; } 
		return null; 
	}
	
}
