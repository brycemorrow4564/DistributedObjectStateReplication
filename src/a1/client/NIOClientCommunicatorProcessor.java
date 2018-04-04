package a1.client;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.CTS_ProposalResponse;
import a1.common.message.Message;
import a1.common.message.MessageTypeInterpreter.ProposalType;
import a1.common.message.STC_ProposalAcceptRequest;
import a1.common.message.STC_ProposalExecute;
import a1.common.nio.NIOByteBufferWrapper;
import a1.util.Util;
import util.interactiveMethodInvocation.IPCMechanism;

public class NIOClientCommunicatorProcessor implements Runnable {
	
	ArrayBlockingQueue<NIOByteBufferWrapper> commandQueue;
	SimulationClient client; 
	
	public NIOClientCommunicatorProcessor(ArrayBlockingQueue<NIOByteBufferWrapper> commandQueue, SimulationClient client) {
		this.commandQueue = commandQueue; 
		this.client = client; 
	}
	
	private void processMessage(Message msg) {
		System.out.println("We read this msg from the buffer: " + msg.toString());
		STC_ProposalAcceptRequest accReq = null; 
		STC_ProposalExecute propExec = null; 
		try { accReq = (STC_ProposalAcceptRequest) msg; } catch (Exception e) {}
		try { propExec = (STC_ProposalExecute) msg; } catch (Exception e) {}
		ClientState state = ClientStateFactory.getState();
		ProposalType propType = msg.getType();
		if (accReq != null) {
			//AcceptRequest received from server
			boolean propAccepted = !state.isRejectMetaStateChange() && msg.getType() != ProposalType.SimulationCommand; //NEVER veto sim commmands  
			Message responseMsg = new CTS_ProposalResponse(propType, propAccepted, 
					Util.getBroadcastModeFromState(), 
					Util.getIpcMechanismFromState(), 
					Util.getConsensusAlgorithmFromState());
			client.getCommunicator().sendMessageToServer(responseMsg);
		} else { 
			//ProposalExecute received from server 
			switch (propType) {
				case SimulationCommand: 
					//Done so that inputing command does not fire a property change event 
					if (Util.getBroadcastModeFromState() == BroadcastMode.ATOMIC) {
						client.getSimulation().setConnectedToSimulation(true);
						client.getSimulation().processCommand(msg.getCommandToExecute());
						client.getSimulation().setConnectedToSimulation(false);
					} else {
						client.getSimulation().processCommand(msg.getCommandToExecute());
					}
					break; 
				case BroadcastModeChange: 
					switch (msg.getBModeToSet()) {
						case ATOMIC: 		state.setAtomicBroadcast(true); break; 
						case NON_ATOMIC: 	state.setAtomicBroadcast(false); break; 
						case LOCAL: 			state.setLocalProcessingOnly(true); break;  
					}
					client.updateSimulationConnectivity(Util.getBroadcastModeFromState());
					break; 
				case IpcMechanismChange: 
					switch (msg.getIpcMechToSet()) {
						case NIO: state.setIPCMechanism(IPCMechanism.NIO); break; 
						case RMI: state.setIPCMechanism(IPCMechanism.RMI); break;
						default: break; 
					}
					break; 
				default: 
					System.out.println("ERROR: In read processor thread. Read from buffer but msg has unknown type");
			}
		}
	}
	
	@Override
	public void run() {
		NIOByteBufferWrapper		wrapper = null; 
		ByteBuffer 				buf	= null;
		while (true) {
			try   { 
				wrapper = this.commandQueue.take(); 
				buf	= wrapper.getByteBuffer(); 
				byte[] arr = new byte[buf.remaining()];
				buf.get(arr);
				Message msg = Util.deserializeMessage(arr);
				processMessage(msg); 
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			}
		}
	}

}
