package a1.common.rmi;

import a1.common.message.Message;
import a1.server.ServerCommunicator;

public class ADistributedServerRelayer implements DistributedServerRelayer {

	private ServerCommunicator servComm; 
	public static int clientCounter = 1; 
	
	public ADistributedServerRelayer(ServerCommunicator communicator) {
		this.servComm = communicator; 
	}
	
	public synchronized String rmiClientJoin(DistributedClientManager clientManager) {
		String id = "Client" + clientCounter; 
		clientCounter += 1;
		servComm.rmiJoinClient(id, clientManager);  
		return id; 
	}
	
	@Override
	public void gipcClientDeposit(String id, DistributedClientManager clientManager) {
		servComm.gipcJoinClient(id, clientManager);
	}
	
	@Override
	public synchronized void passMsgToServer(Message clientMsg) {
		servComm.processIncomingMessage(clientMsg, null);
	}
	
}
