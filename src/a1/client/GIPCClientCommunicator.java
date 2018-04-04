package a1.client;

import java.rmi.RemoteException;

import a1.common.Communicator;
import a1.common.RPCCommunicator;
import a1.common.message.Message;
import a1.common.rmi.DistributedClientManager;
import a1.common.rmi.DistributedServerRelayer;
import inputport.rpc.ACachingAbstractRPCProxyInvocationHandler;
import inputport.rpc.GIPCLocateRegistry;
import inputport.rpc.GIPCRegistry;

public class GIPCClientCommunicator implements Communicator, RPCCommunicator {
	
	protected GIPCRegistry gipcRegistry; 
	
	protected int gipcPort; 
	protected String clientManagerId;
	
	protected DistributedServerRelayer serverRelayer; //acquire reference to 
	protected DistributedClientManager clientManager; //add to registry 
	
	public GIPCClientCommunicator(int gipcPort, String clientManagerId) {
		this.clientManagerId = clientManagerId; 
		this.gipcPort = gipcPort; 
		init(); 
	}
	
	public void init() {
		ACachingAbstractRPCProxyInvocationHandler.setInvokeObjectMethodsRemotely(false);
		gipcRegistry = GIPCLocateRegistry.getRegistry("localhost", gipcPort, clientManagerId);
	}
	
	public void setClientManager(DistributedClientManager aClientManager) {
		clientManager = aClientManager; 
	}
	
	public void exportObjects() {
		gipcRegistry.rebind(clientManagerId, clientManager);	
	}
	
	public void acquireProxies() {
		serverRelayer = (DistributedServerRelayer) gipcRegistry.lookup(DistributedServerRelayer.class, "ServerRelayer");
	}
	
	public void sendMessageToServer(Message msg) {
		util.misc.ThreadSupport.sleep(1); 
		try {
			msg.setRpcRegistryKey(clientManagerId);
			serverRelayer.passMsgToServer(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
