package a1.server;

import java.rmi.RemoteException;
import java.util.HashMap;

import a1.common.Communicator;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.rmi.DistributedClientManager;
import a1.common.rmi.DistributedServerRelayer;
import inputport.rpc.GIPCLocateRegistry;
import inputport.rpc.GIPCRegistry;
import util.interactiveMethodInvocation.IPCMechanism;

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
		gipcRegistry.rebind("ServerRelayer", relayer);
	}

	public void sendMessageToClients(Message msg) {
		HashMap <String, DistributedClientManager> clientMap = parentCommunicator.getGipcClientManagers(); 
		for (String id : clientMap.keySet()) {
			if (msg.getSenderBMode() == BroadcastMode.NON_ATOMIC && msg.getRpcRegistryKey().equals(id)) {
				continue; //skip origin client in non_atomic mode
			}	
			try {
				util.misc.ThreadSupport.sleep(1);
				clientMap.get(id).passMsgToClient(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

}
