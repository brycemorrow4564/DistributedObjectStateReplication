package inputport.rpc;



import inputport.rpc.simplex.SimplexRPC;

import java.lang.reflect.Method;

import util.trace.Tracer;
import util.trace.port.rpc.ProxyMethodForwardedToPort;
import util.trace.port.rpc.RemoteCallFinished;
import util.trace.port.rpc.RemoteCallInitiated;

public  class AnRPCProxyInvocationHandler extends ACachingAbstractRPCProxyInvocationHandler {	
	public AnRPCProxyInvocationHandler (SimplexRPC theRPCPort, String aDestination, Class aClass, String aName ) {
		super(theRPCPort, aDestination, aClass, aName);
	}	
	@Override
	protected Object call(String aDestination, String aName, Method aMethod, Object[] args) {
//		RemoteCallInitiated.newCase(this, aDestination, aName, aMethod, args);
		Object retVal = rpcInputPort.call(aDestination, aName, aMethod, args);
//		RemoteCallFinished.newCase(this, aDestination, aName, aMethod, args, retVal);
		return retVal;
	}
	@Override
	protected Object call(String aDestination, Method aMethod, Object[] args) {
		Object retVal = rpcInputPort.call(aDestination, aMethod, args);
		return retVal;
	}	
	@Override
	protected Object call(String aDestination, Class aClass, Method method, Object[] args) {
//		Object retVal = rpcInputPort.call(destination, remoteType, method,  args);
		Tracer.info(this, "Invoking call method with destination:" + destination + " method:" + method + " args:" + args);
		ProxyMethodForwardedToPort.newCase(this, aDestination, name, method, args);
		Object retVal = rpcInputPort.call(aDestination, aClass, method,  args);
		return retVal;
	}
}
