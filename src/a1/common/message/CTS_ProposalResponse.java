package a1.common.message;

import java.io.Serializable;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;

public class CTS_ProposalResponse extends Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2709497822454576738L;
	private boolean propAccepted;
	
	public CTS_ProposalResponse(ProposalType type, boolean proposalAccepted, BroadcastMode sBMode, IPCMechanism sIpcMech, ConsensusAlgorithm sConsAlg) {
		super(type, sBMode, sIpcMech, sConsAlg); 
		propAccepted = proposalAccepted; 
	}

	public boolean isProposalAccepted() { return propAccepted; }; 
	
	public String toString() {
		return this.getClass().getSimpleName() + "\n" + super.toString() + "/nProposalAccepted: " + propAccepted; 
	}
}
