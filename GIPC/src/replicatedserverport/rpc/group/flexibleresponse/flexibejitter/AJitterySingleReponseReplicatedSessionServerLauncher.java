package replicatedserverport.rpc.group.flexibleresponse.flexibejitter;

import port.PortLauncherSupport;
import port.jitter.AJitterControllingPortLauncherSupport;
import port.sessionserver.ServerPortDescription;
import replicatedserverport.rpc.group.flexibleresponse.flexible.ASingleReponseReplicatedSessionServerLauncher;

public  class AJitterySingleReponseReplicatedSessionServerLauncher extends ASingleReponseReplicatedSessionServerLauncher{
//	public static String SESSION_SERVER_NAME = "Sessions Server";
//	public static String RELAYER_NAME = "Relayer";

	
	public AJitterySingleReponseReplicatedSessionServerLauncher(
			ServerPortDescription aServerPortDescription) {
		super(aServerPortDescription);
	}

	protected PortLauncherSupport getJitterPortLauncherSupport() {	
		return new AJitterControllingPortLauncherSupport();
	}
	

	

}
