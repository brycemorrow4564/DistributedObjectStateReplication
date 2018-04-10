package a1.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import a1.common.Communicator;
import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.rmi.ADistributedServerRelayer;
import a1.common.rmi.DistributedClientManager;
import a1.common.rmi.DistributedServerRelayer;
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
			rmiRegistry = LocateRegistry.getRegistry(); 
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
	
	private void traceProposalLearnedNotificationSent(Message outMsg) {
		if (outMsg.getSenderIPCMechanism() == IPCMechanism.RMI) {
			String proposalType = "";
			switch (outMsg.getType()) {
				case MetaStateChange: 
					ProposalLearnedNotificationSent.newCase(this, CommunicationStateNames.BROADCAST_MODE, -1, outMsg.getBModeToSet() == BroadcastMode.ATOMIC);
					ProposalLearnedNotificationSent.newCase(this, CommunicationStateNames.IPC_MECHANISM, -1, outMsg.getIpcMechToSet());
					break; 
				case SimulationCommand: 
					ProposalLearnedNotificationSent.newCase(this, CommunicationStateNames.COMMAND, -1, outMsg.getCommandToExecute());
					break; 
				default: 
			}
		}
	}
	
	public void sendMessageToClients(Message msg) {
		traceProposalLearnedNotificationSent(msg);
		HashMap <String, DistributedClientManager> clientMap = parentCommunicator.getRmiClientManagers(); 
		for (String id : clientMap.keySet()) {
			if (msg.getSenderBMode() == BroadcastMode.NON_ATOMIC && msg.getRpcRegistryKey().equals(id)) {
				continue; //skip origin client in non_atomic mode
			}	
			try {
				util.misc.ThreadSupport.sleep(Util.getSendDelay());
				clientMap.get(id).passMsgToClient(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
}
