package a1.common.message;

import java.io.Serializable;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;

public class STC_ProposalAcceptRequest extends Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3570572791689528763L;

	public STC_ProposalAcceptRequest(ProposalType type, BroadcastMode sBMode, IPCMechanism sIpcMech, ConsensusAlgorithm sConsAlg) {
		super(type, sBMode, sIpcMech, sConsAlg);
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + "\n" + super.toString();
	}
	
}
