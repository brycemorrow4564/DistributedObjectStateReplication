package a1.server;

import a1.common.InitialConfigurations.BroadcastMode;
import assignments.util.inputParameters.AnAbstractSimulationParametersBean;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;

public class ServerState extends AnAbstractSimulationParametersBean {

	public static IPCMechanism oldIpcMech = null;
	public static BroadcastMode oldBMode = null;

	public ServerState(SimulationServer server) {}
	
	public static IPCMechanism getOldIpcMech() { return oldIpcMech == null ? IPCMechanism.RMI : oldIpcMech; }	
	public static BroadcastMode getOldBMode() { return oldBMode == null ? BroadcastMode.ATOMIC : oldBMode; }
	
	public static void setOldIpcMech(IPCMechanism oldIpcMech) { ServerState.oldIpcMech = oldIpcMech; }
	public static void setOldBMode(BroadcastMode oldBMode) { ServerState.oldBMode = oldBMode; }

}
