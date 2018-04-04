package examples.mvc.rmi.duplex;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;


public class DistributedRMIRegistryStarter {
	public static void main (String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
