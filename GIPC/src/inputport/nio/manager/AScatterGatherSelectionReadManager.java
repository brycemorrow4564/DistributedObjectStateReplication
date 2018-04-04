package inputport.nio.manager;

import inputport.nio.manager.commands.ReadCommand;
import inputport.nio.manager.commands.classes.AScatterGatherReadCommand;

import java.nio.channels.SocketChannel;



public class AScatterGatherSelectionReadManager extends ASelectionReadManager   {
//	public static final int BYTES_IN_INT =  Integer.SIZE/Byte.SIZE;
//	BlockingQueue<HeaderWriteCommand> headerBufferPool = new ArrayBlockingQueue<HeaderWriteCommand>(AScatterGatherSelectionManager.getMaxOutstandingWrites());


	int messageLength;
//	public static final int MAX_OUTSTANDING_WRITES = 256*4*4; // 4K buffers should be enough for even mouse movements

	public AScatterGatherSelectionReadManager(MessagingSelectingRunnable aSelectionManager) {
		super(aSelectionManager);		
	}

	@Override
	public ReadCommand createReadHandler(SocketChannel theSocketChannel) {
		return new AScatterGatherReadCommand(selectionManager, theSocketChannel);
	}
	
	






}
