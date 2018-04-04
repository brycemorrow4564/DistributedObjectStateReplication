package util.trace.port.consensus;

import inputport.rpc.RemoteCall;
import util.annotations.ComponentWidth;
import util.annotations.DisplayToString;
import util.trace.TraceableInfo;
@DisplayToString(true)
@ComponentWidth(1000)
public class ProposalLearnedNotificationSent extends TraceableInfo {
	

	public ProposalLearnedNotificationSent(String aMessage, Object aSource, String anObjectName, float aProposalNumber, Object aProposal) {
		super(aMessage, aSource );
	}
	
	
	public static ProposalLearnedNotificationSent newCase(Object aSource, String anObjectName, float aProposalNumber, Object aProposal) {
    	String aMessage =  anObjectName + "," + aProposalNumber + "=" + aProposal;
    	ProposalLearnedNotificationSent retVal = new ProposalLearnedNotificationSent(aMessage, aSource, anObjectName, aProposalNumber, aProposal);
   	    retVal.announce();
    	return retVal;

	}
	
	static {
//		Tracer.setKeywordDisplayStatus(CallReceived.class, true);
	}

}
