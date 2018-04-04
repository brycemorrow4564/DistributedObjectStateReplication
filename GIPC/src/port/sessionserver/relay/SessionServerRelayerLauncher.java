package port.sessionserver.relay;

import inputport.InputPort;
import port.PortLauncherSupport;
import port.relay.ARelayerLauncher;
import port.relay.RelayerLauncher;
import port.sessionserver.ASessionServerLauncher;




public class SessionServerRelayerLauncher extends ARelayerLauncher implements RelayerLauncher{;
	String sessionServerHost;
	String sessionServerId;
	String sessionServerName;
	String sessionName;
	public static String SESSION_SERVER_HOST = "localhost";
	public static void main (String args[]) {	
		(new SessionServerRelayerLauncher(RELAYER_ID, RELAYER_NAME, SESSION_SERVER_HOST, ASessionServerLauncher.SESSION_SERVER_ID, ASessionServerLauncher.SESSION_SERVER_NAME, null)).launch();

	}
	public  SessionServerRelayerLauncher (String aRelayerId, String aRelayerName, String aSessionServerHost, String aSessionServerId, String aSessionServerName, String aSessionName) {
		super(aRelayerId, aRelayerName);
		sessionServerHost = aSessionServerHost;
		sessionServerId = aSessionServerId;
		sessionServerName = aSessionServerName;
		sessionName = aSessionName;
	}	
	protected PortLauncherSupport getPortLauncherSupport() {
		return new ASessionServerRelayerLauncherSupport();
	}	
	protected InputPort getPort() {
		return SessionServerRelayerPortSelector.createRelayerPort(
				   relayerServerId, relayerServerName, sessionServerHost, sessionServerId, sessionServerName, 
				   sessionName);
	}
	
}
