package a1.client.gipc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import a1.common.Communicator;
import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import a1.common.message.Message.ProposalType;
import a1.common.rpc.DistributedClientManager;
import a1.common.rpc.DistributedServerRelayer;
import a1.server.gipc.GIPCConnectionListener;
import examples.gipc.counter.customization.ACustomDuplexObjectInputPortFactory;
import examples.gipc.counter.customization.FactorySetterFactory;
import inputport.InputPort;
import inputport.datacomm.ReceiveListener;
import inputport.datacomm.duplex.object.explicitreceive.ExplicitReceiveListener;
import inputport.rpc.ACachingAbstractRPCProxyInvocationHandler;
import inputport.rpc.GIPCLocateRegistry;
import inputport.rpc.GIPCRegistry;
import inputport.rpc.duplex.DuplexRPCClientInputPort;
import port.ATracingConnectionListener;
import util.trace.port.consensus.RemoteProposeRequestSent;
import util.trace.port.consensus.communication.CommunicationStateNames;
import util.trace.port.rpc.gipc.GIPCRegistryLocated;

public class GIPCClientCommunicator implements Communicator {
	
	protected GIPCRegistry gipcRegistry; 
	protected int gipcPort; 
	protected String clientManagerId; //assigned by the server 
	protected String serverRelayerId = "ServerRelayer";
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
		GIPCRegistryLocated.newCase(this, serverHost, gipcPort, clientManagerId);
		if (gipcRegistry == null) {
			System.err.println("Could not connect to server :" + serverHost + ":" + gipcPort);
			System.exit(-1);
		}
		InputPort gipcInputPort = gipcRegistry.getInputPort(); 
		gipcInputPort.addConnectionListener(new GIPCConnectionListener(gipcInputPort));
		serverRelayer = (DistributedServerRelayer) 
				gipcRegistry.lookup(DistributedServerRelayer.class, serverRelayerId);
	}
	
	private void traceRemoteSend(Message msg) {
		if (msg.getPropType() == ProposalType.MetaStateChange) {
			RemoteProposeRequestSent.newCase(this, CommunicationStateNames.BROADCAST_MODE, -1, msg.getBModeToSet() == BroadcastMode.ATOMIC);
			RemoteProposeRequestSent.newCase(this, CommunicationStateNames.IPC_MECHANISM, -1, msg.getIpcMechToSet());
		} else {
			RemoteProposeRequestSent.newCase(this, CommunicationStateNames.COMMAND, -1, msg.getCommandToExecute());
		}
	}
	
	public void sendMessageToServer(Message msg) throws Exception { 
		try {
			msg.setRpcRegistryKey(clientManagerId);
			traceRemoteSend(msg); 
			//util.misc.ThreadSupport.sleep(1);
			boolean x; 
			switch (Util.getConsensusAlgorithmFromState()) {
				case CENTRALIZED_SYNCHRONOUS: 
					x = serverRelayer.syncProposeGipc(msg); 
				case CENTRALIZED_ASYNCHRONOUS: 
					serverRelayer.asyncProposeGipc(msg);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
