package a1.server.gipc;

import inputport.ConnectionType;
import inputport.InputPort;
import port.ATracingConnectionListener;

public class GIPCConnectionListener extends ATracingConnectionListener {
	
	public GIPCConnectionListener(InputPort anInputPort) {
		super(anInputPort); 
	}
	
	@Override
	public void connected(String aRemoteEnd, ConnectionType aConnectionType) {
		super.connected(aRemoteEnd, aConnectionType);	
		System.out.println("GIPC CONNECTION ESTABLISHED WITH " + aRemoteEnd);
	}

}
