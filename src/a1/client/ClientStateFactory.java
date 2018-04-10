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
		if (client == null || communicator == null) {
			System.out.println("ERROR: Either the client or commuicator property of the ClientStateFactory class was null when we called the static method getState()");
			return null; 
		}
		if (state == null) {
			state = new ClientState(client, communicator);
		}
		return state;  
	}
	
	
}
