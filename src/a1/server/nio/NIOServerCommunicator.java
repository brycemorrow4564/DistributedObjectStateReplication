package a1.server.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import a1.common.message.Message;
import a1.common.message.Message.ProposalType;
import a1.common.Communicator;
import a1.common.InitialConfigurations;
import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.nio.NIOByteBufferWrapper;
import a1.server.SimulationServer;
import assignments.util.MiscAssignmentUtils;
import inputport.nio.manager.AScatterGatherSelectionManager;
import inputport.nio.manager.NIOManagerFactory;
import inputport.nio.manager.factories.classes.AReadingAcceptCommandFactory;
import inputport.nio.manager.factories.selectors.AcceptCommandFactorySelector;
import inputport.nio.manager.listeners.SocketChannelAcceptListener;
import inputport.nio.manager.listeners.SocketChannelCloseListener;
import inputport.nio.manager.listeners.SocketChannelReadListener;
import util.trace.port.nio.SocketChannelBound;

public class NIOServerCommunicator implements 	SocketChannelAcceptListener,
												SocketChannelReadListener, 
												SocketChannelCloseListener, 
												Communicator {
	
	private SimulationServer 						server; 
	private ArrayBlockingQueue<NIOByteBufferWrapper> 	commandQueue;
	private ServerSocketChannel 						serverSocketChannel; 
	private Thread 									readProcessorThread;
	private ArrayList<SocketChannel>				 	writeSockets;
	
	public NIOServerCommunicator(SimulationServer aServer, int aServerPort) {
		setFactories();
		server = aServer;  
		writeSockets = new ArrayList<SocketChannel>();
		commandQueue = new ArrayBlockingQueue<NIOByteBufferWrapper>(AScatterGatherSelectionManager.getMaxOutstandingWrites()*2);
		serverSocketChannel = createSocketChannelAndBindToPort(aServerPort);
		makeServerConnectable(aServerPort); 
		createAndStartReadProcessorThread(); 
	}
	
	public void sendMessageToClients(Message msg, SocketChannel originChannel) {
		ByteBuffer serializedMsg = ByteBuffer.wrap(Util.serializeMessage(msg));
		boolean isNonAtomic =  msg.getSenderBMode() == BroadcastMode.NON_ATOMIC;
		for (SocketChannel sc : writeSockets) {
			if (!(msg.getPropType() == ProposalType.SimulationCommand && isNonAtomic && sc.equals(originChannel))) {
				NIOManagerFactory.getSingleton().write(sc, serializedMsg);
			}
		}
	}
	
	public ArrayList<SocketChannel> getWritableSockets() {
		return writeSockets; 
	}

	protected void makeServerConnectable(int aServerPort) {
		NIOManagerFactory.getSingleton().enableListenableAccepts(serverSocketChannel, this);
	}	
	
	@Override
	public void socketChannelAccepted(ServerSocketChannel aServerSocketChannel, SocketChannel aSocketChannel) {
		NIOManagerFactory.getSingleton().addReadListener(aSocketChannel, this);	
		writeSockets.add(aSocketChannel); 
	}
	
	protected ServerSocketChannel createSocketChannelAndBindToPort(int aServerPort) {
		try {
			ServerSocketChannel retVal = ServerSocketChannel.open();
			InetSocketAddress socketAddress = new InetSocketAddress(aServerPort);
			retVal.socket().bind(socketAddress);
			SocketChannelBound.newCase(this, retVal, socketAddress);
			return retVal;
		} catch (Exception e) { 
			e.printStackTrace(); 
			return null; 
		}
	}

	@Override
	public void socketChannelRead(SocketChannel aSocketChannel, ByteBuffer aMessage, int aLength) {
		while (true) {
			try {
				//System.out.println("NIO Server read from the buffer");
				ByteBuffer bufferDeepCopy = MiscAssignmentUtils.deepDuplicate(aMessage);
				NIOByteBufferWrapper wrapper = new NIOByteBufferWrapper(bufferDeepCopy, aLength, aSocketChannel); 
				commandQueue.add(wrapper);
				return; 
			} catch (IllegalStateException e) {
				System.out.println(e.getMessage());
			}	
		}
	}
	
	@Override
	public void closed(SocketChannel closedChannel, Exception theReadException) {
		writeSockets.remove(closedChannel);
	}
	
	private void createAndStartReadProcessorThread() {
		NIOServerReadProcessor runnable = new NIOServerReadProcessor(server, commandQueue); 
		readProcessorThread = new Thread(runnable); 
		readProcessorThread.setName(InitialConfigurations.READ_THREAD_NAME);  
		readProcessorThread.start(); 
	}
	
	protected void setFactories() {	
		AcceptCommandFactorySelector.setFactory(new AReadingAcceptCommandFactory());
	}

}
