package a1.common.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOByteBufferWrapper {

	private ByteBuffer 		bBuff; 
	private int 				len;
	private SocketChannel 	originChannel;
	
	public NIOByteBufferWrapper(ByteBuffer bBuff, int len, SocketChannel originChannel) {
		this.bBuff = bBuff; 
		this.len = len; 
		this.originChannel = originChannel; 
	}
	
	public ByteBuffer getByteBuffer() {
		return this.bBuff; 
	}
	
	public int getLength() {
		return this.len; 
	}
	
	public SocketChannel getOriginSocketChannel() {	
		return this.originChannel;
	}
	
}