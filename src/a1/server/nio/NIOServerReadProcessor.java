package a1.server.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;

import a1.common.Util;
import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import a1.common.nio.NIOByteBufferWrapper;
import a1.server.ServerCommunicator;
import a1.server.SimulationServer;
import util.interactiveMethodInvocation.IPCMechanism;

public class NIOServerReadProcessor implements Runnable {
	
	ArrayBlockingQueue<NIOByteBufferWrapper> 	commandQueue;
	SimulationServer							server; 
	
	public NIOServerReadProcessor(SimulationServer server, ArrayBlockingQueue<NIOByteBufferWrapper> commandQueue) {
		this.server = server; 
		this.commandQueue = commandQueue; 
	}

	public void processIncomingMessage(Message msg, SocketChannel originChannel) {
		Message response = new Message(MsgType.STC_ProposalExecute); //NIO does NOT support centralized synchronous broadcast
		response.setSenderBMode(msg.getSenderBMode());
		response.setProposalType(msg.getPropType());
		response.setCommandToExecute(msg.getCommandToExecute());
		response.setBModeToSet(msg.getBModeToSet());
		response.setIpcMechToSet(msg.getIpcMechToSet());
		NIOServerCommunicator c = (NIOServerCommunicator) server.getCommunicator().getSubCommunicatorOfType(IPCMechanism.NIO);
		c.sendMessageToClients(response, originChannel);
	}
	
	@Override
	public void run() {
		NIOByteBufferWrapper wrapper = null; 
		while (true) { 		
			try { 
				wrapper = this.commandQueue.take();
				ByteBuffer buf = wrapper.getByteBuffer(); 
				byte[] arr = new byte[buf.remaining()];
				buf.get(arr);
				Message msg = Util.deserializeMessage(arr);
				SocketChannel originChannel = wrapper.getOriginSocketChannel();
				processIncomingMessage(msg, originChannel);
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			}
		}
	}

}
