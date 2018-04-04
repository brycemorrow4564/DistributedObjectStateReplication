package a1.common.message;

import java.io.Serializable;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;

public class CTS_Proposal extends Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2566412798808295760L;

	public CTS_Proposal(ProposalType type, BroadcastMode sBMode, IPCMechanism sIpcMech, ConsensusAlgorithm sConsAlg) {
		super(type, sBMode, sIpcMech, sConsAlg); 
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + "\n" + super.toString(); 
	}
	
}
