package a1.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import a1.common.Communicator;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.rmi.DistributedClientManager;
import a1.common.rmi.DistributedServerRelayer;
import a1.server.GIPCConnectionListener;
import inputport.rpc.ACachingAbstractRPCProxyInvocationHandler;
import inputport.rpc.GIPCLocateRegistry;
import inputport.rpc.GIPCRegistry;
import port.ATracingConnectionListener;
import util.trace.port.consensus.RemoteProposeRequestSent;
import util.trace.port.consensus.communication.CommunicationStateNames;

public class GIPCClientCommunicator implements Communicator {
	
	protected GIPCRegistry gipcRegistry; 
	protected int gipcPort; 
	protected String clientManagerId;
	protected String serverHost;
	protected DistributedServerRelayer serverRelayer; 
	protected DistributedClientManager clientManager;   
	
	public GIPCClientCommunicator(int gipcPort, String serverHost) {
		this.gipcPort = gipcPort; 
		this.serverHost = serverHost; 
	}
	
	public void setClientManager(DistributedClientManager aClientManager) {
		clientManager = aClientManager; 
	}
	
	public void setClientManagerId(String id) {
		clientManagerId = id; 
	}
	
	public void exportObjects() {
		try {
			serverRelayer.gipcClientDeposit(clientManagerId, clientManager);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void acquireProxies() {
		ACachingAbstractRPCProxyInvocationHandler.setInvokeObjectMethodsRemotely(false);
		gipcRegistry = GIPCLocateRegistry.getRegistry(serverHost, gipcPort, clientManagerId);
		gipcRegistry.getInputPort().addConnectionListener(new GIPCConnectionListener(gipcRegistry.getInputPort()));
		serverRelayer = (DistributedServerRelayer) gipcRegistry.lookup(DistributedServerRelayer.class, "ServerRelayer");
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
			util.misc.ThreadSupport.sleep(1);
			serverRelayer.passMsgToServer(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
