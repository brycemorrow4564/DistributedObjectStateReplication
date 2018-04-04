package consensus.asynchronous.sequential;

import inputport.ConnectionListener;
import consensus.ConsensusMechanism;

public interface LearnedMechanism<StateType> extends 
		ConsensusMechanism<StateType>,
		Learned<StateType> {
		
//	 void setPeerProxy(RemoteTwoPartyPeer<StateType> aPeerProxy);
		
		
}
