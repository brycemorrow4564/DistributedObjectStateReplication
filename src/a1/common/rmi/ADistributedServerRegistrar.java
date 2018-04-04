package a1.common.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ADistributedServerRegistrar implements DistributedServerRegistrar {

	private static ArrayList<String> registeredClientIds; 
	private static int clientNum = 1; 
	
	public ADistributedServerRegistrar() {
		registeredClientIds = new ArrayList<String>(); 
	}
	
	public ArrayList<String> getRegisteredClientIds() { return registeredClientIds; }
	
	@Override
	public String registerClientsRemoteObjects() throws RemoteException {
		System.out.println("Registering client " + Integer.toString(clientNum) + " on the server");
		String managerId = "Client" + Integer.toString(clientNum) + "Manager";
		clientNum += 1; 
		registeredClientIds.add(managerId);
		return managerId; 
	}

}
