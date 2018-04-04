package inputport.rpc.duplex;

import inputport.InputPort;
import inputport.datacomm.NamingSender;
import inputport.rpc.RemoteCall;
import inputport.rpc.simplex.ASimplexCallSendTrapper;
import util.trace.Tracer;
import util.trace.port.rpc.RemoteCallReturnValueDetermined;

public class ADuplexCallSendTrapper extends ASimplexCallSendTrapper {

//	DuplexCallTrapperSharedState sharedSenderReceiverState = new DuplexCallTrapperSharedState();

	protected DuplexRPCInputPort duplexRPCInputPort;
	
	protected DuplexCallTrapperSharedState sharedSenderReceiverState() {
		return (DuplexCallTrapperSharedState) sharedSenderReceiverState;
	}

	public ADuplexCallSendTrapper(InputPort anInputPort,
			NamingSender<Object> aDestination) {
		super(anInputPort, aDestination);
//		sharedSenderReceiverState = new DuplexCallTrapperSharedState();
//		duplexRPCInputPort = (DuplexRPCInputPort) anInputPort;
////		sharedSenderReceiverState.localRemoteReferenceTranslator = createLocalRemoteReferenceTranslator();
//		sharedSenderReceiverState().localRemoteReferenceTranslator = duplexRPCInputPort.getLocalRemoteReferenceTranslator();
//		sharedSenderReceiverState().duplexSentCallCompleter = createDuplexSentCallCompleter();
	}
	protected void  createSupportingObjects() {
		sharedSenderReceiverState = new DuplexCallTrapperSharedState();
		duplexRPCInputPort = (DuplexRPCInputPort) inputPort;
//		sharedSenderReceiverState.localRemoteReferenceTranslator = createLocalRemoteReferenceTranslator();
		sharedSenderReceiverState().localRemoteReferenceTranslator = duplexRPCInputPort.getLocalRemoteReferenceTranslator();
		sharedSenderReceiverState().duplexSentCallCompleter = createDuplexSentCallCompleter();
	}
	
	
	@Override
	public void send(String remoteName, Object message) {
		Tracer.info(this, "Sending message :" + remoteName + " Message:" + message);
		if (message instanceof RemoteCall) {
		RemoteCall call = (RemoteCall) message;
		sharedSenderReceiverState().localRemoteReferenceTranslator // pass reemote name also?
				.transformSentRemoteReferences(call.getArgs(), call.getMethod().getParameterTypes());
		}
		super.send(remoteName, message);
	}

	@Override
	public Object returnValue(String aDestination, Object aMessage) {
		RemoteCall call = (RemoteCall) aMessage;
		Object retVal = sharedSenderReceiverState().duplexSentCallCompleter
				.returnValueOfRemoteMethodCall(
//						duplexRPCInputPort.getLastSender(), 
//						duplexRPCInputPort.getPhysicalRemoteEndPoint(), 
						aDestination,						
						call);
		RemoteCallReturnValueDetermined.newCase(this, aMessage, retVal);

		return retVal;

//		return sharedSenderReceiverState().duplexSentCallCompleter
//				.returnValueOfRemoteMethodCall(
////						duplexRPCInputPort.getLastSender(), 
////						duplexRPCInputPort.getPhysicalRemoteEndPoint(), 
//						aDestination,						
//						call);
		
	}
	@Override
	public Object getSharedSenderReceiverState() {
		return sharedSenderReceiverState;
	}
	

	protected DuplexSentCallCompleter createDuplexSentCallCompleter() {
		return DuplexSentCallCompleterSelector.createDuplexSentCallCompleter(
				duplexRPCInputPort,
				sharedSenderReceiverState().localRemoteReferenceTranslator);
	}

//	protected LocalRemoteReferenceTranslator createLocalRemoteReferenceTranslator() {
//		return LocalRemoteReferenceTranslatorSelector
//				.createLocalRemoteReferenceTranslator(duplexRPCInputPort);
//
//	}



}
