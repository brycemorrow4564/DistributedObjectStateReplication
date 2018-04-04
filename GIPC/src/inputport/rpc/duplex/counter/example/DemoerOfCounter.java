package inputport.rpc.duplex.counter.example;

import bus.uigen.pipe.MainClassLaunchingUtility;

public class DemoerOfCounter {
	public static void main(String args[]) {
		demo();
	}	
	public static void demo() {		
		Class[] classes = {
				ADuplexCounterServerLauncher.class,
				ADuplexCounterClientLauncher.class	
		};
		MainClassLaunchingUtility.createInteractiveLauncher(classes);
	}
}
