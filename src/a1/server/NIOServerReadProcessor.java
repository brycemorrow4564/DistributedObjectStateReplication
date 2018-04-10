package a1.server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.CTS_Proposal;
import a1.common.message.CTS_ProposalResponse;
import a1.common.message.Message;
import a1.common.message.STC_ProposalAcceptRequest;
import a1.common.message.STC_ProposalExecute;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import a1.common.nio.NIOByteBufferWrapper;
import a1.common.rmi.DistributedClientManager;
import inputport.nio.manager.NIOManagerFactory;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;
import util.trace.port.rpc.gipc.GIPCObjectLookedUp;
import util.trace.port.rpc.rmi.RMIObjectLookedUp;

public class NIOServerReadProcessor implements Runnable {
	
	ArrayBlockingQueue<NIOByteBufferWrapper> 	commandQueue;
	SimulationServer							server; 
	
	public NIOServerReadProcessor(SimulationServer server, ArrayBlockingQueue<NIOByteBufferWrapper> commandQueue) {
		this.server = server; 
		this.commandQueue = commandQueue; 
	}
	

	
	public void processMessage(Message clientMsg, SocketChannel originChannel) {
		ServerCommunicator comm = server.getCommunicator(); 
		Message msg = comm.generateResponseMessage(clientMsg, commandQueue.size()); 
		if (msg == null) { return; } //we are not waiting for a message at this time 
		comm.processIncomingMessage(clientMsg, originChannel);
	}
	
	@Override
	public void run() {
		NIOByteBufferWrapper wrapper = null; 
		while (true) { 		
			try { 
				wrapper = this.commandQueue.take();
				ByteBuffer buf	= wrapper.getByteBuffer(); 
				byte[] arr = new byte[buf.remaining()];
				buf.get(arr);
				Message msg = Util.deserializeMessage(arr);
				processMessage(msg, wrapper.getOriginSocketChannel()); 
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			}
		}
	}

}
