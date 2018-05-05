package a1.client.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import a1.common.Communicator;
import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.rpc.DistributedClientManager;
import a1.common.rpc.DistributedServerRelayer;
import util.trace.port.consensus.ProposalMade;
import util.trace.port.consensus.RemoteProposeRequestSent;
import util.trace.port.rpc.rmi.RMIObjectLookedUp;
import util.trace.port.rpc.rmi.RMIObjectRegistered;
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
	
	private void traceRemoteSend(Message msg) {
		switch (msg.getPropType()) {
			case MetaStateChange: 
				RemoteProposeRequestSent.newCase(this, CommunicationStateNames.BROADCAST_MODE, -1, msg.getBModeToSet() == BroadcastMode.ATOMIC);
				RemoteProposeRequestSent.newCase(this, CommunicationStateNames.IPC_MECHANISM, -1, msg.getIpcMechToSet());
				break;
			case SimulationCommand: 
				RemoteProposeRequestSent.newCase(this, CommunicationStateNames.COMMAND, -1, msg.getCommandToExecute());
				break;
		}
	}
	
	private static float traceCounter = 0;
	
	public void sendMessageToServer(Message msg) throws Exception {
		try {
			msg.setRpcRegistryKey(clientManagerId); 
			traceRemoteSend(msg); 
			//util.misc.ThreadSupport.sleep(1);
			switch (Util.getConsensusAlgorithmFromState()) {
				case CENTRALIZED_SYNCHRONOUS: 
					serverRelayer.syncProposeRmi(msg); break;
				case CENTRALIZED_ASYNCHRONOUS:
					serverRelayer.asyncProposeRmi(msg); break;
			}
			traceCounter += 1; 
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void exportObjects() {
		try {
			UnicastRemoteObject.exportObject(clientManager, 0);
			String myId = serverRelayer.rmiClientDeposit(clientManager); 
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
