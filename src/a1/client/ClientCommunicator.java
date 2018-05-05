package a1.client;

import a1.client.gipc.GIPCClientCommunicator;
import a1.client.nio.NIOClientCommunicator;
import a1.client.rmi.RMIClientCommunicator;
import a1.common.Communicator;
import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import a1.common.rpc.ADistributedClientManager;
import a1.common.rpc.DistributedClientManager;
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
		//This id is the one used by the server to identify this particular client 
		return rmiCommunicator.getServerSpecifiedId(); //generated via RMI, also used for GIPC 
	}
	
	public void createDistributedObjects(HalloweenCommandProcessor simulation) {
		clientManager = new ADistributedClientManager(ClientStateFactory.getState(), simulation);
	}
	
	public void notifyRpcCommunicatorsOfDistributedObjects() {
		rmiCommunicator.setClientManager(clientManager);
		gipcCommunicator.setClientManager(clientManager);
	}

	public void sendMessageToServer(Message msg) {
		try {
			switch (msg.getSenderIPCMechanism()) {
				case NIO: nioCommunicator.sendMessageToServer(msg); break; 
				case RMI: rmiCommunicator.sendMessageToServer(msg); break; 
				case GIPC: gipcCommunicator.sendMessageToServer(msg); break; 
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Communicator getSubCommunicatorOfType(IPCMechanism mech) {
		switch (mech) {
			case NIO: return nioCommunicator;   
			case RMI: return rmiCommunicator;
			case GIPC: return gipcCommunicator; 
			default: return null;
		}
	}
	
	public void performRpcSetup(HalloweenCommandProcessor simulation) {
		createDistributedObjects(simulation);
		notifyRpcCommunicatorsOfDistributedObjects();
		rmiCommunicator.acquireProxies();
		rmiCommunicator.exportObjects();
		gipcCommunicator.setClientManagerId(getRpcId());
		gipcCommunicator.acquireProxies();
		gipcCommunicator.exportObjects();
	}
	
}
