package util.trace.port.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import inputport.rpc.RemoteCall;
import util.trace.TraceableInfo;
import util.trace.port.rpc.ReceivedCallEndedOld;

public class SocketChannelRead extends SocketChannelDataInfo {	
	public SocketChannelRead(String aMessage, Object aFinder,
			SocketChannel aSocketChannel,
			ByteBuffer aByteBuffer,
			int aLength) {
		super(aMessage, aFinder,  aSocketChannel, aByteBuffer);
	}
	public static SocketChannelRead newCase(Object aSource, 			
			SocketChannel aSocketChannel, ByteBuffer aByteBuffer, int aLength) {    	
		String aMessage = 
				"(" + aLength + ")" +
				aByteBuffer +
				" " +
				 aSocketChannel; 
				
		SocketChannelRead retVal = new SocketChannelRead(aMessage, 
				aSource, aSocketChannel, aByteBuffer, aLength);
    	retVal.announce();
    	return retVal;
	}
}
