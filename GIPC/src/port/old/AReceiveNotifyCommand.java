package port.old;

import inputport.datacomm.ReceiveRegistrarAndNotifier;

import java.nio.ByteBuffer;

public class AReceiveNotifyCommand implements Command {
	ReceiveRegistrarAndNotifier receiptNotifier;
	String remoteEnd;
	ByteBuffer message;
	int length;
	public AReceiveNotifyCommand(ReceiveRegistrarAndNotifier theReceiptNotifier,
			String theRemoteEnd,
			ByteBuffer theMessage,
			int theLength) {
		receiptNotifier = theReceiptNotifier;
		remoteEnd = theRemoteEnd;
		length = theLength;	
	}
	@Override
	public void execute() {
		receiptNotifier.notifyPortReceive(remoteEnd, message);
	}
}
