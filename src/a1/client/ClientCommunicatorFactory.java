package a1.client;

public class ClientCommunicatorFactory {

	static ClientCommunicator communicator; 
	
	public static void init() {}
	
	public static void setCommunicator(ClientCommunicator aCommunicator) {
		communicator = aCommunicator; 
	}
	
	public static ClientCommunicator getCommunicator() {
		if (communicator == null) {
			System.out.println("ERROR: Client Commmunicator Factory had no communicator set");
			return null; 
		}
		return communicator;  
	}
	
}
