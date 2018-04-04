package util.trace.port.objects;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import inputport.ConnectionManager;
import inputport.rpc.RemoteCall;
import util.trace.TraceableInfo;
import util.trace.port.rpc.ReceivedCallEndedOld;

public class BufferDeserializationFinished extends TraceableInfo {	
	public BufferDeserializationFinished(String aMessage, Object aFinder,
			String aSource,
			ByteBuffer aByteBuffer,
			Object anObject) {
		super(aMessage, aFinder);
	}
	public static BufferDeserializationFinished newCase(Object aFinder, 
			String aSource,
			ByteBuffer aByteBuffer, Object anObject) {    	
		String aMessage =
				aSource + "->" +
				anObject + " <-" +
				aByteBuffer ;
		BufferDeserializationFinished retVal = new BufferDeserializationFinished(aMessage, 
				aFinder,
				aSource,  aByteBuffer, anObject);
    	retVal.announce();
    	return retVal;
	}
}
