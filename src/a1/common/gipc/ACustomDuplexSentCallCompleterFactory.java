package a1.common.gipc;

import inputport.rpc.duplex.ADuplexSentCallCompleterFactory;
import inputport.rpc.duplex.DuplexRPCInputPort;
import inputport.rpc.duplex.DuplexSentCallCompleter;
import inputport.rpc.duplex.LocalRemoteReferenceTranslator;

public class ACustomDuplexSentCallCompleterFactory extends ADuplexSentCallCompleterFactory {

	@Override
	public DuplexSentCallCompleter createDuplexSentCallCompleter(DuplexRPCInputPort anInputPort, 
			LocalRemoteReferenceTranslator aTranslator) {
		return new CustomDuplexSentCallCompleter(anInputPort, aTranslator);
	}
}
