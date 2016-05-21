package hu.unideb.inf.server.clientConnector;

import java.util.HashMap;
import java.util.Map;

import hu.unideb.inf.server.Main;
import hu.unideb.inf.server.model.User;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

/**
 * A kliensekkel történő kommunikációt vezérlő osztály.
 */
public class ClientService {

	/**
	 * A kliensekkel történő kommunikációt végző osztály.
	 */
	private Server server;
	
	public static Map<User, Channel> userChannels;
	public static ChannelGroup channels;
	private int port;

	public ClientService(int port) {
		this.port = port;
		userChannels = new HashMap<User, Channel>();
		channels = new DefaultChannelGroup();
		server = new Server(port);
	}

	public void startServer() {
		if (server == null || !server.isAlive()) {
			server = new Server(port);
			server.start();
		}
	}

	public void stopServer() {
		for (User user : userChannels.keySet()) {
			userChannels.get(user).disconnect();
		}
		for (Channel channel : channels)
			channel.disconnect();
		if (server != null) {
			server.interrupt();
			while (server.isAlive()) {}
		}
	}

	public boolean isRunning() {
		return server.isAlive();
	}

	public String newMessage(Channel channel, String message) {
		System.out.println(message);
		if (message.startsWith("LOGIN")) {
			String[] fields = message.split(";");
			User tmp = login(fields[1], fields[2], channel);
			return tmp != null ? tmp.toString() : "ERROR";
		} else if (message.startsWith("REG")) {
			String[] fields = message.split(";");
			if (fields.length > 5)
				return register(fields[1], fields[2].toUpperCase(), fields[3], fields[4], Integer.parseInt(fields[5]));
			return "ERROR";
		}
		return null;
	}

	private User login(String username, String password, Channel channel) {
		User user = Main.dataService.getUserByUsernameAndPassword(username, password);
		if (user != null) {
			channels.remove(channel);
			userChannels.put(user, channel);
		}
		return user;
	}

	private void logout(User user) {
		userChannels.get(user).disconnect();
		userChannels.remove(user);
	}

	public void forceQuit(Channel channel) {
		if (userChannels.containsValue(channel)) {
			for (User user : userChannels.keySet())
				if (userChannels.get(user).equals(channel))
					logout(user);
		} else {
			channels.remove(channel);
		}
	}

	public String register(String username, String password, String name, String email, Integer age) {
		User user = Main.dataService.createUser(username, password, name, email, age);
		return user != null ? "OK" : "ERROR";
	}
}
