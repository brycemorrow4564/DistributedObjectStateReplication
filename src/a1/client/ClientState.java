package a1.client;

import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import a1.common.message.Message.ProposalType;
import assignments.util.inputParameters.AnAbstractSimulationParametersBean;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;
import util.trace.port.PerformanceExperimentEnded;
import util.trace.port.PerformanceExperimentStarted;

public class ClientState extends AnAbstractSimulationParametersBean {
	
	protected SimulationClient client; 
	
	public ClientState(SimulationClient client) {
		this.client = client; 
	}
	
	public SimulationClient getClient() {
		return client; 
	}
	
	public void updateSimulationConnectivity() {
		BroadcastMode bMode = Util.getBroadcastModeFromState(); 
		if (bMode == null) {
			System.out.println("Error tried to update simulation connectivity but broadcast mode was null");
		}
		client.updateSimulationConnectivity(bMode);
	}
	
	@Override
	public void localProcessingOnly(boolean newValue) {
		super.localProcessingOnly(newValue);
		updateSimulationConnectivity(); 
	}
	
	private void handleBroadcastMetaState(BroadcastMode newMode, IPCMechanism newMech) {
		Message msg = new Message(MsgType.CTS_Proposal);
		msg.setProposalType(ProposalType.MetaStateChange);
		msg.setSenderIpcMech(IPCMechanism.RMI);
		msg.setBModeToSet(newMode);
		msg.setIpcMechToSet(newMech);
		client.getCommunicator().sendMessageToServer(msg);
	}
	
	@Override
	public void atomicBroadcast(boolean newValue) {
		if (waiting()) { 
			return; 
		}
		if (ClientStateFactory.getState().isBroadcastMetaState()) { 
			BroadcastMode newMode = newValue ? BroadcastMode.ATOMIC : BroadcastMode.NON_ATOMIC; 
			IPCMechanism newMech = Util.getIpcMechanismFromState(); 
			handleBroadcastMetaState(newMode, newMech);
		} else {
			super.atomicBroadcast(newValue);
			updateSimulationConnectivity();
		}
	}

	@Override
	public void ipcMechanism(IPCMechanism newMech) {
		if (waiting())  { 
			return; 
		}  
		if (ClientStateFactory.getState().isBroadcastMetaState()) {
			BroadcastMode newMode = Util.getBroadcastModeFromState(); 
			handleBroadcastMetaState(newMode, newMech);
		} else {
			super.ipcMechanism(newMech); 
		}
	}
	
	public boolean waiting() {
		return Util.getBroadcastModeFromState() == null || Util.getIpcMechanismFromState() == null;
	}
	
	@Override
	public void simulationCommand(String aCommand) {
		super.simulationCommand(aCommand);
		if (Util.getBroadcastModeFromState() == BroadcastMode.LOCAL) {
			client.getSimulation().setInputString(aCommand);
			return; 
		}
		if (waiting()) return; 
		if (aCommand.equals("experiment")) {
			client.printExperiments();
		} else {
			client.getSimulation().setInputString(aCommand); 
		}
	}

	@Override
	public void quit(int aCode) {
		client.exitWithCode(aCode);
	}
	
	@Override
	public void experimentInput() {
		client.assignment3Experiment();
	}
	
}
