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
import util.trace.port.rpc.rmi.RMITraceUtility;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import a1.common.message.CTS_Proposal;
import a1.common.message.Message;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.util.Util;
import assignments.util.MiscAssignmentUtils;
import assignments.util.inputParameters.ASimulationParametersController;
import assignments.util.mainArgs.ClientArgsProcessor;
import main.BeauAndersonFinalProject;
import stringProcessors.HalloweenCommandProcessor;

@Tags({DistributedTags.CLIENT, DistributedTags.RMI, DistributedTags.NIO})
public class SimulationClient implements PropertyChangeListener {

	private HalloweenCommandProcessor simulation;
	private ClientCommunicator communicator;
	private SimulationParametersController commandProcessor;
	private ArrayList<String> experimentLog;
	private String clientName;

	public SimulationClient(String aClientName) {
		clientName = aClientName;
		experimentLog = new ArrayList<String>();
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
		gipcComm.exportObjects();
		gipcComm.acquireProxies();
	}

	private void setupRMICommunicator() {
		RMIClientCommunicator rmiComm = (RMIClientCommunicator) communicator.getSubCommunicatorOfType(IPCMechanism.RMI);
		rmiComm.exportObjects();
		rmiComm.acquireProxies();
	}
	
	private ClientCommunicator initAndGetCommunicator(String aServerHost, int aServerPort, int gipcPort) {
		return new ClientCommunicator(this, aServerHost, aServerPort, gipcPort, simulation);
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
	}

	private void initialize(String aServerHost, int aServerPort, String aClientName, int gipcPort) {
		setupTracing();
		setupArgsProcessor();
		simulation = initAndGetSimulation();
		communicator = initAndGetCommunicator(aServerHost, aServerPort, gipcPort);
		setupClientStateFactory(); //MUST be done before setting up rmiCommunicator. sets communicator and client properties
		communicator.createDistributedObjects(simulation); //MUST be done after setting up client state factory
		communicator.notifySubCommunicatorsOfDistributedObjects();
		setupRMICommunicator(); 
		setupGIPCCommunicator(); //MUST be done after setting up rmiCommunicator. relies on id assigned by server returned as rmi callback 
		startProcessingArgs();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals("InputString")) return;
		System.out.println("PROP CHANGE: " + Util.getBroadcastModeFromState().name());
		BroadcastMode currBMode = Util.getBroadcastModeFromState(); 
		if (currBMode != BroadcastMode.LOCAL) {
			String newCommand = (String) evt.getNewValue();
			ClientState state = ClientStateFactory.getState(); 
			Message msg = new CTS_Proposal(
					ProposalType.SimulationCommand, 
					currBMode, 
					state.getIPCMechanism(), 
					state.getConsensusAlgorithm());
			msg.setCommandToExecute(newCommand);
			if (state.getIPCMechanism() != IPCMechanism.NIO) {
				msg.setRpcRegistryKey(communicator.getRpcId());
			}
			communicator.sendMessageToServer(msg);
		}
	}

	private static void launchClient(String aServerHost, int aServerPort, String aClientName, int gipcPort) {
		(new SimulationClient(aClientName)).initialize(aServerHost, aServerPort, aClientName, gipcPort);
	}

	public static void main(String[] args) {
		args = ClientArgsProcessor.removeEmpty(args);
		MiscAssignmentUtils.setHeadless(ClientArgsProcessor.getHeadless(args));
		String 	serverHost = ClientArgsProcessor.getServerHost(args); 
		int 		serverPort = ClientArgsProcessor.getServerPort(args);
		String 	clientName = ClientArgsProcessor.getClientName(args); 
		int 		gipcPort = ClientArgsProcessor.getGIPCPort(args); 
		launchClient(serverHost, serverPort, clientName, gipcPort);
	}
	
}
