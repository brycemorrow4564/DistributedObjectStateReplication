package inputport.rpc.duplex.mvc.multiuser.example;


import inputport.rpc.duplex.mvc.singleuser.example.ADuplexRPCClientMVCLauncher;
import inputport.rpc.simplex.mvc.example.ASimplexRPCClientMVCLauncher;
import inputport.rpc.simplex.mvc.example.SimplexRPCServerMVCLauncher;





public class BobDuplexRPCClientMVCLauncher extends ASimplexRPCClientMVCLauncher  {
	public static final String  USER_NAME = "Bob";
	
	
	public static void main (String[] args) {		
		(new ADuplexRPCClientMVCLauncher(USER_NAME, "localhost", SimplexRPCServerMVCLauncher.MVC_SERVER_ID, SimplexRPCServerMVCLauncher.MVC_SERVER_NAME )).launch();

	}
	


}
