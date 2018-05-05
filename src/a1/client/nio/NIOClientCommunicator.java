package a1.client.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;

import a1.common.InitialConfigurations;
import a1.common.Util;
import a1.common.message.Message.ProposalType;
import a1.common.message.Message;
import a1.client.SimulationClient;
import a1.common.Communicator;
import a1.common.nio.NIOByteBufferWrapper;
import assignments.util.MiscAssignmentUtils;
import inputport.nio.manager.AScatterGatherSelectionManager;
import inputport.nio.manager.NIOManagerFactory;
import inputport.nio.manager.factories.classes.AReadingWritingConnectCommandFactory;
import inputport.nio.manager.factories.selectors.ConnectCommandFactorySelector;
import inputport.nio.manager.listeners.SocketChannelConnectListener;
import inputport.nio.manager.listeners.SocketChannelReadListener;

public class NIOClientCommunicator implements SocketChannelReadListener, 
											 SocketChannelConnectListener, 
											 Communicator {
	 
	private ArrayBlockingQueue<NIOByteBufferWrapper> 	commandQueue;
	private SocketChannel 							socketChannel; 
	private Thread 									readProcessorThread;
	
	public NIOClientCommunicator(SimulationClient aClient, String aServerHost, int aServerPort) {
		setFactories(); 
		commandQueue = new ArrayBlockingQueue<NIOByteBufferWrapper>(AScatterGatherSelectionManager.getMaxOutstandingWrites());
		socketChannel = createSocketChannel(); 
		connectToServer(socketChannel, aServerHost, aServerPort);
		createAndStartReadProcessorThread(aClient);  
	}

	public void sendMessageToServer(Message msg) {
		ByteBuffer serializedMsg = ByteBuffer.wrap(Util.serializeMessage(msg)); 
		NIOManagerFactory.getSingleton().write(socketChannel, serializedMsg);
	}
	
	public void socketChannelRead(SocketChannel aSocketChannel, ByteBuffer aMessage, int aLength) {
		try {
			commandQueue.add(new NIOByteBufferWrapper(MiscAssignmentUtils.deepDuplicate(aMessage), aLength, aSocketChannel)); 	
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}	
	}

	public void connected(SocketChannel aSocketChannel) {
		System.out.println("Our client is connected to the server via NIO");
		NIOManagerFactory.getSingleton().addReadListener(aSocketChannel, this);
	}
	
	public void notConnected(SocketChannel aSocketChannel, Exception e) {
		if (e != null) { e.printStackTrace(); }
	}
	
	protected SocketChannel createSocketChannel() {
		try {  
			return SocketChannel.open();
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
			return null; 
		}
	}
	
	private void connectToServer(SocketChannel localChannel, String aServerHost, int aServerPort) {
		connectToSocketChannel(localChannel, aServerHost, aServerPort);
	}
	
	private void connectToSocketChannel(SocketChannel localChannel, String aServerHost, int aServerPort) {
		try {
			InetAddress aServerAddress = InetAddress.getByName(aServerHost);
			NIOManagerFactory.getSingleton().connect(localChannel, aServerAddress, aServerPort, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setFactories() {
		ConnectCommandFactorySelector.setFactory(new AReadingWritingConnectCommandFactory());
	}

	private void createAndStartReadProcessorThread(SimulationClient client) {
		NIOClientCommunicatorProcessor runnable = new NIOClientCommunicatorProcessor(commandQueue, client); 
		readProcessorThread = new Thread(runnable); 
		readProcessorThread.setName(InitialConfigurations.READ_THREAD_NAME);  
		readProcessorThread.start(); 
	}
}
