package hu.unideb.inf.server;

import hu.unideb.inf.server.clientConnector.ClientService;
import hu.unideb.inf.server.controller.Drawer;
import hu.unideb.inf.server.model.DataService;
import hu.unideb.inf.server.view.Console;

/**
 * Az alkalmazás fő pontja.
 * 
 * @author Tuza József
 *
 */
public class Main {
	
	/**
	 * A kliensekkel történő kommunikációt vezérlő osztály.
	 */
	public static ClientService clientService;
	
	/**
	 * Az adatbázissal kapcsolatot tartó osztály.
	 */
	public static DataService dataService;
	
	/**
	 * A szelvények sorsolását végző osztály.
	 */
	public static Drawer drawer;
	
	/**
	 * Portszám, amire a kliensek csatlakoznak.
	 */
	private static int port;
	
	/**
	 * Az alkalmazás belépési pontja
	 * 
	 * @param args parancssori argumentumok.
	 */
	public static void main(String[] args) {
		if(args.length == 0)
			port = 8000;
		else
			port = Integer.parseInt(args[0]);
		
		dataService = new DataService();
		clientService = new ClientService(port);
		drawer = new Drawer();
		
		try {
			new Console(clientService).run();
		} finally {
			clientService.stopServer();
			dataService.close();
		}
	}
}
