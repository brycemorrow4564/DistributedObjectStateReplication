package inputport.rpc.duplex.example;

import bus.uigen.pipe.MainClassLaunchingUtility;

public class DemoerOfDuplexRPCInputPort {
	public static void main(String args[]) {
		demo();
	}
	
	public static void demo() {

		
		Class[] classes = {
				ADuplexRPCServerInputPortLauncher.class,
				AliceDuplexRPCInputPortLauncher.class,
				BobDuplexRPCInputPortLauncher.class
				
		};
		MainClassLaunchingUtility.createInteractiveLauncher(classes);
	}
	
	

}
