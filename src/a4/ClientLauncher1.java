package a4;

import util.annotations.Tags;
import util.annotations.Comp533Tags;

@Tags({ Comp533Tags.CUSTOM_RPC_CLIENT1 })
public class ClientLauncher1 {
	
	public static void main(String[] args) {
		ANewCustomCounterClient.launch("Client1");
	}
}
