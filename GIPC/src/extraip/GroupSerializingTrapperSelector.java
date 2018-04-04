package extraip;
import inputport.InputPort;
import inputport.datacomm.group.GroupNamingSender;
import inputport.datacomm.group.GroupSendTrapperFactory;

import java.nio.ByteBuffer;


public class GroupSerializingTrapperSelector {
	static GroupSendTrapperFactory<Object, ByteBuffer> factory;	
	public static GroupSendTrapperFactory<Object, ByteBuffer> 
		getGroupSendTrapperFactory() {
		return factory;
	}	
	public static void setGroupSendTrapperFactory
		(GroupSendTrapperFactory<Object, ByteBuffer> newVal) {
		factory = newVal;
	}
	public static GroupNamingSender<Object> createGroupSendTrapper(InputPort anInputPort, 
			GroupNamingSender<ByteBuffer> aDestination){
		return factory.createGroupSendTrapper(anInputPort, aDestination);
	}
}
