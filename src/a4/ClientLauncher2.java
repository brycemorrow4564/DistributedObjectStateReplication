package a4;

import util.annotations.Comp533Tags;
import util.annotations.Tags;

@Tags({Comp533Tags.CUSTOM_RPC_CLIENT2})
public class ClientLauncher2 {
	
	public static void main(String[] args) {
		ANewCustomCounterClient.launch("Client2");
	}
	
}
