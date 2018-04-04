package a1.client;

public class ClientStateFactory {

	static ClientState state = null; 
	static SimulationClient client; 
	static ClientCommunicator communicator; 
	
	public static void init() {}
	
	public static void setClient(SimulationClient aClient) {
		client = aClient;
	}
	
	public static void setCommunicator(ClientCommunicator aCommunicator) {
		communicator = aCommunicator; 
	}
	
	public static ClientState getState() {
		if (state == null) {
			state = new ClientState(client, communicator);
		}
		return state;  
	}
	
	
}
