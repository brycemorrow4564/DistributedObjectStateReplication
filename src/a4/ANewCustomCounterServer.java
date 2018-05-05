package a4;

import examples.gipc.counter.customization.ACustomCounterServer;
import examples.gipc.counter.customization.ATracingFactorySetter;
import examples.gipc.counter.customization.FactorySetterFactory;
import examples.gipc.counter.layers.AMultiLayerCounterServer;
import inputport.datacomm.duplex.object.DuplexObjectInputPortSelector;
import inputport.rpc.duplex.DuplexReceivedCallInvokerSelector;
import inputport.rpc.duplex.DuplexSentCallCompleterSelector;
import util.annotations.Comp533Tags;
import util.annotations.Tags;
import util.trace.port.objects.ObjectTraceUtility;
import util.trace.port.rpc.RPCTraceUtility;

@Tags({Comp533Tags.CUSTOM_RPC_SERVER})
public class ANewCustomCounterServer extends ACustomCounterServer {

	public static void setFactories() {
		DuplexObjectInputPortSelector.setDuplexInputPortFactory(new GIPCInputPortFactory());	
		DuplexSentCallCompleterSelector.setDuplexSentCallCompleterFactory(new SynchronousSentCallCompleterFactory());
		DuplexReceivedCallInvokerSelector.setReceivedCallInvokerFactory(new AsynchronousCustomDuplexReceivedCallInvokerFactory());
	}

	public static void main(String[] args) {
		ObjectTraceUtility.setTracing();
		RPCTraceUtility.setTracing();
		setFactories();
		//Methods called in this way so as to avoid overriding our call to setFactories() 
		AMultiLayerCounterServer.launch();
		ACustomCounterServer.addConnectListener();
		ACustomCounterServer.doReceive();
	}
	
}
