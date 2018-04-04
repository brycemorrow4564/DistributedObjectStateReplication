package port.sessionserver.example;

import port.ParticipantChoice;
import port.sessionserver.ASessionServerLauncher;

public class CathyClientSessionClient {
	public static void main (String[] args) {
		(new ASessionServerClientLauncher(ASessionServerClientLauncher.SESSION_SERVER_HOST,
				"" + ASessionServerLauncher.SESSION_SERVER_PORT,
				ASessionServerLauncher.SESSION_SERVER_NAME, "9102", "Cathy", 
				ParticipantChoice.CLIENT_ONLY)).launch();
	}

	
	public static void oldMain (String[] args) {
		AnOldSessionClientLauncher.launch ("localhost", "9102", "cathy client");
	}

}
