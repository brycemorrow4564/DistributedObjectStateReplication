package a1.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.CTS_Proposal;
import a1.common.message.Message;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import a1.common.message.STC_ProposalExecute;
import a1.util.Test;
import a1.util.Util;
import assignments.util.mainArgs.ServerArgsProcessor;
import util.annotations.Tags;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;
import util.tags.DistributedTags;
import util.trace.bean.BeanTraceUtility;
import util.trace.factories.FactoryTraceUtility;
import util.trace.misc.ThreadDelayed;
import util.trace.port.consensus.ConsensusTraceUtility;
import util.trace.port.nio.NIOTraceUtility;
import util.trace.port.rpc.rmi.RMITraceUtility;

@Tags({DistributedTags.SERVER, DistributedTags.RMI, DistributedTags.NIO})
public class SimulationServer {
	
	private ServerCommunicator communicator;
	
	public SimulationServer(int nioPort, int gipcPort) {
		ServerStateFactory.setServer(this);
		communicator = new ServerCommunicator(this, nioPort, gipcPort);
		setupTracing(); 
	}	
	
	public ServerCommunicator getCommunicator() { 
		return communicator; 
	}
	
	private static void setupTracing() {
		FactoryTraceUtility.setTracing();
		BeanTraceUtility.setTracing();
		NIOTraceUtility.setTracing(); 
		RMITraceUtility.setTracing();
		ConsensusTraceUtility.setTracing();
		ThreadDelayed.enablePrint();
	}

	public static void main(String[] args) { 
		args = ServerArgsProcessor.removeEmpty(args); 
		int gipcPort 		= ServerArgsProcessor.getGIPCServerPort(args); //index 3
		//String registryHost 	= ServerArgsProcessor.getRegistryHost(args); //index 1, for both rmi and gipc 
		//int rmiRegistryPort 	= ServerArgsProcessor.getRegistryPort(args); //index 2, registry rmi port 
		int nioPort 			= ServerArgsProcessor.getServerPort(args); //index 0, nio server port 
		new SimulationServer(nioPort, gipcPort);
	}

}
