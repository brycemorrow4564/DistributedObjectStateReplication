package a1.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import a1.common.Communicator;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import a1.common.RPCCommunicator;
import a1.common.rmi.ADistributedClientManager;
import a1.common.rmi.ADistributedServerRelayer;
import a1.common.rmi.DistributedClientManager;
import a1.common.rmi.DistributedServerRegistrar;
import a1.common.rmi.DistributedServerRelayer;
import a1.util.Util;
import stringProcessors.HalloweenCommandProcessor;
import util.interactiveMethodInvocation.IPCMechanism;
import util.trace.port.rpc.rmi.RMIObjectLookedUp;
import util.trace.port.rpc.rmi.RMIRegistryLocated;

public class RMIClientCommunicator implements Communicator, RPCCommunicator {
	
	private static Registry rmiRegistry; 
	
	private static DistributedClientManager clientManager; //add to registry 
	private static DistributedServerRelayer serverRelayer; //acquire reference to 
	
	private static String managerId;
	
	public RMIClientCommunicator() {
		rmiRegistry 	= findRegistry(); 
		rmiConnectToServer();  
	}
	
	public void setClientManager(DistributedClientManager aClientManager) {
		clientManager = aClientManager; 
	}
	
	public String getServerSpecifiedId() {
		return managerId; 
	}
	
	public void sendMessageToServer(Message msg) {
		util.misc.ThreadSupport.sleep(1); 
		try {
			msg.setRpcRegistryKey(managerId);
			serverRelayer.passMsgToServer(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void rmiConnectToServer() {
		try {
			DistributedServerRegistrar serverRegistrarStub = (DistributedServerRegistrar) rmiRegistry.lookup("ServerRegistrar");
			RMIObjectLookedUp.newCase(this, serverRegistrarStub, "ServerRegistar", rmiRegistry);
			util.misc.ThreadSupport.sleep(1);
			managerId = serverRegistrarStub.registerClientsRemoteObjects(); 
			System.out.println(managerId.substring(0, 7) + " has been registered with the server");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private Registry findRegistry() {
		try { 
			Registry registry = LocateRegistry.getRegistry(); 
			RMIRegistryLocated.newCase(this, "localhost", 1099, rmiRegistry);
			return registry; 
		} 
		catch (RemoteException e) { e.printStackTrace(); }
		return null; 
	}
	
	public void exportObjects() {
		try {
			UnicastRemoteObject.exportObject(clientManager, 0);
			rmiRegistry.rebind(managerId, clientManager);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void acquireProxies() {
		try {
			serverRelayer = (DistributedServerRelayer) rmiRegistry.lookup("ServerRelayer");
			RMIObjectLookedUp.newCase(this, serverRelayer, "ServerRelayer", rmiRegistry);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
