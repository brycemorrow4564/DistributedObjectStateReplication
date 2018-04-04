package a1.common.rmi;

import java.rmi.RemoteException;

import a1.client.ClientState;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import stringProcessors.HalloweenCommandProcessor;
import util.interactiveMethodInvocation.IPCMechanism;

public class ADistributedClientManager implements DistributedClientManager {

	protected ClientState state; 
	private HalloweenCommandProcessor simulation; 
	
	public ADistributedClientManager(ClientState aState, HalloweenCommandProcessor aNonDistSimulation) {
		state = aState; 
		simulation = aNonDistSimulation; 
	}
	
	@Override
	public void passMsgToClient(Message msg) throws RemoteException {
		switch (msg.getType()) {
			case BroadcastModeChange: 
				this.setBroadcastMode(msg.getBModeToSet());
			case ConsensusAlgorithmChange: 
				break; 
			case IpcMechanismChange: 
				this.setIpcMechanism(msg.getIpcMechToSet());
			case SimulationCommand: 
				this.executeSimulationCommand(msg.getCommandToExecute());
		}
	}

	private void executeSimulationCommand(String command) {
		simulation.processCommand(command);
		System.out.println("RMI: Executed simulation command");
	}
	
	private void setBroadcastMode(BroadcastMode mode) {
		if (mode == BroadcastMode.LOCAL) {
			state.setLocalProcessingOnly(true);
		} else {
			state.setAtomicBroadcast(mode == BroadcastMode.ATOMIC);
		}
		state.updateSimulationConnectivity();
		System.out.println("RMI: Broadcast mode set to " + mode.name());
	}

	public void setIpcMechanism(IPCMechanism mech) {
		state.setIPCMechanism(mech);
		System.out.println("RMI: IpcMechanism set to " + mech.name());
	}
}
