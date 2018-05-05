package a4;

import inputport.InputPort;
import inputport.rpc.duplex.LocalRemoteReferenceTranslator;
import inputport.rpc.duplex.RPCReturnValue;
import inputport.rpc.duplex.RPCReturnValueQueue;

public class SynchronousSentCallCompleter extends ACustomDuplexSentCallCompleter {

	public SynchronousSentCallCompleter(InputPort anInputPort, LocalRemoteReferenceTranslator aRemoteHandler) {
		super(anInputPort, aRemoteHandler);
	}
	
	@Override
	protected Object getReturnValueOfRemoteProcedureCall(String aRemoteEndPoint, Object aMessage) {
		return super.getReturnValueOfRemoteFunctionCall(aRemoteEndPoint, aMessage);
	}
	
	@Override
	protected void returnValueReceived(String source, Object message) {
		//do nothing 
	}

}
