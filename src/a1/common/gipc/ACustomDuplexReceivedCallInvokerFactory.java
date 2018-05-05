package a1.common.gipc;

import inputport.datacomm.duplex.DuplexInputPort;
import inputport.rpc.RPCRegistry;
import inputport.rpc.duplex.ADuplexReceivedCallInvokerFactory;
import inputport.rpc.duplex.DuplexReceivedCallInvoker;
import inputport.rpc.duplex.LocalRemoteReferenceTranslator;

public class ACustomDuplexReceivedCallInvokerFactory extends ADuplexReceivedCallInvokerFactory {

	@Override
	public DuplexReceivedCallInvoker createDuplexReceivedCallInvoker(
			LocalRemoteReferenceTranslator aRemoteHandler,
			DuplexInputPort<Object> aReplier, RPCRegistry anRPCRegistry) {
		return new ACustomDuplexReceivedCallInvoker(aRemoteHandler, aReplier, anRPCRegistry);
	}
	
}
