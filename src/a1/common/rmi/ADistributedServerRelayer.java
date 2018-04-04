package a1.common.rmi;

import a1.common.message.Message;
import a1.server.ServerCommunicator;

public class ADistributedServerRelayer implements DistributedServerRelayer {

	private ServerCommunicator communicator; 
	
	public ADistributedServerRelayer(ServerCommunicator communicator, DistributedServerRegistrar registrar) {
		this.communicator = communicator; 
	}
	
	@Override
	public synchronized void passMsgToServer(Message clientMsg) {
		communicator.processIncomingMessage(clientMsg, null);
	}
	
}
