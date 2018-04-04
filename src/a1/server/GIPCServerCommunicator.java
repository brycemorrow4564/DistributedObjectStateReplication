package a1.server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import a1.common.Communicator;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.rmi.ADistributedServerRegistrar;
import a1.common.rmi.DistributedClientManager;
import a1.common.rmi.DistributedServerRegistrar;
import a1.common.rmi.DistributedServerRelayer;
import inputport.rpc.GIPCLocateRegistry;
import inputport.rpc.GIPCRegistry;
import util.interactiveMethodInvocation.IPCMechanism;
import util.trace.port.rpc.gipc.GIPCObjectLookedUp;
import util.trace.port.rpc.rmi.RMIObjectLookedUp;

public class GIPCServerCommunicator implements Communicator {
	
	protected GIPCRegistry 		gipcRegistry; 
	protected int 				gipcPort; 
	protected ServerCommunicator parentCommunicator; 
	
	public GIPCServerCommunicator(ServerCommunicator parentCommunicator, int gipcPort) {
		this.gipcPort = gipcPort; 
		this.parentCommunicator = parentCommunicator; 
		init(); 
		exportServerRelayer(); 
	}
	
	public void init() {
		gipcRegistry = GIPCLocateRegistry.createRegistry(gipcPort);
		gipcRegistry.getInputPort().addConnectionListener(new GIPCConnectionListener(gipcRegistry.getInputPort()));
	}
	
	public void exportServerRelayer() {
		RMIServerCommunicator rmiComm = (RMIServerCommunicator) parentCommunicator.getSubCommunicatorOfType(IPCMechanism.RMI);
		DistributedServerRelayer relayer = rmiComm.getServerRelayer(); 
		gipcRegistry.rebind("Server", relayer);
	}

	public void sendMessageToClients(Message msg) {
		ADistributedServerRegistrar servReg = (ADistributedServerRegistrar) ((RMIServerCommunicator) parentCommunicator.getSubCommunicatorOfType(IPCMechanism.RMI)).getServerRegistrar(); 
		ArrayList<String> clientStubIds = servReg.getRegisteredClientIds();
		for (String id: clientStubIds) {
			DistributedClientManager clientStateStub = null;
			try {
				clientStateStub = (DistributedClientManager) gipcRegistry.lookup(DistributedClientManager.class, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			GIPCObjectLookedUp.newCase(this, clientStateStub, DistributedClientManager.class, id, gipcRegistry);
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
