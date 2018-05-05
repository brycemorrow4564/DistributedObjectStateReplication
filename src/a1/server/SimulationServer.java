package a1.server;

import assignments.util.mainArgs.ServerArgsProcessor;
import util.annotations.Tags;
import util.tags.DistributedTags;
import util.trace.bean.BeanTraceUtility;
import util.trace.factories.FactoryTraceUtility;
import util.trace.misc.ThreadDelayed;
import util.trace.port.consensus.ConsensusTraceUtility;
import util.trace.port.nio.NIOTraceUtility;
import util.trace.port.rpc.gipc.GIPCRPCTraceUtility;
import util.trace.port.rpc.rmi.RMITraceUtility;

@Tags({DistributedTags.SERVER, DistributedTags.RMI, DistributedTags.GIPC, DistributedTags.NIO})
public class SimulationServer {
	
	protected ServerCommunicator communicator;
	
	protected String registryHost;
	protected int gipcPort;  		  	
	protected int registryPort; 
	protected int nioPort; 			
	
	public SimulationServer(String[] args) {
		getArgs(args); 
		ServerStateFactory.setServer(this);
		communicator = new ServerCommunicator(this, nioPort, gipcPort, registryHost, registryPort);
		setupTracing(); 
	}	
	
	public ServerCommunicator getCommunicator() { 
		return communicator; 
	}
	
	private static void setupTracing() {
		//FactoryTraceUtility.setTracing();
		//BeanTraceUtility.setTracing();
//		NIOTraceUtility.setTracing(); 
//		RMITraceUtility.setTracing();
//		//ConsensusTraceUtility.setTracing();
//		ThreadDelayed.enablePrint();
//		GIPCRPCTraceUtility.setTracing();
	}

	private void getArgs(String[] args) {
		gipcPort = ServerArgsProcessor.getGIPCServerPort(args);
		registryHost = ServerArgsProcessor.getRegistryHost(args); 
		registryPort = ServerArgsProcessor.getRegistryPort(args);
		nioPort = ServerArgsProcessor.getServerPort(args);
	}
	
	public static void main(String[] args) { 
		args = ServerArgsProcessor.removeEmpty(args); 
		new SimulationServer(args);
	}

}
