package sessionport.rpc.duplex.direct.example;

import inputport.ConnectionListener;
import port.ParticipantChoice;
import port.sessionserver.SessionServerLauncher;
import sessionport.datacomm.duplex.object.ObjectDuplexSessionPortSelector;
import sessionport.datacomm.duplex.object.direct.ADirectObjectDuplexSessionPortFactory;
import sessionport.datacomm.duplex.object.relayed.ARelayingObjectDuplexSessionPortFactory;
import sessionport.rpc.duplex.DuplexRPCSessionPort;
import sessionport.rpc.duplex.DuplexRPCSessionPortSelector;
import sessionport.rpc.duplex.relayed.example.ACallingConnectListener;
import sessionport.rpc.duplex.relayed.example.Adder;
import sessionport.rpc.duplex.relayed.example.AnAdder;


public class ADuplexRPCDirectSessionPortLauncher {
//	static final int SESSION_SERVER_PORT = 9090;
	static final int SESSION_SERVER_PORT = SessionServerLauncher.SESSION_SERVER_PORT;
//	static final String SESSION_SERVER_NAME = "SESSION SERVER";
	static final String SESSION_SERVER_NAME = SessionServerLauncher.SESSION_SERVER_NAME; 



	public static void launchSessionPartipant( String anId, String aName, ParticipantChoice aChoice) {
//		Tracer.showInfo(true);
		ObjectDuplexSessionPortSelector.setDuplexSessionPortFactory(
				new ADirectObjectDuplexSessionPortFactory());
//		ObjectDuplexSessionPortSelector.setDuplexSessionPortFactory(
//				new ARelayingObjectDuplexSessionPortFactory());
		DuplexRPCSessionPort sessionPort = DuplexRPCSessionPortSelector.createDuplexRPCSessionPort("localhost", 
				"" + SESSION_SERVER_PORT, SESSION_SERVER_NAME, "Test Session", anId, aName,
				aChoice
				);	
//		DuplexRPCSessionPort sessionPort = DuplexRPCSessionPortSelector.createDuplexRPCSessionPort("localhost", 
//				"" + SESSION_SERVER_PORT, aSessionServerName, "Test Session", anId, aName,
//				aChoice
//				);
		ConnectionListener connectListener = new ACallingConnectListener(sessionPort);
		sessionPort.addConnectionListener(connectListener);
		Adder adder = new AnAdder();
		sessionPort.register(Adder.class, adder);
		sessionPort.connect();		
	}
	

}
