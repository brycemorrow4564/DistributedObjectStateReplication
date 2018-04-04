package a1.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import util.interactiveMethodInvocation.IPCMechanism;

public interface DistributedServerRelayer extends Remote {
	public void passMsgToServer(Message msg) throws RemoteException; 
}
