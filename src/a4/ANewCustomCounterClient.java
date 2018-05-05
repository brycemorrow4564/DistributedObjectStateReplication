package a4;

import java.util.Scanner;

import examples.gipc.counter.customization.ACustomCounterClient;
import examples.gipc.counter.customization.ATracingFactorySetter;
import examples.gipc.counter.customization.FactorySetterFactory;
import examples.gipc.counter.layers.AMultiLayerCounterClient;
import inputport.datacomm.duplex.object.DuplexObjectInputPortSelector;
import inputport.datacomm.duplex.object.explicitreceive.ReceiveReturnMessage;
import inputport.rpc.duplex.DuplexReceivedCallInvokerSelector;
import inputport.rpc.duplex.DuplexSentCallCompleterSelector;
import util.trace.port.objects.ObjectTraceUtility;
import util.trace.port.rpc.RPCTraceUtility;

public class ANewCustomCounterClient extends ACustomCounterClient {

	public static void setFactories() {
		DuplexObjectInputPortSelector.setDuplexInputPortFactory(new GIPCInputPortFactory());
		DuplexSentCallCompleterSelector.setDuplexSentCallCompleterFactory(new SynchronousSentCallCompleterFactory());
		DuplexReceivedCallInvokerSelector.setReceivedCallInvokerFactory(new AsynchronousCustomDuplexReceivedCallInvokerFactory());
	}
	
	public static void launch(String aClientName) {
		ObjectTraceUtility.setTracing();
		RPCTraceUtility.setTracing();
		setFactories();
		//Methods called in this way so as to avoid overriding our call to setFactories() 
		AMultiLayerCounterClient.launchClient(aClientName);
		ACustomCounterClient.doReceive();
	}
	
}
