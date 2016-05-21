package hu.unideb.inf.Client;

import hu.unideb.inf.Client.Control.ClientService;
import hu.unideb.inf.Client.Model.User;
import hu.unideb.inf.Client.View.ViewController;

public class Main {
	
	public static User currentUser;
	public static ViewController viewController;
	public static ClientService clientService;
	
	public static void main(String[] args) {
		viewController = new ViewController();
		try {
			clientService = new ClientService("127.0.0.1", 8000);
			viewController.show();
		} finally {
			clientService.disconnect();
		}
	}	
}
