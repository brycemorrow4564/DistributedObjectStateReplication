package a1.server;

public class ServerStateFactory {

	static ServerState state = null; 
	static SimulationServer server; 
	
	public static void init() {}
	
	public static void setServer(SimulationServer server) {
		ServerStateFactory.server = server;
	}
	
	public static ServerState getState() {
		if (state == null) {
			state = new ServerState(server);
		}
		return state;  
	}
	
	
}