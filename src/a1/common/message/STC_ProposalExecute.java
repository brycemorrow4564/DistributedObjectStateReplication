package a1.common.message;

import java.io.Serializable;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;

public class STC_ProposalExecute extends Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4248333000156676021L;

	public STC_ProposalExecute(ProposalType type, BroadcastMode sBMode, IPCMechanism sIpcMech, ConsensusAlgorithm sConsAlg) {
		super(type, sBMode, sIpcMech, sConsAlg); 
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + "\n" + super.toString(); 
	}

}
