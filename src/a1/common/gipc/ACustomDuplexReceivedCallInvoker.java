package a1.common.gipc;

import inputport.datacomm.duplex.DuplexInputPort;
import inputport.rpc.RPCRegistry;
import inputport.rpc.duplex.ADuplexReceivedCallInvoker;
import inputport.rpc.duplex.LocalRemoteReferenceTranslator;

public class ACustomDuplexReceivedCallInvoker extends ADuplexReceivedCallInvoker {

	public ACustomDuplexReceivedCallInvoker(LocalRemoteReferenceTranslator aLocalRemoteReferenceTranslator,
			DuplexInputPort<Object> aReplier, RPCRegistry theRPCRegistry) {
		super(aLocalRemoteReferenceTranslator, aReplier, theRPCRegistry);
	}

	@Override 
	protected void handleProcedureReturn(String sender, Exception e) {
		super.handleFunctionReturn(sender, null, null, e);
	}
	
}
