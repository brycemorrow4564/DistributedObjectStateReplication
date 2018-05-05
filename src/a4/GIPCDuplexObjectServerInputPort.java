package a4;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import inputport.datacomm.ReceiveRegistrarAndNotifier;
import inputport.datacomm.duplex.DuplexServerInputPort;
import inputport.datacomm.duplex.object.ADuplexObjectServerInputPort;
import inputport.datacomm.duplex.object.explicitreceive.AReceiveReturnMessage;
import inputport.datacomm.duplex.object.explicitreceive.ReceiveReturnMessage;
import util.trace.port.objects.ReceivedMessageDequeued;

public class GIPCDuplexObjectServerInputPort extends ADuplexObjectServerInputPort {
	
	protected HashMap<String, ArrayBlockingQueue> clientQueueMap = null; 

	public GIPCDuplexObjectServerInputPort(DuplexServerInputPort<ByteBuffer> aBBDuplexServerInputPort) {
		super(aBBDuplexServerInputPort);
		clientQueueMap = new HashMap<String, ArrayBlockingQueue>();
		
	}
	
	@Override
	protected ReceiveRegistrarAndNotifier<Object> createReceiveRegistrarAndNotifier() {
		return new AServerExplicitReceiveNotifier(this);
	}
	
	@Override 
	public ReceiveReturnMessage<Object> receive(String aSource) {
		if (!clientQueueMap.containsKey(aSource)) {
			return null; 
		}
		ArrayBlockingQueue msgQueue = clientQueueMap.get(aSource);
		Object msg = null;
		try {
			msg = msgQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null; 
		}
		ReceivedMessageDequeued.newCase(this, msgQueue, msg);
		return new AReceiveReturnMessage(aSource, msg);
	}
	

}
