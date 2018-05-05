package a1.client;

import util.annotations.Tags;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism;
import util.interactiveMethodInvocation.SimulationParametersController;
import util.tags.DistributedTags;
import util.trace.bean.BeanTraceUtility;
import util.trace.bean.NotifiedPropertyChangeEvent;
import util.trace.factories.FactoryTraceUtility;
import util.trace.misc.ThreadDelayed;
import util.trace.port.PerformanceExperimentEnded;
import util.trace.port.PerformanceExperimentStarted;
import util.trace.port.consensus.ConsensusTraceUtility;
import util.trace.port.nio.NIOTraceUtility;
import util.trace.port.objects.ObjectTraceUtility;
import util.trace.port.rpc.gipc.GIPCRPCTraceUtility;
import util.trace.port.rpc.rmi.RMITraceUtility;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Scanner;

import a1.common.message.Message;
import a1.common.message.Message.MsgType;
import a1.common.message.Message.ProposalType;
import a1.client.gipc.GIPCClientCommunicator;
import a1.client.rmi.RMIClientCommunicator;
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
	
	public void printExperiments() {
		for (String e : experimentLog) { System.out.println(e); }
	}

	public void addToExperimentLog(String desc) {
		experimentLog.add(desc);
	}

	public void exitWithCode(int aCode) {
		System.exit(aCode);
	}

	public void updateSimulationConnectivity(BroadcastMode mode) {
		simulation.setConnectedToSimulation(!(mode == BroadcastMode.ATOMIC));
	}

	private void initCommunicator() {
		communicator = new ClientCommunicator(this, simulation, serverHost, serverPort, registryHost, registryPort, gipcPort);
	}

	private void initSimulation() {
		simulation = BeauAndersonFinalProject.createSimulation("", 0, 0, 450, 765, 0, 0);
		simulation.addPropertyChangeListener(this);
	}

	private void setupFactories() {
		ClientCommunicatorFactory.setCommunicator(communicator);
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
//		FactoryTraceUtility.setTracing();
//		BeanTraceUtility.setTracing();
//		NIOTraceUtility.setTracing();
//		RMITraceUtility.setTracing();
//		ConsensusTraceUtility.setTracing();
//		ThreadDelayed.enablePrint();
//		GIPCRPCTraceUtility.setTracing();
//		ObjectTraceUtility.setTracing();
	}

	
	private void initialize(boolean experiment) {
		setupTracing();
		setupArgsProcessor();
		initSimulation();
		initCommunicator();
		setupFactories();
		communicator.performRpcSetup(simulation); 
		startProcessingArgs();
	}
	
	public void handleSimulationCommand(String cmd) {
		Message msg = new Message(MsgType.CTS_Proposal); 
		msg.setProposalType(ProposalType.SimulationCommand);
		msg.setSenderIpcMech(Util.getIpcMechanismFromState());
		msg.setSenderBMode(Util.getBroadcastModeFromState());
		msg.setCommandToExecute(cmd);
		communicator.sendMessageToServer(msg);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals("InputString")) return;
		NotifiedPropertyChangeEvent.newCase(this, evt, null);
		if (Util.getBroadcastModeFromState() != BroadcastMode.LOCAL) {
			handleSimulationCommand((String) evt.getNewValue());
		}
	}

	private static void launchClient(String[] args, boolean experiment) {
		 (new SimulationClient(args))
			 .initialize(experiment);
	}

	public static void main(String[] args, boolean experiment) {
		launchClient(ClientArgsProcessor.removeEmpty(args), experiment);
	}
	
	public void assignment2Experiment() {
		int numCommands = 1000; 
		String[] commands = Util.generateRandomSimulationCommands(numCommands);
		BroadcastMode[] bModes = {BroadcastMode.ATOMIC, BroadcastMode.NON_ATOMIC, BroadcastMode.LOCAL};
		IPCMechanism mech = IPCMechanism.RMI; 
		ClientStateFactory.getState().setIPCMechanism(mech);
		Long start, end; 
		Scanner stopper = new Scanner(System.in);
		String desc; 
		for (int i = 0; i < bModes.length; i++) {
			BroadcastMode currMode = bModes[i];
			if (currMode == BroadcastMode.LOCAL) {
				ClientStateFactory.getState().setLocalProcessingOnly(true);
			} else if (currMode == BroadcastMode.ATOMIC) {
				ClientStateFactory.getState().setAtomicBroadcast(true);
				ClientStateFactory.getState().setLocalProcessingOnly(false);
			} else { //currMode == BroadcastMode.NON_ATOMIC
				ClientStateFactory.getState().setAtomicBroadcast(false);
				ClientStateFactory.getState().setLocalProcessingOnly(false);
			}
			ClientStateFactory.getState().updateSimulationConnectivity(); 
			start = (Long) System.nanoTime(); 
			for (int j = 0; j < commands.length; j++) {
				simulation.setInputString(commands[j]);
			}
			end = (Long) System.nanoTime(); 
			desc = "IPC: "+ mech.toString() + " --- BROADCAST: " + currMode.toString() + 
					" --- Duration: " + ((end-start)/Math.pow(10, 9));
			addToExperimentLog(desc);
			System.out.println(desc + "\nClick to continue");
			stopper.nextLine();
		}
		printExperiments(); 
	}
	
	
	public void assignment3Experiment() {
		int numCommands = 500; 
		String[] commands = Util.generateRandomSimulationCommands(numCommands);
		BroadcastMode[] bModes = {BroadcastMode.ATOMIC, BroadcastMode.NON_ATOMIC, BroadcastMode.LOCAL};
		IPCMechanism[] ipcMechs = {IPCMechanism.NIO, IPCMechanism.GIPC, IPCMechanism.RMI};
		Long start, end; 
		Scanner stopper = new Scanner(System.in);
		String desc; 
		for (int x = 0; x < ipcMechs.length; x++) {
			for (int i = 0; i < bModes.length; i++) {
				BroadcastMode currMode = bModes[i];
				IPCMechanism currMech = ipcMechs[x];
				ClientStateFactory.getState().setIPCMechanism(currMech);
				if (currMode == BroadcastMode.LOCAL) {
					ClientStateFactory.getState().setLocalProcessingOnly(true);
				} else if (currMode == BroadcastMode.ATOMIC) {
					ClientStateFactory.getState().setAtomicBroadcast(true);
					ClientStateFactory.getState().setLocalProcessingOnly(false);
				} else { //currMode == BroadcastMode.NON_ATOMIC
					ClientStateFactory.getState().setAtomicBroadcast(false);
					ClientStateFactory.getState().setLocalProcessingOnly(false);
				}
				ClientStateFactory.getState().updateSimulationConnectivity(); 
				start = (Long) System.nanoTime(); 
				for (int j = 0; j < commands.length; j++) {
					simulation.setInputString(commands[j]);
				}
				end = (Long) System.nanoTime(); 
				desc = "IPC: "+ currMech.toString() + " --- BROADCAST: " + currMode.toString() + 
						" --- Duration: " + ((end-start)/Math.pow(10, 9));
				addToExperimentLog(desc);
				System.out.println(desc + "\nClick to continue");
				stopper.nextLine();
			}
		}
		printExperiments(); 
	}
	
	
}
