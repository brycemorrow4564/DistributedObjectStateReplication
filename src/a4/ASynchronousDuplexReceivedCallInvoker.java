package a4;

import inputport.datacomm.duplex.DuplexInputPort;
import inputport.rpc.RPCRegistry;
import inputport.rpc.duplex.ADuplexReceivedCallInvoker;
import inputport.rpc.duplex.LocalRemoteReferenceTranslator;

public class ASynchronousDuplexReceivedCallInvoker extends ADuplexReceivedCallInvoker {

	public ASynchronousDuplexReceivedCallInvoker(LocalRemoteReferenceTranslator aLocalRemoteReferenceTranslator,
			DuplexInputPort<Object> aReplier, RPCRegistry theRPCRegistry) {
		super(aLocalRemoteReferenceTranslator, aReplier, theRPCRegistry);
	}
	
	@Override
	protected void handleProcedureReturn(String sender, Exception e) {
		super.handleFunctionReturn(sender, null, Object.class, e);
	}

}
