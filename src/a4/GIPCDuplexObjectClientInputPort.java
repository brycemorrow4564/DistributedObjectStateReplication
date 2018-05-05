package a4;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

import inputport.datacomm.ReceiveRegistrarAndNotifier;
import inputport.datacomm.duplex.DuplexClientInputPort;
import inputport.datacomm.duplex.object.ADuplexObjectClientInputPort;
import inputport.datacomm.duplex.object.explicitreceive.AReceiveReturnMessage;
import inputport.datacomm.duplex.object.explicitreceive.ReceiveReturnMessage;
import util.trace.port.objects.ReceivedMessageDequeued;
import util.trace.port.objects.ReceivedMessageQueueCreated;

public class GIPCDuplexObjectClientInputPort extends ADuplexObjectClientInputPort {
	
	protected ArrayBlockingQueue messages; 

	public GIPCDuplexObjectClientInputPort(DuplexClientInputPort<ByteBuffer> aBBClientInputPort) {
		super(aBBClientInputPort);
		messages = new ArrayBlockingQueue(2000);
		ReceivedMessageQueueCreated.newCase(this, messages);
	}
	
	@Override 
	protected ReceiveRegistrarAndNotifier<Object> createReceiveRegistrarAndNotifier() {
		return new AClientExplicitReceiveNotifier(this);
	}
	
	@Override
	public ReceiveReturnMessage<Object> receive(String aSource) {
		try {
			Object msg = messages.take();
			ReceivedMessageDequeued.newCase(this, messages, msg);
			return new AReceiveReturnMessage<Object>(aSource, msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null; 
		}
	}

}
