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
	protected ClientCommunicator communicator; 
	
	public ClientState(SimulationClient client, ClientCommunicator communicator) {
		this.client = client; 
		this.communicator = communicator; 
	}
	
	public void updateSimulationConnectivity() {
		client.updateSimulationConnectivity(Util.getBroadcastModeFromState());
	}
	
	@Override
	public void localProcessingOnly(boolean newValue) {
		super.localProcessingOnly(newValue);
		updateSimulationConnectivity();
	}
	
	private void handleBroadcastMetaState(BroadcastMode newMode, IPCMechanism newMech) {
		Message msg = new Message(MsgType.CTS_Proposal);
		msg.setProposalType(ProposalType.MetaStateChange);
		msg.setBModeToSet(newMode);
		msg.setIpcMechToSet(newMech);
		communicator.sendMessageToServer(msg);
	}
	
	@Override
	public void atomicBroadcast(boolean newValue) {
		if (Util.getBroadcastModeFromState() == null) {
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
		if (Util.getIpcMechanismFromState() == null) {
			return; 
		}
		if (ClientStateFactory.getState().isBroadcastMetaState()) {
			BroadcastMode newMode = Util.getBroadcastModeFromState(); 
			handleBroadcastMetaState(newMode, newMech);
		} else {
			super.ipcMechanism(newMech); 
		}
	}
	
	@Override
	public void simulationCommand(String aCommand) {
		super.simulationCommand(aCommand);
		client.getSimulation().setInputString(aCommand); 
	}

	@Override
	public void quit(int aCode) {
		client.exitWithCode(aCode);
	}
	
	@Override
	public void experimentInput() {
		int numCommands = 1000;
		String[] commands = Util.generateRandomSimulationCommands(numCommands); 
		long startTime = (long) System.nanoTime(); 
		PerformanceExperimentStarted.newCase(this, startTime, 0);
		for (String c : commands) { simulationCommand(c); }
		long endTime = (long) System.nanoTime(); 
		PerformanceExperimentEnded.newCase(this, startTime, endTime, endTime - startTime, 0); 
		String experimentDescription = "Mode: " + Util.getBroadcastModeFromState().name() + "\nDuration (seconds): " + ((endTime - startTime) / Math.pow(10, 9)); 
		client.addToExperimentLog(experimentDescription); 
		System.out.println(experimentDescription);	
	}

}
