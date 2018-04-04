package util.trace.port.nio;



import util.trace.ImplicitKeywordKind;
import util.trace.TraceableInfo;
import util.trace.Tracer;
import util.trace.port.PerformanceExperimentEnded;
import util.trace.port.PerformanceExperimentStarted;


public class NIOTraceUtility {
	/**
	 * Do not change this, I will keep updating it and you will run into conflicts
	 * Make a copy if you want this changed
	 */
	public static void setTracing() {
		Tracer.showInfo(true);
		Tracer.setDisplayThreadName(true);
		Tracer.setDisplayTime(true);
		TraceableInfo.setPrintTraceable(true);
		TraceableInfo.setPrintSource(true);
		Tracer.setImplicitPrintKeywordKind(ImplicitKeywordKind.OBJECT_CLASS_NAME);	
		Tracer.setKeywordPrintStatus(ListenableAcceptsEnabled.class, true);
		Tracer.setKeywordPrintStatus(ReadListenerAdded.class, true);
		Tracer.setKeywordPrintStatus(ReadRequestCreated.class, true);
		Tracer.setKeywordPrintStatus(ReadsEnabled.class, true);
		Tracer.setKeywordPrintStatus(PerformanceExperimentEnded.class, true);
		Tracer.setKeywordPrintStatus(PerformanceExperimentStarted.class, true);
		Tracer.setKeywordPrintStatus(SelectCalled.class, true);
		Tracer.setKeywordPrintStatus(SelectorWokenUp.class, true);
		Tracer.setKeywordPrintStatus(SelectUnblocked.class, true);
		Tracer.setKeywordPrintStatus(SelectorRequestEnqueued.class, true);
		Tracer.setKeywordPrintStatus(SelectorRequestDequeued.class, true);
		Tracer.setKeywordPrintStatus(SelectorRequestNextInterestOp.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelAccepted.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelBlockingConfigured.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelBound.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelConnected.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelConnectInitiated.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelConnectRequested.class, true);

		Tracer.setKeywordPrintStatus(SocketChannelFullMessageRead.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelHeaderRead.class, true);

		Tracer.setKeywordPrintStatus(SocketChannelInterestOp.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelPartialMessageFound.class, true);

		Tracer.setKeywordPrintStatus(SocketChannelRead.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelRegistered.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelWriteRequested.class, true);
		Tracer.setKeywordPrintStatus(SocketChannelWritten.class, true);
		Tracer.setKeywordPrintStatus(WriteBufferIsEmpty.class, true);
		Tracer.setKeywordPrintStatus(WriteListenerAdded.class, true);
		Tracer.setKeywordPrintStatus(WriteRequestEnqueued.class, true);
		Tracer.setKeywordPrintStatus(WriteRequestDequeued.class, true);


	}

}
