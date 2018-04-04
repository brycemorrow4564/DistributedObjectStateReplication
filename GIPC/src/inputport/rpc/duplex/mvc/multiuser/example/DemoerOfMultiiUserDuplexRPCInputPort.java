package inputport.rpc.duplex.mvc.multiuser.example;

import bus.uigen.pipe.MainClassLaunchingUtility;

public class DemoerOfMultiiUserDuplexRPCInputPort {
	public static void main(String args[]) {
		demo();
	}	
	public static void demo() {		
		Class[] classes = {
				AMultiUserDuplexRPCServerMVCLauncher.class,
				AliceDuplexRPCClientMVCLauncher.class,	
				BobDuplexRPCClientMVCLauncher.class	
		};
		MainClassLaunchingUtility.createInteractiveLauncher(classes);
	}
}
