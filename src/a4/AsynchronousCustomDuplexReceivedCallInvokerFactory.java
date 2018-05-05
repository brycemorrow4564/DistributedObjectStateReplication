package a4;

import inputport.datacomm.duplex.DuplexInputPort;
import inputport.rpc.RPCRegistry;
import inputport.rpc.duplex.AnAsynchronousSingleThreadDuplexReceivedCallInvoker;
import inputport.rpc.duplex.DuplexReceivedCallInvoker;
import inputport.rpc.duplex.LocalRemoteReferenceTranslator;

public class AsynchronousCustomDuplexReceivedCallInvokerFactory 
	extends ASynchronizedDuplexReceivedCallInvokerFactory {
	
	@Override
	public DuplexReceivedCallInvoker createDuplexReceivedCallInvoker(
			LocalRemoteReferenceTranslator aRemoteHandler,
			DuplexInputPort<Object> aReplier, RPCRegistry anRPCRegistry) {

		DuplexReceivedCallInvoker invoker = new ASynchronousDuplexReceivedCallInvoker(aRemoteHandler, aReplier, anRPCRegistry);
		return new AnAsynchronousSingleThreadDuplexReceivedCallInvoker(invoker);
	}
	
}
