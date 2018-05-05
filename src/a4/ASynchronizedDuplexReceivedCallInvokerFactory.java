package a4;

import inputport.datacomm.duplex.DuplexInputPort;
import inputport.rpc.RPCRegistry;
import inputport.rpc.duplex.ADuplexReceivedCallInvokerFactory;
import inputport.rpc.duplex.DuplexReceivedCallInvoker;
import inputport.rpc.duplex.LocalRemoteReferenceTranslator;

public class ASynchronizedDuplexReceivedCallInvokerFactory extends ADuplexReceivedCallInvokerFactory {
	
	@Override
	public DuplexReceivedCallInvoker createDuplexReceivedCallInvoker(
			LocalRemoteReferenceTranslator aRemoteHandler,
			DuplexInputPort<Object> aReplier, RPCRegistry anRPCRegistry) {
		return new ASynchronousDuplexReceivedCallInvoker(aRemoteHandler, aReplier, anRPCRegistry);
	}
	
}
