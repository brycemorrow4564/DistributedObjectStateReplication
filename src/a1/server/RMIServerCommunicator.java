package a1.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import a1.common.Communicator;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.rmi.ADistributedServerRegistrar;
import a1.common.rmi.ADistributedServerRelayer;
import a1.common.rmi.DistributedClientManager;
import a1.common.rmi.DistributedServerRegistrar;
import a1.common.rmi.DistributedServerRelayer;
import util.trace.port.rpc.rmi.RMIObjectLookedUp;
import util.trace.port.rpc.rmi.RMIObjectRegistered;
import util.trace.port.rpc.rmi.RMIRegistryCreated;
import util.trace.port.rpc.rmi.RMIRegistryLocated;

public class RMIServerCommunicator implements Communicator {
	
	private ServerCommunicator 							parentCommunicator; 
	
	private 	Registry 									rmiRegistry; 
	private static ADistributedServerRelayer				serverRelayer; 
	private static ADistributedServerRegistrar 			serverRegistrar; 
	
	public RMIServerCommunicator(ServerCommunicator parentCommunicator) {
		this.parentCommunicator = parentCommunicator; 
		createAndExportObjects();
	}
	
	public DistributedServerRelayer getServerRelayer() {
		return (DistributedServerRelayer) serverRelayer; 
	}
	
	public DistributedServerRegistrar getServerRegistrar() {
		return (DistributedServerRegistrar) serverRegistrar; 
	}
	
	private void findRegistry() {
		try { 
			RMIRegistryCreated.newCase(this, 1099); //Todo: move this to a separate process for grading 
			LocateRegistry.createRegistry(1099); //GET RID OF THIS LATER ON 
			rmiRegistry = LocateRegistry.getRegistry(); 
			RMIRegistryLocated.newCase(this, "localhost", 1099, rmiRegistry);
		} 
		catch (RemoteException e) { e.printStackTrace(); }
	}

	private void createAndExportObjects() {
		serverRegistrar = new ADistributedServerRegistrar(); 
		serverRelayer = new ADistributedServerRelayer(parentCommunicator, serverRegistrar);
		try {
			this.findRegistry();
			UnicastRemoteObject.exportObject((DistributedServerRegistrar) serverRegistrar, 0);
			UnicastRemoteObject.exportObject((DistributedServerRelayer) serverRelayer, 0);
			rmiRegistry.rebind("ServerRegistrar", serverRegistrar);
			rmiRegistry.rebind("ServerRelayer", serverRelayer);
			RMIObjectRegistered.newCase(this, "ServerRegistrar", serverRegistrar, rmiRegistry);
			RMIObjectRegistered.newCase(this, "ServerRelayer", serverRelayer, rmiRegistry);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessageToClients(Message msg) {
		ArrayList<String> clientStubIds = serverRegistrar.getRegisteredClientIds();
		for (String id: clientStubIds) {
			DistributedClientManager clientStateStub = null;
			try {
				clientStateStub = (DistributedClientManager) rmiRegistry.lookup(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			RMIObjectLookedUp.newCase(this, clientStateStub, id, rmiRegistry);
			if (!(msg.getSenderBMode() == BroadcastMode.NON_ATOMIC && msg.getRpcRegistryKey().equals(id)))	{
				continue; //skip origin client in non_atomic mode 
			}	
			util.misc.ThreadSupport.sleep(1);
			try {
				clientStateStub.passMsgToClient(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
}
