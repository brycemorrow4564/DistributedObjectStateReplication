package a1.common.rpc;

import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;

import a1.common.message.Message;

public class PendingRequestProcessor implements Runnable {
	
	private ArrayBlockingQueue<PendingRequest> pendingRequests;
	private ArrayBlockingQueue<Method> pendingCalls; 

	public PendingRequestProcessor(ArrayBlockingQueue<PendingRequest> pendingRequests) {
		this.pendingRequests = pendingRequests; 
	}
	
	@Override
	public void run() {
		PendingRequest req; 
		while (true) { 
			try {
				req = pendingRequests.take();
				req.fufillRequest();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
