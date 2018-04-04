package port.old;


import inputport.InputPort;
import inputport.datacomm.ReceiveListener;
import inputport.datacomm.simplex.object.example.AnEchoingObjectReceiveListener;
import inputport.rpc.RPCProxyGenerator;
import inputport.rpc.RPCRegistry;
import inputport.rpc.duplex.ADuplexRPCInputPortLauncherSupport;
import inputport.rpc.duplex.DuplexRPCClientInputPort;
import inputport.rpc.duplex.DuplexRPCInputPortSelector;
import inputport.rpc.duplex.example.ADuplexCounterAndSenderAwareSummer;
import inputport.rpc.duplex.example.ARegisteredEchoer;
import inputport.rpc.duplex.example.AnAnotherCounter;
import inputport.rpc.duplex.example.AnUnregisteredEchoer;
import inputport.rpc.duplex.example.AnotherCounter;
import inputport.rpc.duplex.example.AnotherEchoer;
import inputport.rpc.duplex.example.DuplexCounterAndSenderAwareSummer;
import inputport.rpc.simplex.example.ASimplexRPCClientInputPortLauncher;

import java.util.Scanner;

import port.PortLauncherSupport;





public class ADuplexRPCClientInputPortLauncher extends ASimplexRPCClientInputPortLauncher  {
	protected DuplexCounterAndSenderAwareSummer adderProxy;
	protected RPCProxyGenerator rpcProxyGenerator; 
	public ADuplexRPCClientInputPortLauncher(String aClientName) {
		super(aClientName);
	}

	protected AnotherEchoer registerdEchoer;
	protected AnotherEchoer unregisteredEchoer;
	protected AnotherCounter counter;
	protected PortLauncherSupport getPortLauncherSupport() {
		return new ADuplexRPCInputPortLauncherSupport();
	}
	@Override
	protected InputPort getPort() {
		return DuplexRPCInputPortSelector.createDuplexRPCClientInputPort(
				SERVER_HOST, SERVER_ID, SERVER_NAME, 	clientName);
	}
	
	protected DuplexRPCClientInputPort duplexRPCClientInputPort() {
		return ((DuplexRPCClientInputPort) mainPort);
	}
	

	@Override
	protected void registerRemoteObjects()  {
		RPCRegistry anRPCRegistry = (RPCRegistry) mainPort;
		registerdEchoer = createAndRegisterEchoer(anRPCRegistry);
		unregisteredEchoer = createUnregisteredEchoer(anRPCRegistry); //does not stricltly belong in this method
		counter = createAndRegisterCounter(anRPCRegistry);
		
	}
	

	@Override
	protected ReceiveListener getReceiveListener (InputPort anInputPort) {
		return new AnEchoingObjectReceiveListener(anInputPort);
//		return null;
	}
	@Override
	protected void createProxies() {
		rpcProxyGenerator = duplexRPCClientInputPort().getRPCProxyGenerator();

		 adderProxy = (DuplexCounterAndSenderAwareSummer) 
				   rpcProxyGenerator.generateRPCProxy(null, ADuplexCounterAndSenderAwareSummer.class, null);
	}
	@Override
	public void createUI (InputPort aClientInputPort) {
		
//		RPCProxyGenerator rpcProxyGenerator = duplexRPCClientInputPort().getRPCProxyGenerator();
//		DuplexCounterAndSenderAwareSumComputerAndPrinter adderProxy = (DuplexCounterAndSenderAwareSumComputerAndPrinter) StaticRPCProxyGenerator.generateRPCProxy(aClientInputPort, null, DuplexCounterAndSenderAwareSumComputerAndPrinter.class, null);
//		DuplexCounterAndSenderAwareSumComputerAndPrinter adderProxy = (DuplexCounterAndSenderAwareSumComputerAndPrinter) 
//		   rpcProxyGenerator.generateRPCProxy(null, DuplexCounterAndSenderAwareSumComputerAndPrinter.class, null);

		String stringMessage1;
		String stringMessage2;
		Scanner in = new Scanner(System.in);	
		while (true) {
			System.out.println("Please enter two messages:");
		    stringMessage1  = in.nextLine();
		    stringMessage2 = in.nextLine();
		    counter.increment(1);
		    try {
		    	// synchronous
		    	Object retVal = adderProxy.sum(stringMessage1, stringMessage2);
		    	System.out.println("Remote sum:" + retVal);
		    	// sync or async depending on RPC
		    	adderProxy.printUppercase(stringMessage1);
		    	// sync or async depending on RPC, sync will cause deadlock
		    	System.out.println("Sending echoer:" + unregisteredEchoer);
		    	adderProxy.printSumAndCallBackProcedureAndFunction(unregisteredEchoer, stringMessage1, stringMessage2);
		    			
		    } catch (Exception e) {
		    		e.printStackTrace();
		    }
		}
	}	
	protected AnotherCounter createAndRegisterCounter(RPCRegistry aClientInputPort) {
		AnotherCounter counter = new AnAnotherCounter();
		aClientInputPort.register(counter);
		return counter;
	}	
	protected AnotherEchoer createAndRegisterEchoer(RPCRegistry aClientInputPort) {
		AnotherEchoer echoer = new ARegisteredEchoer();
		aClientInputPort.register(echoer);
		return echoer;
	}
	
	protected AnotherEchoer createUnregisteredEchoer(RPCRegistry aClientInputPort) {
		AnotherEchoer echoer = new AnUnregisteredEchoer();
//		aClientInputPort.register(Echoer.class, echoer);
		return echoer;
	}
	

	


}
