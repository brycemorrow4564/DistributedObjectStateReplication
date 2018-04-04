package a1.server;

import a1.common.InitialConfigurations.BroadcastMode;
import assignments.util.inputParameters.AnAbstractSimulationParametersBean;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;

public class ServerState extends AnAbstractSimulationParametersBean {

	private SimulationServer server;
	public static IPCMechanism oldIpcMech;
	public static BroadcastMode oldBMode;
	public static ConsensusAlgorithm oldConsAlg;

	public ServerState(SimulationServer server) {
		this.server = server;
	}

	public void setServer(SimulationServer server) {
		this.server = server;
	}

	public static IPCMechanism getOldIpcMech() {
		return oldIpcMech;
	}

	public static void setOldIpcMech(IPCMechanism oldIpcMech) {
		ServerState.oldIpcMech = oldIpcMech;
	}

	public static BroadcastMode getOldBMode() {
		return oldBMode;
	}

	public static void setOldBMode(BroadcastMode oldBMode) {
		ServerState.oldBMode = oldBMode;
	}

	public static ConsensusAlgorithm getOldConsAlg() {
		return oldConsAlg;
	}

	public static void setOldConsAlg(ConsensusAlgorithm oldConsAlg) {
		ServerState.oldConsAlg = oldConsAlg;
	}

}
