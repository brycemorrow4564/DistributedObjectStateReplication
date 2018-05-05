
package a1.common.rpc;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import a1.common.message.Message.ProposalType;
import a1.server.ServerCommunicator;
import util.interactiveMethodInvocation.IPCMechanism;
import util.trace.port.consensus.ProposalLearnedNotificationSent;

public class ADistributedServerRelayer implements DistributedServerRelayer {

	private ServerCommunicator serverCommunicator; 
	public static int clientCounter = 1; 
	private Message pendingMessage; 
	private boolean BLOCKED = false; //A mutex indicator boolean 
	
	public ADistributedServerRelayer(ServerCommunicator communicator) {
		this.serverCommunicator = communicator; 
	}
	
	@Override
	public synchronized String rmiClientDeposit(DistributedClientManager clientManager) {
		String id = "Client" + clientCounter; 
		clientCounter += 1;
		serverCommunicator.rmiJoinClient(id, clientManager);  
		return id; 
	}
	
	@Override
	public void gipcClientDeposit(String id, DistributedClientManager clientManager) {
		serverCommunicator.gipcJoinClient(id, clientManager);
	}
	
	private void generalizedRpcCentralAsyncPropose(Message msg, HashMap<String,DistributedClientManager> map, String rpcType) throws RemoteException {
		if (BLOCKED) {
			return; 
		}
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String clientId = (String) iter.next(); 
			DistributedClientManager cli =  (DistributedClientManager) map.get(clientId);
			if (msg.getSenderBMode() == BroadcastMode.NON_ATOMIC && 
					msg.getRpcRegistryKey().equals(clientId)) {
				continue; 
			} else {
				switch (msg.getPropType()) {
					//These remote calls are synchronous in case of RMI, asynchronous in case of GIPC
					case MetaStateChange:
						cli.executeMetaStateChange(msg.getBModeToSet(), msg.getIpcMechToSet()); break; 
					case SimulationCommand:
						cli.executeSimulationCommand(msg.getCommandToExecute()); break; 
					default: 
						System.err.println("Server received async " + rpcType + " proposal "
								+ "with no property type set in message");
				}
			}
		}
	}
	
	@Override
	public void asyncProposeGipc(Message msg) throws RemoteException {
		//CENTRALIZED ASYNCHRONOUS -- STATE CHANGE PROPOSAL CALLED BY CLIENT -- GIPC
		generalizedRpcCentralAsyncPropose(msg, serverCommunicator.getGipcClientManagers(), "GIPC");
	}

	@Override
	public void asyncProposeRmi(Message msg) throws RemoteException {
		//CENTRALIZED ASYNCHRONOUS -- STATE CHANGE PROPOSAL CALLED BY CLIENT -- RMI 
		generalizedRpcCentralAsyncPropose(msg, serverCommunicator.getRmiClientManagers(), "RMI");
	}
	
	public static float traceProposalNumber = 0;
	
	@Override
	public void syncProposeRmi(Message msg) throws RemoteException {
		// CENTRALIZED SYNCHRONOUS -- STATE CHANGE PROPOSAL CALLED BY CLIENT -- RMI 
		HashMap<String,DistributedClientManager> map = serverCommunicator.getRmiClientManagers();
		Iterator iter = map.keySet().iterator();
		boolean clientVetoed = false;
		while (iter.hasNext()) {
			DistributedClientManager client = map.get(iter.next());
			boolean isMetaStateChange = msg.getPropType() == ProposalType.MetaStateChange; 
			if (!client.acceptProposalRmi(isMetaStateChange)) {
				clientVetoed = true; 
				break; 
			}
		}
		iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String clientId = (String) iter.next(); 
			DistributedClientManager cli = map.get(clientId);
			if (msg.getSenderBMode() == BroadcastMode.NON_ATOMIC && 
					msg.getRpcRegistryKey().equals(clientId) && 
					msg.getPropType() == ProposalType.SimulationCommand) {
				continue; 
			} else {
				switch (msg.getPropType()) {
					case MetaStateChange:
						BroadcastMode newBMode = clientVetoed ? BroadcastMode.ATOMIC : msg.getBModeToSet();
						IPCMechanism newIpcMech = clientVetoed ? IPCMechanism.RMI : msg.getIpcMechToSet();
						ProposalLearnedNotificationSent.newCase(this, "", traceProposalNumber, newBMode == BroadcastMode.ATOMIC);
						ProposalLearnedNotificationSent.newCase(this, "", traceProposalNumber, newIpcMech);
						cli.executeMetaStateChange(newBMode, newIpcMech);
						break; 
					case SimulationCommand:
						String simCommand = clientVetoed ? "" : msg.getCommandToExecute();
						ProposalLearnedNotificationSent.newCase(this, "", traceProposalNumber, simCommand);
						cli.executeSimulationCommand(simCommand);
						break; 
					default: 
						System.err.println("Server received async RMI proposal "
								+ "with no property type set in message");
				}
				traceProposalNumber += 1;
			}
		}
	}
	
	//will always be called by thread with mutually exclusive access to BLOCKED 
	private void unblock() {
		BLOCKED = false; 
	}
	
	private synchronized boolean testSetBlocked() {
		if (BLOCKED) { return false; }
		BLOCKED = true; 
		return true; 
	}

	@Override
	public boolean syncProposeGipc(Message msg) throws RemoteException {
		// CENTRALIZED SYNCHRONOUS -- STATE CHANGE PROPOSAL CALLED BY CLIENT -- GIPC 
		//ensures only sync gipc proposal at a time via a synchronized test and set. 
		//Calls to this object will be discarded if they are not of interest to this method. 
		boolean keepGoing = testSetBlocked();  
		if (!keepGoing) {
			return false; 
		}
		HashMap<String,DistributedClientManager> map = serverCommunicator.getGipcClientManagers();
		Iterator iter = map.keySet().iterator();
		boolean clientVetoed = false;
		boolean isMetaStateChange = msg.getPropType() == ProposalType.MetaStateChange;
		boolean accepted; 
		while (iter.hasNext()) {
			DistributedClientManager client = map.get(iter.next()); 
			accepted = client.acceptProposalGipc(isMetaStateChange);
			if (!accepted) { clientVetoed  = true; break; }
		}
		iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String clientId = (String) iter.next(); 
			DistributedClientManager cli = map.get(clientId);
			if (msg.getSenderBMode() == BroadcastMode.NON_ATOMIC && 
					msg.getRpcRegistryKey().equals(clientId) && 
					msg.getPropType() == ProposalType.SimulationCommand) {
				continue; 
			} else {
				String simCommand = clientVetoed ? "" : msg.getCommandToExecute();
				if (simCommand == null) {
					System.err.println("Error: simulation command not contained in gipc message sent to server" );
				}
				cli.executeSimulationCommand(simCommand);
			}
		}
		unblock();  
		return true; 
	}
	
}
