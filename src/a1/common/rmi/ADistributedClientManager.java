package a1.common.rmi;

import java.rmi.RemoteException;

import a1.client.ClientCommunicatorFactory;
import a1.client.ClientState;
import a1.client.ClientStateFactory;
import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.CTS_ProposalResponse;
import a1.common.message.Message;
import a1.common.message.STC_ProposalAcceptRequest;
import a1.common.message.STC_ProposalExecute;
import stringProcessors.HalloweenCommandProcessor;
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
	public void passMsgToClient(Message msg) throws RemoteException {
		STC_ProposalAcceptRequest accReq = null; 
		STC_ProposalExecute propExec = null; 
		try { accReq = (STC_ProposalAcceptRequest) msg; } catch (Exception e) {}
		try { propExec = (STC_ProposalExecute) msg; } catch (Exception e) {}
		ClientState state = ClientStateFactory.getState();
		if (accReq != null) {
			//AcceptRequest received from server.
			Message responseMsg; 
			if (state.isRejectMetaStateChange()) {
				responseMsg = new CTS_ProposalResponse(msg.getType(), false, 
						msg.getSenderBMode(), msg.getSenderIPCMechanism(), null);
			} else {
				responseMsg = new CTS_ProposalResponse(msg.getType(), true, 
						msg.getSenderBMode(), msg.getSenderIPCMechanism(), null);
			}
			ClientCommunicatorFactory.getCommunicator().sendMessageToServer(responseMsg);
		} else { 
			//ProposalExecute received from server
			switch (msg.getType()) {
				case MetaStateChange: 
					this.setBroadcastMode(msg.getBModeToSet());
					this.setIpcMechanism(msg.getIpcMechToSet());
					break; 
				case SimulationCommand: 
					this.executeSimulationCommand(msg.getCommandToExecute());
					break; 
			}
		}
	}

	private void executeSimulationCommand(String command) {
		ProposalLearnedNotificationReceived.newCase(this, CommunicationStateNames.COMMAND, -1, command);
		if (Util.getBroadcastModeFromState() == BroadcastMode.ATOMIC) {
			simulation.setConnectedToSimulation(true);
			simulation.processCommand(command);
			simulation.setConnectedToSimulation(false);
		} else {
			simulation.processCommand(command);
		}
		System.out.println("RMI / GIPC: Executed simulation command");
	}
	
	private void setBroadcastMode(BroadcastMode mode) {
		ProposalLearnedNotificationReceived.newCase(this, CommunicationStateNames.BROADCAST_MODE, -1, mode == BroadcastMode.ATOMIC);
		state.setAtomicBroadcast(mode == BroadcastMode.ATOMIC);
		state.updateSimulationConnectivity();
		System.out.println("RMI / GIPC: Broadcast mode set to " + mode.name());
	}

	public void setIpcMechanism(IPCMechanism mech) {
		ProposalLearnedNotificationReceived.newCase(this, CommunicationStateNames.IPC_MECHANISM, -1, mech);
		state.setIPCMechanism(mech);
		System.out.println("RMI / GIPC: IpcMechanism set to " + mech.name());
	}
}
