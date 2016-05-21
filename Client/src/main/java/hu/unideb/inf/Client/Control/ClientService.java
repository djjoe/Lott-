package hu.unideb.inf.Client.Control;

import hu.unideb.inf.Client.Main;
import hu.unideb.inf.Client.Model.User;
import javafx.scene.control.Alert;

public class ClientService {

	Client client;

	public ClientService(String host, int port) {
		client = new Client(host, port);
		client.start();
	}

	public void send(String message) {
		if (client.channel.isActive()) {
			client.channel.write(message);
		} else {
			Main.viewController.errorAlert("Hiba", "Nincs kapcsolat a szerverrel", Alert.AlertType.ERROR);
		}
	}

	public void disconnect() {
		client.channel.disconnect();
		client.group.shutdownGracefully();
		client.interrupt();
		while (client.isAlive()) {
		}
	}

	public void login(String username, String password) {
		send("LOGIN;" + username + ";" + password);
	}

	public void setCurrentUser(String message) {
		String[] fields = message.split(";");
		Main.currentUser = new User(fields[1], fields[2], fields[3], Integer.parseInt(fields[4]),
				Integer.parseInt(fields[5]));
		System.out.println(Main.currentUser);
	}

	public void newMessage(String message) {
		if (message.startsWith("USER"))
			setCurrentUser(message);
		System.out.println(message);
	}

	public void register(String username, String password, String name, String email, Integer age) {
		send("REG;" + username + ";" + password + ";" + name + ";" + email + ";" + age);
	}
}
