package a1.server.gipc;

import a1.common.Communicator;
import a1.common.rpc.DistributedServerRelayer;
import a1.server.ServerCommunicator;
import a1.server.rmi.RMIServerCommunicator;
import a4.GIPCInputPortFactory;
import inputport.ConnectionListener;
import inputport.datacomm.ReceiveListener;
import inputport.datacomm.duplex.object.DuplexObjectInputPortSelector;
import inputport.rpc.GIPCLocateRegistry;
import inputport.rpc.GIPCRegistry;
import inputport.rpc.duplex.DuplexRPCServerInputPort;
import util.interactiveMethodInvocation.IPCMechanism;

public class GIPCServerCommunicator implements Communicator {
	
	protected GIPCRegistry 		gipcRegistry; 
	protected int 				gipcPort; 
	protected ServerCommunicator parentCommunicator; 
	
	protected DuplexRPCServerInputPort duplexRPCServerInputPort;
	protected ReceiveListener receiveListener;
	protected ConnectionListener connectionListener; 
	
	public GIPCServerCommunicator(ServerCommunicator parentCommunicator, int gipcPort) {
		this.gipcPort = gipcPort; 
		this.parentCommunicator = parentCommunicator; 
		initAsync(); //setup related to asynchronous rpc 
		exportServerRelayer(); 
		initSync(); //setup related to synchronous rpc 
		
	}
	
	public void initSync() {
		setFactories();
		setPort(); 
	}
	
	public static void setFactories() {
		/*
		 * Determines the ports  for sending and
		 * receiving objects. Needed for sync receive.
		 */
		DuplexObjectInputPortSelector.setDuplexInputPortFactory(new GIPCInputPortFactory());
		/*
		 * Two alternatives for received call invoker factory, with one
		 * commented out. This factory determines the object that 
		 * actually calls a method of a remote object in response to
		 * a received message
		 */
//		DuplexReceivedCallInvokerSelector.setReceivedCallInvokerFactory(
//				new ACustomDuplexReceivedCallInvokerFactory());
		/*
		 * Determines the object that processes return value, if any, of
		 * a remote call
		 */
//		DuplexSentCallCompleterSelector.setDuplexSentCallCompleterFactory(
//				new ACustomSentCallCompleterFactory());
	}
	
	public void setPort() {
		duplexRPCServerInputPort = gipcRegistry.getRPCServerPort();
	}
	
	public void initAsync() {
		gipcRegistry = GIPCLocateRegistry.createRegistry(gipcPort);
		connectionListener = new GIPCConnectionListener(gipcRegistry.getInputPort());
		gipcRegistry.getInputPort().addConnectionListener(connectionListener);
	}
	
	public void exportServerRelayer() {
		RMIServerCommunicator rmiComm = (RMIServerCommunicator) parentCommunicator.getSubCommunicatorOfType(IPCMechanism.RMI);
		DistributedServerRelayer relayer = rmiComm.getServerRelayer(); 
		gipcRegistry.rebind("ServerRelayer", relayer);
	}

}
