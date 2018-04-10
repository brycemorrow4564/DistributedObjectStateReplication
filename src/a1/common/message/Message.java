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
	protected ProposalType			type;
	protected IPCMechanism 			mechToSet = null; 
	protected BroadcastMode 			bModeToSet = null;
	protected ConsensusAlgorithm   	consAlgToSet = null; 
	protected String 				commandToExecute = null; 
	protected boolean 				accepted; 
	protected String 				rpcRegistryKey = null; 
	private IPCMechanism 			sIpcMech = null; 
	private ConsensusAlgorithm 		sConsAlg = null; 
	private BroadcastMode 			sBMode = null; 
	
	public Message(MsgType type) {
		this.msgType = type;
	}
	
	public String toString() {
		return  "\nMessageType: " + msgType.name() + 
				"\nSenderBroadcastMode: " + sBMode.name() + 
				"\nSenderIPCMechanism: " + sIpcMech.name() + 
				"\nSenderConsensusAlgorithm: " + sConsAlg.name() + 
				"\nProposalType: " + type.name() + 
				"\nIPCMechToSet: " + (mechToSet != null ? mechToSet.name() : "none") + 
				"\nBModeToSet: " + (bModeToSet != null ? bModeToSet.name() : "none") + 
				"\nConsAlgToSet: " + (consAlgToSet != null ? consAlgToSet.name() : "none") + 
				"\nSimCmdToExecute: " + (commandToExecute != null ? commandToExecute : "none");
	}
	
	public BroadcastMode getSenderBMode() { return sBMode; }
	public IPCMechanism getSenderIPCMechanism() { return sIpcMech; }
	public ConsensusAlgorithm getSenderConsensusAlgorithm() { return sConsAlg; } 
	public MsgType getMsgType() { return msgType; } 
	public ProposalType getType() { return type; }
	public IPCMechanism getIpcMechToSet() { return mechToSet; }
	public BroadcastMode getBModeToSet() { return bModeToSet; }
	public ConsensusAlgorithm getConsAlgToSet() { return consAlgToSet; }
	public Boolean getAccepted() { return accepted; } 
	public String getCommandToExecute() { return commandToExecute; } 
	public String getRpcRegistryKey() { return rpcRegistryKey; } 
	
	public void setAccepting(boolean accepting) { accepted = accepting; } 
	public void setIpcMechToSet(IPCMechanism mech) { mechToSet = mech; }
	public void setBModeToSet(BroadcastMode mode) { bModeToSet = mode; }
	public void setCommandToExecute(String cmd) { commandToExecute = cmd; } 
	public void setProposalType(ProposalType aType) { type = aType; }  
	public void setSenderBMode(BroadcastMode bMode) { sBMode = bMode; } 
	public void setSenderIpcMech(IPCMechanism mech) { sIpcMech = mech; } 
	
	public void setRpcRegistryKey(String aRpcRegistryKey) { rpcRegistryKey = aRpcRegistryKey; } 
	
}
