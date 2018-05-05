package a1.server;

import java.util.HashMap;

import a1.server.gipc.GIPCServerCommunicator;
import a1.server.nio.NIOServerCommunicator;
import a1.server.rmi.RMIServerCommunicator;
import a1.common.Communicator;
import a1.common.rpc.DistributedClientManager;
import util.interactiveMethodInvocation.IPCMechanism;

public class ServerCommunicator {
	
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

}
