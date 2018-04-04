package a1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import util.annotations.Tags;
import util.tags.DistributedTags;
import util.trace.port.rpc.rmi.RMIRegistryCreated;

@Tags({DistributedTags.REGISTRY, DistributedTags.RMI})
public class RegistryLauncher {
	
	public RegistryLauncher() {}
	
	private void createRegistry() {
		try {
			int registryPort = 1099; 
			RMIRegistryCreated.newCase(this, registryPort);
			LocateRegistry.createRegistry(registryPort);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RegistryLauncher rl = new RegistryLauncher(); 
		rl.createRegistry(); 
	}
}
