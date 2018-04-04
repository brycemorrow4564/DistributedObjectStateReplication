package inputport.rpc.duplex.echoer.example;
import inputport.ConnectionListener;
import inputport.InputPort;
import inputport.rpc.duplex.AnAbstractDuplexRPCServerPortLauncher;
import inputport.rpc.duplex.DuplexRPCServerInputPort;
import inputport.rpc.duplex.ReplyRPCProxyGenerator;
import inputport.rpc.duplex.mvc.singleuser.example.DuplexRPCClientMVCLauncher;
import port.ATracingConnectionListener;
import examples.mvc.local.duplex.ADuplexUpperCaser;
import examples.mvc.local.duplex.Counter;
import examples.mvc.local.duplex.DuplexUpperCaser;
public class AnEchoingDuplexRPCServerLauncher extends AnAbstractDuplexRPCServerPortLauncher implements EchoingDuplexServerLauncher  {
	public AnEchoingDuplexRPCServerLauncher(String aServerName,
			String aServerPort) {
		super (aServerName, aServerPort);
	}
	protected  ConnectionListener getConnectionListener (InputPort anInputPort) {
		return new ATracingConnectionListener(anInputPort);
	}	
	protected void registerRemoteObjects() {
		DuplexRPCServerInputPort aDuplexRPCServerInputPort = (DuplexRPCServerInputPort) mainPort;
		DuplexUpperCaser upperCaser = getUpperCaser();
		aDuplexRPCServerInputPort.register(upperCaser);
	}	
	protected Counter counter;
	protected  void createProxies() {
    	counter = (Counter) ReplyRPCProxyGenerator.generateReplyRPCProxy((DuplexRPCServerInputPort) mainPort, (registeredCounterClass()));
	}
	protected Class registeredCounterClass() {
		return DuplexRPCClientMVCLauncher.REGISTERED_COUNTER_CLASS;
	}	
	protected DuplexUpperCaser getUpperCaser() {
		return new ADuplexUpperCaser(counter);
	}	
	public static void main (String[] args) {
		(new AnEchoingDuplexRPCServerLauncher(ECHOER_SERVER_NAME, ECHOER_SERVER_ID)).launch();
	}	
}
