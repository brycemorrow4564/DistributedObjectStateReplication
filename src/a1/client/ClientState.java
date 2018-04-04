package a1.client;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.CTS_Proposal;
import a1.common.message.Message;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import a1.util.Util;
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
	
	public Message generateMetaStateMessage(ProposalType type) {
		return new CTS_Proposal(type, BroadcastMode.ATOMIC, IPCMechanism.RMI, this.getConsensusAlgorithm());
	}
	
	@Override
	public void atomicBroadcast(boolean newValue) {
		if (ClientStateFactory.getState().isBroadcastMetaState()) { 
			BroadcastMode bMode = newValue ? BroadcastMode.ATOMIC : BroadcastMode.NON_ATOMIC; 
			IPCMechanism oldIpcMech = this.getIPCMechanism(); 
			this.setIPCMechanism(IPCMechanism.RMI);
			this.setAtomicBroadcast(true);
			Message msg = generateMetaStateMessage(ProposalType.BroadcastModeChange);
			msg.setBModeToSet(bMode);
			communicator.sendMessageToServer(msg);
			this.setIPCMechanism(oldIpcMech);
		} else {
			super.atomicBroadcast(newValue);
			updateSimulationConnectivity();
		}
	}

	@Override
	public void ipcMechanism(IPCMechanism newMech) {
		if (ClientStateFactory.getState().isBroadcastMetaState()) {
			IPCMechanism oldIpcMech = this.getIPCMechanism(); 
			this.setIPCMechanism(IPCMechanism.RMI);
			Message msg = generateMetaStateMessage(ProposalType.IpcMechanismChange);
			msg.setIpcMechToSet(newMech);
			communicator.sendMessageToServer(msg); 
		} else {
			super.ipcMechanism(newMech); 
		}
	}
	
	@Override
	public void consensusAlgorithm(ConsensusAlgorithm alg) {
		if (ClientStateFactory.getState().isBroadcastMetaState()) { 
			IPCMechanism oldIpcMech = this.getIPCMechanism(); 
			this.setIPCMechanism(IPCMechanism.RMI);
			Message msg = generateMetaStateMessage(ProposalType.ConsensusAlgorithmChange);
			msg.setConsAlgToSet(alg);
			communicator.sendMessageToServer(msg);
			this.setIPCMechanism(oldIpcMech);
		} else {
			super.consensusAlgorithm(alg);
			updateSimulationConnectivity();
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
