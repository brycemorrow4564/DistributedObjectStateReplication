package a1.common.message;

import java.io.Serializable;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;

public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2659538282398928694L;

	protected ProposalType type;
	
	protected IPCMechanism 			mechToSet = null; 
	protected BroadcastMode 			bModeToSet = null;
	protected ConsensusAlgorithm   	consAlgToSet = null; 
	protected String 				commandToExecute = null; 
	
	protected String 				rpcRegistryKey; 
	
	//Necessary parameter 
	private IPCMechanism 		sIpcMech = null; 
	private ConsensusAlgorithm 	sConsAlg = null; 
	private BroadcastMode 		sBMode = null; 
	
	public Message(ProposalType type, BroadcastMode sBMode, IPCMechanism sIpcMech, ConsensusAlgorithm sConsAlg) {
		this.type = type; 
		this.sBMode = sBMode; 
		this.sIpcMech = sIpcMech; 
		this.sConsAlg = sConsAlg; 
	}
	
	public String toString() {
		return  "SenderBroadcastMode: " + sBMode.name() + 
				"\nSenderIPCMechanism: " + sIpcMech.name() + "\nSenderConsensusAlgorithm: " + sConsAlg.name() + 
				"\nProposalType: " + type.name() + 
				"\nIPCMechToSet: " + (mechToSet != null ? mechToSet.name() : "none") + 
				"\nBModeToSet: " + (bModeToSet != null ? bModeToSet.name() : "none") + 
				"\nConsAlgToSet: " + (consAlgToSet != null ? consAlgToSet.name() : "none") + 
				"\nSimCmdToExecute: " + (commandToExecute != null ? commandToExecute : "none");
	}
	
	public BroadcastMode getSenderBMode() { return sBMode; }
	public IPCMechanism getSenderIPCMechanism() { return sIpcMech; }
	public ConsensusAlgorithm getSenderConsensusAlgorithm() { return sConsAlg; } 
	public ProposalType getType() { return type; }
	public IPCMechanism getIpcMechToSet() { return mechToSet; }
	public BroadcastMode getBModeToSet() { return bModeToSet; }
	public ConsensusAlgorithm getConsAlgToSet() { return consAlgToSet; }
	public String getCommandToExecute() { return commandToExecute; } 
	public String getRpcRegistryKey() { return rpcRegistryKey; } 
	
	public void setIpcMechToSet(IPCMechanism mech) { mechToSet = mech; }
	public void setBModeToSet(BroadcastMode mode) { bModeToSet = mode; }
	public void setConsAlgToSet(ConsensusAlgorithm alg) { consAlgToSet = alg; }
	public void setCommandToExecute(String cmd) { commandToExecute = cmd; } 
	
	public void setRpcRegistryKey(String aRpcRegistryKey) { rpcRegistryKey = aRpcRegistryKey; } 
	
}
