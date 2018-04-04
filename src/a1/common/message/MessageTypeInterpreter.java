package a1.common.message;

public interface MessageTypeInterpreter {
	public enum ProposalType { IpcMechanismChange, BroadcastModeChange, ConsensusAlgorithmChange, SimulationCommand };
}
