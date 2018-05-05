package a1.common.rpc;

import java.lang.reflect.Method;

import a1.common.message.Message;

public class PendingRequest {
	
	protected ADistributedServerRelayer source; 
	protected Method method; 
	protected Object[] args; 
	
	public PendingRequest(ADistributedServerRelayer aSource, Method aMethod, Object[] someArgs) {
		source = aSource; 
		method = aMethod; 
		args = someArgs;
	}
	
	public void fufillRequest() {
		try {
			method.invoke(source, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
