package a4;

import java.nio.ByteBuffer;

import examples.gipc.counter.customization.ACustomDuplexObjectClientInputPort;
import examples.gipc.counter.customization.ACustomDuplexObjectServerInputPort;
import inputport.datacomm.duplex.DuplexClientInputPort;
import inputport.datacomm.duplex.DuplexServerInputPort;
import inputport.datacomm.duplex.buffer.DuplexBufferInputPortSelector;
import inputport.datacomm.duplex.object.ADuplexObjectInputPortFactory;

public class GIPCInputPortFactory extends ADuplexObjectInputPortFactory {

	@Override 
	public DuplexClientInputPort<Object> createDuplexClientInputPort(DuplexClientInputPort<ByteBuffer> bbClientInputPort) {
		return new GIPCDuplexObjectClientInputPort(bbClientInputPort);
	}
	
	@Override 
	public DuplexServerInputPort<Object> createDuplexServerInputPort(DuplexServerInputPort<ByteBuffer> bbServerInputPort) {
		return new GIPCDuplexObjectServerInputPort(bbServerInputPort);
	}
	
	@Override
	public DuplexClientInputPort<Object> createDuplexClientInputPort(
			String theServerHost, String theServerId, String aServerName, String aClientName) {
		DuplexClientInputPort<ByteBuffer> bbClientInputPort = DuplexBufferInputPortSelector.createDuplexClientInputPort(theServerHost, theServerId, aServerName, aClientName);
		return new GIPCDuplexObjectClientInputPort(bbClientInputPort);
	}
	
	@Override
	public DuplexServerInputPort<Object> createDuplexServerInputPort(
			String theServerId, String theServerName) {
		DuplexServerInputPort<ByteBuffer> bbServerInputPort = DuplexBufferInputPortSelector.createDuplexServerInputPort(theServerId, theServerName);
		return new GIPCDuplexObjectServerInputPort(bbServerInputPort);
	}
	
}




