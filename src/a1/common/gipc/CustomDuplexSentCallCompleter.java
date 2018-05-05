package a1.common.gipc;

import inputport.datacomm.duplex.object.explicitreceive.AReceiveReturnMessage;
import inputport.rpc.duplex.ADuplexSentCallCompleter;
import inputport.rpc.duplex.DuplexRPCInputPort;
import inputport.rpc.duplex.LocalRemoteReferenceTranslator;
import inputport.rpc.duplex.RPCReturnValue;
import util.trace.port.rpc.RemoteCallReceivedReturnValue;
import util.trace.port.rpc.RemoteCallWaitingForReturnValue;

public class CustomDuplexSentCallCompleter extends ADuplexSentCallCompleter {
	
	public CustomDuplexSentCallCompleter(DuplexRPCInputPort anInputPort, LocalRemoteReferenceTranslator aTranslator) {
		super(anInputPort, aTranslator);
	}
	
	@Override
	public Object waitForReturnValue(String aRemoteEndPoint) {
		Object val;
		while (true) {
			val = ((DuplexRPCInputPort) inputPort).receive(aRemoteEndPoint);		
			if (((AReceiveReturnMessage) val).getMessage() instanceof RPCReturnValue) { break; } 
		}
		return val;
	}

	@Override
	protected Object getReturnValueOfRemoteProcedureCall(String aRemoteEndPoint, Object aMessage) {
		RemoteCallWaitingForReturnValue.newCase(this);
		Object val = waitForReturnValue(aRemoteEndPoint);
		RemoteCallReceivedReturnValue.newCase(this, val);
		return val;
	}

}
