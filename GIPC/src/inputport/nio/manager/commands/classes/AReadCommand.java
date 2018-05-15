package inputport.nio.manager.commands.classes;

import inputport.nio.manager.SelectionManager;
import inputport.nio.manager.commands.ReadCommand;
import inputport.nio.manager.listeners.SocketChannelCloseListener;
import inputport.nio.manager.listeners.SocketChannelReadListener;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import util.trace.Tracer;
import util.trace.port.nio.ReadRequestCreated;
import util.trace.port.nio.SocketChannelFullMessageRead;
import util.trace.port.nio.SocketChannelInterestOp;
import util.trace.port.nio.SocketChannelRead;


public class AReadCommand extends AnAbstractNIOCommand implements ReadCommand {
	public static final int READ_BUFFER_SIZE = 256*4*256*4*4; // with late comer, even 1 M was not enough in simple demo
	SocketChannel socketChannel;
	SelectionManager selectionManager;
	ByteBuffer readBuffer;
	private List<SocketChannelCloseListener> closeListeners = new ArrayList();
	private List<SocketChannelReadListener> readListeners = new ArrayList();
	public AReadCommand(SelectionManager theSelectionManager,
			SocketChannel aSocketChannel, Integer aNextInterestOps) {
		super(aSocketChannel, aNextInterestOps);
		socketChannel = aSocketChannel;
		selectionManager = theSelectionManager;	
		allocateReadState();
	}
	public AReadCommand(SelectionManager theSelectionManager,
			SocketChannel theSocketChannel) {
		this(theSelectionManager, theSocketChannel, null);
	}
	@Override
	public SelectableChannel getChannel() {
		return socketChannel;
	}
	@Override
	public boolean initiate() {
		Tracer.info(this, "Selector registering read as no pending writes");
		SelectionKey selectionKey = socketChannel.keyFor(selectionManager.getSelector());
		selectionKey.interestOps(SelectionKey.OP_READ);
		Tracer.info(this, "New selection ops for channel: " + selectionKey.channel() + " are " + selectionKey.interestOps());
		SocketChannelInterestOp.newCase(this, selectionKey, SelectionKey.OP_READ);

		return false;
	}
	@Override
	public synchronized boolean execute() {
		try {
			processRead();
			return true;
		} catch (IOException e) {
//			e.printStackTrace();
			System.err.println ("AReadCommand for " + socketChannel + ":" +  e.getMessage());
			SelectionKey key = socketChannel.keyFor(selectionManager.getSelector());
			selectionManager.close(key, e);
			notifyCloseListeners(socketChannel, e);
			return false;
		}
	}
	protected void processRead() throws IOException {
		
		Tracer.info(this, "Processing read on channel:" + socketChannel);
		readIntoBuffer();
		readBuffer.flip();// why flip if we are clearing later? To do the wrap correctly?

		Tracer.info(this, "Read buffer after flip:" + readBuffer);
		ByteBuffer messageBuffer = ByteBuffer.wrap(readBuffer.array(), 0, readBuffer.remaining());

		notifyRead(socketChannel, messageBuffer, readBuffer.remaining());
		readBuffer.clear();	
		Tracer.info(this, "Cleared read buffer");
	}
	protected int  readIntoBuffer() throws IOException {
		int bytesRead = socketChannel.read(readBuffer);
		SocketChannelRead.newCase(this, socketChannel, readBuffer, bytesRead);

//		SocketChannelRead.newCase(this, socketChannel, readBuffer);

		if (readBuffer.position() == readBuffer.capacity()) {
			Tracer.error("Read Buffer Overflow. Bytes read : " + bytesRead);
		}
		Tracer.info(this, "Read: " + bytesRead + " bytes into buffer: " + readBuffer);
		if (bytesRead < 0)  {
			throw new EOFException("An existing connection was normally closed by the remote host.");				
		}
		return bytesRead;
			
     }
	protected synchronized void notifyRead(SocketChannel theSocketChannel, ByteBuffer theBuffer, int length ) {
		SocketChannelFullMessageRead.newCase(this, socketChannel, theBuffer, length, readListeners);

		for (SocketChannelReadListener readListener:readListeners)
//		if (readListener != null)
			readListener.socketChannelRead(theSocketChannel, theBuffer, length);
		
	}
	protected void allocateReadState() {
		readBuffer = ByteBuffer.allocate(READ_BUFFER_SIZE);
		Tracer.info(this, "Marked and allocated read buffer:" + readBuffer + " of size " + READ_BUFFER_SIZE );
		readBuffer.mark();
		
	}
	
	@Override
	public synchronized void addReadListener(SocketChannelReadListener aListener) {
		if (aListener == null) {
			System.err.println("Ignoring attempt to register null read listener");
			return;
		}
		if (readListeners.contains(aListener))
			return;
		readListeners.add(aListener);
		
	}
	public synchronized void addCloseListener(SocketChannelCloseListener aListener) {
		if (aListener == null) {
			System.err.println("Ignoring attempt to register null close listener");
			return;
		}
		if (closeListeners.contains(aListener))
			return;
		closeListeners.add(aListener);
	}
	public void notifyCloseListeners(SocketChannel theSocketChannel, Exception anException) {
		try {
	for (SocketChannelCloseListener closeListener:closeListeners)
	    closeListener.closed(theSocketChannel, anException);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
		
	
	

}
