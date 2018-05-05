package a1.common.message;

import java.io.Serializable;

import a1.common.InitialConfigurations.BroadcastMode;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;

public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1651702018031616149L;

	public enum MsgType { CTS_Proposal, CTS_ProposalResponse, STC_ProposalAcceptRequest, STC_ProposalExecute }
	public enum ProposalType { MetaStateChange, SimulationCommand };
	
	protected MsgType 				msgType; 
	protected ProposalType			propType;
	protected IPCMechanism 			mechToSet = null; 
	protected BroadcastMode 			bModeToSet = null;
	protected ConsensusAlgorithm   	consAlgToSet = null; 
	protected String 				commandToExecute = null; 
	protected boolean 				accepted; 
	protected String 				rpcRegistryKey = null; 
	protected IPCMechanism 			sIpcMech = null; 
	protected ConsensusAlgorithm 	sConsAlg = null; 
	protected BroadcastMode 			sBMode = null; 
	
	public Message(MsgType type) {
		this.msgType = type;
	}
	
	public String toString() {
		return  "\nMessageType: " + 				(msgType != null ? msgType.name() : "none") + 
				"\nRpcRegistryKey: "	+			(rpcRegistryKey != null ? rpcRegistryKey : "none") + 
				"\nSenderBroadcastMode: " + 		(sBMode != null ? sBMode.name() : "none") + 
				"\nSenderIPCMechanism: " + 		(sIpcMech != null ? sIpcMech.name() : "none") + 
				"\nSenderConsensusAlgorithm: " + (sConsAlg != null ? sConsAlg.name() : "none") + 
				"\nProposalType: " + 			(propType != null ? propType.name() : "none") + 
				"\nIPCMechToSet: " +				(mechToSet != null ? mechToSet.name() : "none") + 
				"\nBModeToSet: " + 				(bModeToSet != null ? bModeToSet.name() : "none") + 
				"\nConsAlgToSet: " + 			(consAlgToSet != null ? consAlgToSet.name() : "none") + 
				"\nSimCmdToExecute: " + 			(commandToExecute != null ? commandToExecute : "none");
	}
	
	public void setSenderProperties(BroadcastMode sBMode, IPCMechanism sIpcMech, ConsensusAlgorithm sConsAlg) {
		this.sBMode = sBMode; 
		this.sIpcMech = sIpcMech; 
		this.sConsAlg = sConsAlg; 
	}
	
	public BroadcastMode getSenderBMode() { return sBMode; }
	public IPCMechanism getSenderIPCMechanism() { return sIpcMech; }
	public ConsensusAlgorithm getSenderConsensusAlgorithm() { return sConsAlg; }
	public MsgType getMsgType() { return msgType; } 
	public ProposalType getPropType() { return propType; }
	public IPCMechanism getIpcMechToSet() { return mechToSet; }
	public BroadcastMode getBModeToSet() { return bModeToSet; }
	public ConsensusAlgorithm getConsAlgToSet() { return consAlgToSet; }
	public boolean getAccepted() { return accepted; } 
	public String getCommandToExecute() { return commandToExecute; } 
	public String getRpcRegistryKey() { return rpcRegistryKey; } 
	
	public void setAccepting(boolean accepting) { accepted = accepting; } 
	public void setSConsAlg(ConsensusAlgorithm consAlg) { sConsAlg = consAlg; }
	public void setIpcMechToSet(IPCMechanism mech) { mechToSet = mech; }
	public void setBModeToSet(BroadcastMode mode) { bModeToSet = mode; }
	public void setCommandToExecute(String cmd) { commandToExecute = cmd; } 
	public void setProposalType(ProposalType aType) { propType = aType; }  
	public void setSenderBMode(BroadcastMode bMode) { sBMode = bMode; } 
	public void setSenderIpcMech(IPCMechanism mech) { sIpcMech = mech; } 
	
	public void setRpcRegistryKey(String aRpcRegistryKey) { rpcRegistryKey = aRpcRegistryKey; } 
	
}
