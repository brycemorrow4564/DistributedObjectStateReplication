package a1.server.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import a1.common.Communicator;
import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import a1.common.message.Message.ProposalType;
import a1.common.rpc.ADistributedServerRelayer;
import a1.common.rpc.DistributedClientManager;
import a1.common.rpc.DistributedServerRelayer;
import a1.server.ServerCommunicator;
import util.interactiveMethodInvocation.IPCMechanism;
import util.trace.port.consensus.ProposalLearnedNotificationSent;
import util.trace.port.consensus.communication.CommunicationStateNames;
import util.trace.port.rpc.rmi.RMIObjectRegistered;
import util.trace.port.rpc.rmi.RMIRegistryCreated;
import util.trace.port.rpc.rmi.RMIRegistryLocated;
import util.annotations.Tags;
import util.tags.DistributedTags; 

@Tags({DistributedTags.REGISTRY, DistributedTags.RMI})
public class RMIServerCommunicator implements Communicator {
	
	private ServerCommunicator 							parentCommunicator; 
	private 	Registry 									rmiRegistry; 
	private static ADistributedServerRelayer				serverRelayer; 
	private String registryHost; 
	private int registryPort; 
	
	public RMIServerCommunicator(ServerCommunicator parentCommunicator, String registryHost, int registryPort) {
		this.parentCommunicator = parentCommunicator; 
		this.registryHost = registryHost; 
		this.registryPort = registryPort; 
		createAndExportObjects();
	}
	
	public DistributedServerRelayer getServerRelayer() {
		return (DistributedServerRelayer) serverRelayer; 
	}

	private void findRegistry() {
		try { 
			RMIRegistryCreated.newCase(this, registryPort); 
			LocateRegistry.createRegistry(registryPort);
			rmiRegistry = LocateRegistry.getRegistry("localhost", registryPort); 
			RMIRegistryLocated.newCase(this, registryHost, registryPort, rmiRegistry);
		} 
		catch (RemoteException e) { e.printStackTrace(); }
	}

	private void createAndExportObjects() {
		serverRelayer = new ADistributedServerRelayer(parentCommunicator);
		try {
			findRegistry();
			UnicastRemoteObject.exportObject((DistributedServerRelayer) serverRelayer, 0);
			rmiRegistry.rebind("ServerRelayer", serverRelayer);
			RMIObjectRegistered.newCase(this, "ServerRelayer", serverRelayer, rmiRegistry);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
