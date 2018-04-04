package util.trace.port.rpc;



import util.trace.ImplicitKeywordKind;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class RPCTraceUtility {
	/**
	 * Do not change this, I will keep updating it and you will run into conflicts
	 * Make a copy if you want this changed
	 */
	public static void setTracing() {
		Tracer.showInfo(true);
		Tracer.setDisplayThreadName(true); 
		TraceableInfo.setPrintTraceable(true);
		TraceableInfo.setPrintSource(true);
		Tracer.setImplicitPrintKeywordKind(ImplicitKeywordKind.OBJECT_CLASS_NAME);
		
	
//		Tracer.setKeywordPrintStatus(CallInitiated.class, true);
		Tracer.setKeywordPrintStatus(CallReceived.class, true);
		Tracer.setKeywordPrintStatus(ProxyCacheInvalidated.class, true);
		Tracer.setKeywordPrintStatus(ProxyCreated.class, true);
		
		Tracer.setKeywordPrintStatus(ProxyMethodCalled.class, true);
		Tracer.setKeywordPrintStatus(ProxyMethodConvertedToCallObject.class, true);
		Tracer.setKeywordPrintStatus(ProxyMethodForwardedToPort.class, true);
		Tracer.setKeywordPrintStatus(ProxyMethodReturnsCachedValue.class, true);
		Tracer.setKeywordPrintStatus(ProxyPureFunctionCalled.class, true);

		Tracer.setKeywordPrintStatus(ReceivedCallDequeued.class, true);
		Tracer.setKeywordPrintStatus(ReceivedCallEnded.class, true);
		Tracer.setKeywordPrintStatus(ReceivedCallInitiated.class, true);
		Tracer.setKeywordPrintStatus(ReceivedCallQueued.class, true);
		Tracer.setKeywordPrintStatus(ReceivedObjectTransformed.class, true);
		Tracer.setKeywordPrintStatus(RemoteCallReceivedReturnValue.class, true);
		Tracer.setKeywordPrintStatus(RemoteCallUnblockingWithReturnValue.class, true);
		Tracer.setKeywordPrintStatus(RemoteCallWaitingForReturnValue.class, true);
		Tracer.setKeywordPrintStatus(ReceivedReturnValueQueued.class, true);	
		Tracer.setKeywordPrintStatus(RegisteredObjectLookedUp.class, true);	
		Tracer.setKeywordPrintStatus(RemoteCallBlockedForReturnValue.class, true);
		Tracer.setKeywordPrintStatus(RemoteCallFinished.class, true);
		Tracer.setKeywordPrintStatus(RemoteCallGenerated.class, true);
		Tracer.setKeywordPrintStatus(RemoteCallInitiated.class, true);
		Tracer.setKeywordPrintStatus(RemoteCallReturnValueDetermined.class, true);
		Tracer.setKeywordPrintStatus(ReturnMessageCreated.class, true);			
		Tracer.setKeywordPrintStatus(ReturnValueQueueCreated.class, true);	
		Tracer.setKeywordPrintStatus(ReturnValueQueueCreated.class, true);	
		Tracer.setKeywordPrintStatus(SentObjectTransformed.class, true);
		



//		Tracer.setKeywordPrintStatus(ObjectReceived.class, true);
//		Tracer.setKeywordPrintStatus(ObjectSendInitiated.class, true);
//		Tracer.setKeywordPrintStatus(ClientNameSendInitiated.class, true);
//		Tracer.setKeywordPrintStatus(ClientNameAssociatedWithPort.class, true);
//		Tracer.setKeywordPrintStatus(ClientNameLookedUp.class, true);
//
//
//		Tracer.setKeywordPrintStatus(TrapperBufferSendInitiated.class, true);
//		Tracer.setKeywordPrintStatus(TrapperBufferReceived.class, true);
//		
//		Tracer.setKeywordPrintStatus(SocketChannelAccepted.class, true);
//		Tracer.setKeywordPrintStatus(SocketChannelConnectFinished.class, true);
//		Tracer.setKeywordPrintStatus(SocketChannelWritten.class, true);
//		Tracer.setKeywordPrintStatus(SocketChannelRead.class, true);


	}

}
