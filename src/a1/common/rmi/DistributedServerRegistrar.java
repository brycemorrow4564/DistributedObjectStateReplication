package a1.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DistributedServerRegistrar extends Remote {
	public String registerClientsRemoteObjects() throws RemoteException; 
}
