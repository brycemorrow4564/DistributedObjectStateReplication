package replicatedsessionport.rpc.duplex.singleresponse.example;

import port.sessionserver.ASessionServerLauncher;
import sessionport.datacomm.group.object.flexible.AFlexibleSessionPortClientLauncher;

public class CathySingleResponseReplicatedSessionClientPortLauncher {

	public static void main (String[] args) {
		
		(new ASingleResponseReplicatedGroupSessionPortClientLauncher(
				AFlexibleSessionPortClientLauncher.SESSION_SERVER_HOST,
				"" + ASessionServerLauncher.SESSION_SERVER_PORT, 
				ASessionServerLauncher.SESSION_SERVER_NAME, 
				ASingleResponseReplicatedGroupSessionPortClientLauncher.SERVER_NAME,

				"Cathy",
				AFlexibleSessionPortClientLauncher.SESSION_CHOICE,
				AFlexibleSessionPortClientLauncher.DO_DELAY,
				new port.delay.example.ACathyDelaysSupport(),
				AFlexibleSessionPortClientLauncher.DO_CAUSAL,
				null, ASingleResponseReplicatedGroupSessionPortServerLauncher.DO_JITTER
				)).launch();
	}
	
	

}
