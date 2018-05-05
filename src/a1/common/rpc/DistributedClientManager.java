package a1.common.rpc;

import java.rmi.Remote;
import java.rmi.RemoteException;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import util.interactiveMethodInvocation.IPCMechanism;

public interface DistributedClientManager extends Remote {
	
	public boolean executeMetaStateChange(BroadcastMode newBMode, IPCMechanism newIpcMech) 
			throws RemoteException; 
	public boolean executeSimulationCommand(String command) 
			throws RemoteException; 
	public boolean acceptProposalRmi(boolean isMetaStateChange) 
			throws RemoteException;
	public boolean acceptProposalGipc(boolean isMetaStateChange) 
			throws RemoteException;
}
