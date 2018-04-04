package sessionport.datacomm.group.object.flexible.example;

import port.sessionserver.ASessionServerLauncher;
import port.sessionserver.relay.ARelayerSupportingSessionServerLauncher;
import port.sessionserver.relay.SessionServerRelayerLauncher;
import port.sessionserver.relay.late.ALatecomerSessionServerLauncher;
import sessionport.rpc.duplex.relayed.example.AnAliceDuplexRPCRelayedSessionPort;
import bus.uigen.pipe.MainClassLaunchingUtility;

public class DemoerOfFlexibleGroupObjectSessionPort {
	public static void main(String args[]) {
		demo();
	}	
	public static void demo() {		
		Class[] classes = {
				ALatecomerSessionServerLauncher.class, // can also have relayer
				AnAliceGroupSessionPort.class,
				ABobGroupSessionPort.class,
				ACathyGroupSessionPort.class 
		};
		MainClassLaunchingUtility.createInteractiveLauncher(classes);
	}	

}
