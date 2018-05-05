package a4;

import inputport.datacomm.AReceiveRegistrarAndNotifier;
import util.trace.port.objects.ReceivedMessageQueued;

@SuppressWarnings("rawtypes")
public class AClientExplicitReceiveNotifier extends AReceiveRegistrarAndNotifier {
	
	protected GIPCDuplexObjectClientInputPort port; 
	
	public AClientExplicitReceiveNotifier(GIPCDuplexObjectClientInputPort aPort) {
		port = aPort; 
	}
	
	@Override
	public void notifyPortReceive (String remoteEnd, Object message) {
		try {
			if (message == null) return; 
			port.messages.put(message);
			ReceivedMessageQueued.newCase(this, port.messages, message);
			super.notifyPortReceive(remoteEnd, message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
