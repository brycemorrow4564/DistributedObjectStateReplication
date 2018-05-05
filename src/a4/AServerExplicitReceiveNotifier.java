package a4;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import inputport.datacomm.AReceiveRegistrarAndNotifier;
import util.trace.port.objects.ReceivedMessageQueueCreated;
import util.trace.port.objects.ReceivedMessageQueued;

public class AServerExplicitReceiveNotifier extends AReceiveRegistrarAndNotifier<Object> {
	
	protected GIPCDuplexObjectServerInputPort port; 
	
	public AServerExplicitReceiveNotifier(GIPCDuplexObjectServerInputPort aPort) {
		port = aPort;  
	}
	
	@Override
	public void notifyPortReceive (String remoteEnd, Object message) {
		if (message == null) return; 
		ArrayBlockingQueue arrb = port.clientQueueMap.get(remoteEnd);
		if (arrb == null) {
			arrb = new ArrayBlockingQueue<>(100);
			ReceivedMessageQueueCreated.newCase(this, arrb);
		} 
		try {
			arrb.put(message);
			ReceivedMessageQueued.newCase(this, arrb, message);
			port.clientQueueMap.put(remoteEnd, arrb);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.notifyPortReceive(remoteEnd, message);
	}

}
