package a1.common.rpc;

import java.rmi.Remote;
import java.rmi.RemoteException;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import util.interactiveMethodInvocation.IPCMechanism;

public interface DistributedServerRelayer extends Remote {
		 
	/* The clients are calling methods on this object
	 * Synchronous or asynchronous?
	 * 
	 * Asynchronous: our client wants to propose a change to the server.
	 * 				client passes object and server interprets it. 
	 * 					--> asyncPropose(Message msg) 
	 * Synchronous: our client either wants to make an initial proposal
	 * 				for a state change, or is responding to a server asking
	 * 				if we accept a pending state change. 
	 * 					--> syncProposal(Message msg) 
	 * 					--> syncProposalRespond(Message msg) */
	
	public void asyncProposeRmi(Message msg) throws RemoteException; 
	public void syncProposeRmi(Message msg) throws RemoteException;
	
	public void asyncProposeGipc(Message msg) throws RemoteException; 
	public boolean syncProposeGipc(Message msg) throws RemoteException; //ret val so we can force synchronous execution

	public String rmiClientDeposit(DistributedClientManager clientManager) throws RemoteException; 
	public void gipcClientDeposit(String id, DistributedClientManager clientManager) throws RemoteException;
}
