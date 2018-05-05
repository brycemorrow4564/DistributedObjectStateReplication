package a1.common.rpc;

import java.rmi.RemoteException;

import a1.client.ClientCommunicatorFactory;
import a1.client.ClientState;
import a1.client.ClientStateFactory;
import a1.client.SimulationClient;
import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import stringProcessors.HalloweenCommandProcessor;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;
import util.trace.port.consensus.ProposalLearnedNotificationReceived;
import util.trace.port.consensus.communication.CommunicationStateNames;

public class ADistributedClientManager implements DistributedClientManager {

	protected ClientState state; 
	private HalloweenCommandProcessor simulation; 
	
	public ADistributedClientManager(ClientState aState, HalloweenCommandProcessor aNonDistSimulation) {
		state = aState; 
		simulation = aNonDistSimulation; 
	}

	@Override
	public boolean executeSimulationCommand(String command) {
		ProposalLearnedNotificationReceived.newCase(this, CommunicationStateNames.COMMAND, -1, command);
		if (Util.getBroadcastModeFromState() == BroadcastMode.ATOMIC) {
			simulation.setConnectedToSimulation(true);
			simulation.processCommand(command);
			simulation.setConnectedToSimulation(false);
		} else {
			simulation.processCommand(command);
		}
		return true; 
	}
	
	@Override
	public boolean executeMetaStateChange(BroadcastMode mode, IPCMechanism mech) throws RemoteException {
		state.setIPCMechanism(mech);
		state.setAtomicBroadcast(mode == BroadcastMode.ATOMIC);
		state.updateSimulationConnectivity();
		return false;
	}

	@Override
	public boolean acceptProposalRmi(boolean isMetaStateChange) throws RemoteException {
		if (state.isRejectMetaStateChange()) {
			return false;
		} else {
			if (isMetaStateChange) {
				state.setAtomicBroadcast(null);
				state.setIPCMechanism(null);
			}
			return true; 
		}
	}

	@Override
	public boolean acceptProposalGipc(boolean isMetaStateChange) throws RemoteException {
		if (isMetaStateChange) {
			state.setAtomicBroadcast(null);
			state.setIPCMechanism(null);
		}
		boolean accepted = !state.isRejectMetaStateChange();
		return accepted;
	}


}
