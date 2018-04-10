package a1.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import a1.common.Communicator;
import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.rmi.DistributedClientManager;
import a1.common.rmi.DistributedServerRelayer;
import util.trace.port.consensus.ProposalMade;
import util.trace.port.consensus.RemoteProposeRequestSent;
import util.trace.port.rpc.rmi.RMIObjectLookedUp;
import util.trace.port.rpc.rmi.RMIRegistryLocated;
import util.trace.port.consensus.communication.CommunicationStateNames;

public class RMIClientCommunicator implements Communicator {
	
	protected Registry rmiRegistry; 
	protected DistributedClientManager clientManager; 
	protected DistributedServerRelayer serverRelayer; 
	protected String clientManagerId;
	
	protected String registryHost; 
	protected int registryPort; 
	
	public RMIClientCommunicator(String registryHost, int registryPort) {
		try { 
			rmiRegistry = LocateRegistry.getRegistry(registryHost, registryPort);
			RMIRegistryLocated.newCase(this, registryHost, registryPort, rmiRegistry);
		} 
		catch (RemoteException e) { e.printStackTrace(); }
	}
	
	public void setClientManager(DistributedClientManager aClientManager) {
		clientManager = aClientManager; 
	}
	
	public String getServerSpecifiedId() {
		return clientManagerId; 
	}
	
	private void traceRemoteProposalRequestSend(Message msg) {
		switch (msg.getType()) {
			case MetaStateChange: 
				RemoteProposeRequestSent.newCase(this, CommunicationStateNames.BROADCAST_MODE, -1, msg.getBModeToSet() == BroadcastMode.ATOMIC);
				RemoteProposeRequestSent.newCase(this, CommunicationStateNames.IPC_MECHANISM, -1, msg.getIpcMechToSet());
				break;
			case SimulationCommand: 
				RemoteProposeRequestSent.newCase(this, CommunicationStateNames.COMMAND, -1, msg.getCommandToExecute());
				break; 
			default:
		}
	}
	
	public void sendMessageToServer(Message msg) {
		try {
			msg.setRpcRegistryKey(clientManagerId); 
			traceRemoteProposalRequestSend(msg); 
			util.misc.ThreadSupport.sleep(Util.getSendDelay()); 
			serverRelayer.passMsgToServer(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void exportObjects() {
		try {
			UnicastRemoteObject.exportObject(clientManager, 0);
			String myId = serverRelayer.rmiClientJoin(clientManager); 
			this.clientManagerId = myId;
			System.out.println(clientManagerId + " has registered with the server via RMI");
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
