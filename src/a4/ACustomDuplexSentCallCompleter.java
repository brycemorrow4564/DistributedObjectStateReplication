package a4;

import inputport.InputPort;
import inputport.datacomm.duplex.object.explicitreceive.AReceiveReturnMessage;
import inputport.rpc.duplex.ADuplexSentCallCompleter;
import inputport.rpc.duplex.DuplexRPCInputPort;
import inputport.rpc.duplex.LocalRemoteReferenceTranslator;
import inputport.rpc.duplex.RPCReturnValue;
import util.trace.port.rpc.ReceivedObjectTransformed;
import util.trace.port.rpc.RemoteCallReceivedReturnValue;
import util.trace.port.rpc.RemoteCallWaitingForReturnValue;

public class ACustomDuplexSentCallCompleter extends ADuplexSentCallCompleter {

	public ACustomDuplexSentCallCompleter(InputPort anInputPort, LocalRemoteReferenceTranslator aRemoteHandler) {
		super(anInputPort, aRemoteHandler);
	}

	@Override 
	protected Object waitForReturnValue(String aRemoteEndPoint) {
		Object retVal = null; 
		while (true) {
			try {
				//Using synchronous receive rather than directly acting on a bounded buffer.
				retVal = ((DuplexRPCInputPort) inputPort).receive(aRemoteEndPoint);  
				if (retVal == null) continue; 
				if (((AReceiveReturnMessage) retVal).getMessage() instanceof RPCReturnValue) {
					return ((RPCReturnValue) ((AReceiveReturnMessage) retVal)
											.getMessage()).getReturnValue(); 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
