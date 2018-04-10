package a1.client;

import util.annotations.Tags;
import util.interactiveMethodInvocation.IPCMechanism;
import util.interactiveMethodInvocation.SimulationParametersController;
import util.tags.DistributedTags;
import util.trace.bean.BeanTraceUtility;
import util.trace.factories.FactoryTraceUtility;
import util.trace.misc.ThreadDelayed;
import util.trace.port.consensus.ConsensusTraceUtility;
import util.trace.port.nio.NIOTraceUtility;
import util.trace.port.rpc.gipc.GIPCRPCTraceUtility;
import util.trace.port.rpc.rmi.RMITraceUtility;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import a1.common.message.Message.ProposalType;
import a1.common.Util;
import a1.common.InitialConfigurations.BroadcastMode;
import assignments.util.MiscAssignmentUtils;
import assignments.util.inputParameters.ASimulationParametersController;
import assignments.util.mainArgs.ClientArgsProcessor;
import main.BeauAndersonFinalProject;
import stringProcessors.HalloweenCommandProcessor;

@Tags({DistributedTags.CLIENT, DistributedTags.RMI, DistributedTags.GIPC, DistributedTags.NIO})
public class SimulationClient implements PropertyChangeListener {
	
	private HalloweenCommandProcessor simulation;
	private ClientCommunicator communicator;
	private SimulationParametersController commandProcessor;
	private ArrayList<String> experimentLog;
	
	protected String clientName;
	protected int gipcPort;
	protected String headless;
	protected String registryHost;
	protected int registryPort;
	protected String serverHost;
	protected int serverPort;

	public SimulationClient(String[] args) {
		getArgs(args); 
		experimentLog = new ArrayList<String>();
	}
	
	private void getArgs(String[] args) {
		clientName = ClientArgsProcessor.getClientName(args); 
		gipcPort = ClientArgsProcessor.getGIPCPort(args);
		headless = ClientArgsProcessor.getHeadless(args);
		registryHost = ClientArgsProcessor.getRegistryHost(args); 
		registryPort = ClientArgsProcessor.getRegistryPort(args);
		serverHost = ClientArgsProcessor.getServerHost(args);
		serverPort = ClientArgsProcessor.getServerPort(args);
		MiscAssignmentUtils.setHeadless(ClientArgsProcessor.getHeadless(args));
	}

	public String getClientName() {
		return clientName;
	}

	public ClientCommunicator getCommunicator() {
		return communicator;
	}

	public HalloweenCommandProcessor getSimulation() {
		return simulation;
	}

	public void addToExperimentLog(String desc) {
		experimentLog.add(desc);
	}

	public void exitWithCode(int aCode) {
		System.exit(aCode);
	}

	public void updateSimulationConnectivity(BroadcastMode mode) {
		simulation.setConnectedToSimulation(!(mode == BroadcastMode.ATOMIC));
		System.out.println("SIMULATION CONNECTIVITY UPDATED: " + simulation.isConnectedToSimulation());
	}
	
	public void setupGIPCCommunicator() {
		GIPCClientCommunicator gipcComm = (GIPCClientCommunicator) communicator.getSubCommunicatorOfType(IPCMechanism.GIPC);
		String id = communicator.getRpcId(); 
		gipcComm.setClientManagerId(id);
		gipcComm.acquireProxies();
		gipcComm.exportObjects();
	}

	private void setupRMICommunicator() {
		RMIClientCommunicator rmiComm = (RMIClientCommunicator) communicator.getSubCommunicatorOfType(IPCMechanism.RMI);
		rmiComm.acquireProxies();
		rmiComm.exportObjects();
	}
	
	private ClientCommunicator initAndGetCommunicator() {
		return new ClientCommunicator(this, simulation, serverHost, serverPort, registryHost, registryPort, gipcPort);
	}

	private HalloweenCommandProcessor initAndGetSimulation() {
		HalloweenCommandProcessor sim = BeauAndersonFinalProject.createSimulation("", 0, 0, 450, 765, 0, 0);
		sim.addPropertyChangeListener(this);
		return sim;
	}

	private void setupClientStateFactory() {
		ClientStateFactory.setClient(this);
		ClientStateFactory.setCommunicator(communicator);
	}

	private void setupArgsProcessor() {
		commandProcessor = new ASimulationParametersController();
	}

	private void startProcessingArgs() {
		ClientState state = ClientStateFactory.getState();
		commandProcessor.addSimulationParameterListener(state); //ClientState listens for dynamic command line args 
		commandProcessor.processCommands();
	}

	private void setupTracing() {
		FactoryTraceUtility.setTracing();
		BeanTraceUtility.setTracing();
		NIOTraceUtility.setTracing();
		RMITraceUtility.setTracing();
		ConsensusTraceUtility.setTracing();
		ThreadDelayed.enablePrint();
		GIPCRPCTraceUtility.setTracing();
	}

	private void initialize() {
		setupTracing();
		setupArgsProcessor();
		simulation = initAndGetSimulation();
		communicator = initAndGetCommunicator();
		ClientCommunicatorFactory.setCommunicator(communicator);
		//MUST be done AFTER creating communicator so GIPC and RMI communicators are aware of the client state object. 
		setupClientStateFactory();
		//MUST be done after setting up client state factory
		communicator.createDistributedObjects(simulation); 
		communicator.notifyRpcCommunicatorsOfDistributedObjects();
		setupRMICommunicator(); 
		setupGIPCCommunicator();
		startProcessingArgs();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals("InputString")) return;
		System.out.println("PROP CHANGE: " + Util.getBroadcastModeFromState().name());
		BroadcastMode currBMode = Util.getBroadcastModeFromState(); 
		if (currBMode != BroadcastMode.LOCAL) {
			String cmd = (String) evt.getNewValue();
			ClientState state = ClientStateFactory.getState(); 
			Message msg = new Message(MsgType.CTS_Proposal); 
			msg.setProposalType(ProposalType.SimulationCommand);
			msg.setCommandToExecute(cmd);
			if (state.getIPCMechanism() != IPCMechanism.NIO) {
				msg.setRpcRegistryKey(communicator.getRpcId()); //applicable for RMI and GIPC 
			}
			communicator.sendMessageToServer(msg);
		}
	}

	private static void launchClient(String[] args) {
		(new SimulationClient(args)).initialize();
	}

//	(String clientName, int gipcPort, String headless, String registryHost, int registryPort, String serverHost, int serverPort)
	public static void main(String[] args) {
		launchClient(ClientArgsProcessor.removeEmpty(args));
	}
	
}
