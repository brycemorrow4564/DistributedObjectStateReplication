package multiserverport.datacomm.group.object;

import inputport.datacomm.group.AGroupTrapperSelector;
import inputport.datacomm.group.GroupTrapperSelector;
import inputport.datacomm.group.object.ASerializingGroupForwarderFactory;

import java.nio.ByteBuffer;


public class MultiServerObjectGroupTranslatingIPTrapperSelector {
	static GroupTrapperSelector<Object, ByteBuffer> trapperSelector =  
		new AGroupTrapperSelector();


	public static GroupTrapperSelector<Object, ByteBuffer> getTrapperSelector() {
		return trapperSelector;
	}

	public static void setTrapperSelector(
			GroupTrapperSelector<Object, ByteBuffer> trapperSelector) {
		MultiServerObjectGroupTranslatingIPTrapperSelector.trapperSelector = trapperSelector;
	}
	
	static {
		// this should go in port layncher support
		trapperSelector.setGroupSendTrapperFactory(new ASerializingGroupForwarderFactory());

	
	
	}

}
