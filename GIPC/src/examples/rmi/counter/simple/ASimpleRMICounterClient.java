package examples.rmi.counter.simple;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import examples.mvc.rmi.duplex.ADistributedInheritingRMICounter;
import examples.mvc.rmi.duplex.DistributedRMICounter;

public class ASimpleRMICounterClient implements SimpleCounterClient{
	public static void main (String[] args) {	
		try {
			Registry rmiRegistry = LocateRegistry.getRegistry(SERVER_HOST_NAME, SERVER_PORT);
			DistributedRMICounter counter = (DistributedRMICounter) rmiRegistry.lookup(COUNTER_NAME);			
			counter.increment(1);
			System.out.println (counter.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
