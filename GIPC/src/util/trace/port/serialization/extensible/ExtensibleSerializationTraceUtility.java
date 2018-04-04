package util.trace.port.serialization.extensible;


import inputport.rpc.ACachingAbstractRPCProxyInvocationHandler;
import inputport.rpc.GIPCLocateRegistry;
import inputport.rpc.GIPCRegistry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import util.trace.ImplicitKeywordKind;
import util.trace.TraceableInfo;
import util.trace.Tracer;
import util.trace.port.ReceiveListenerNotified;
import util.trace.port.ReceiveListenerRegistered;
import util.trace.port.buffer.ClientNameAssociatedWithPort;
import util.trace.port.buffer.ClientNameLookedUp;
import util.trace.port.buffer.ClientNameSendInitiated;
import util.trace.port.buffer.TrapperBufferReceived;
import util.trace.port.buffer.TrapperBufferSendInitiated;
import util.trace.port.nio.SocketChannelAccepted;
import util.trace.port.nio.SocketChannelConnected;
import util.trace.port.nio.SocketChannelRead;
import util.trace.port.nio.SocketChannelWritten;
import util.trace.port.objects.BufferDeserializationFinished;
import util.trace.port.objects.BufferDeserializationInitiated;
import util.trace.port.objects.ObjectSerializationFinished;
import util.trace.port.objects.ObjectSerializationInitiated;
import util.trace.port.objects.TrapperObjectReceived;
import util.trace.port.objects.TrapperObjectSendInitiated;
import examples.mvc.rmi.duplex.ADistributedInheritingRMICounter;
import examples.mvc.rmi.duplex.DistributedRMICounter;
import examples.rmi.counter.simple.SimpleCounterClient;

public class ExtensibleSerializationTraceUtility implements SimpleCounterClient{
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
		
		Tracer.setKeywordPrintStatus(BufferDeserializationFinished.class, true);
		Tracer.setKeywordPrintStatus(BufferDeserializationInitiated.class, true);
		Tracer.setKeywordPrintStatus(ExtensibleBufferDeserializationFinished.class, true);
		Tracer.setKeywordPrintStatus(ExtensibleBufferDeserializationInitiated.class, true);
		Tracer.setKeywordPrintStatus(ObjectSerializationFinished.class, true);
		Tracer.setKeywordPrintStatus(ObjectSerializationInitiated.class, true);
		Tracer.setKeywordPrintStatus(ExtensibleValueSerializationFinished.class, true);
		Tracer.setKeywordPrintStatus(ExtensibleValueSerializationInitiated.class, true);			

	}

}
