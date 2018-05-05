package a1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import util.annotations.Tags;
import util.tags.DistributedTags;
import util.trace.port.rpc.rmi.RMIRegistryCreated;

@Tags({DistributedTags.REGISTRY, DistributedTags.RMI})
public class RegistryLauncher {
	
	public RegistryLauncher() {}
	
	private void createRegistry() {
		try {
			RMIRegistryCreated.newCase(this, 1099);
			LocateRegistry.createRegistry(1099);
			Scanner scanner = new Scanner(System.in);
			while (true) { scanner.nextLine(); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RegistryLauncher rl = new RegistryLauncher(); 
		rl.createRegistry(); 
	}
}
