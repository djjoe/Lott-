package hu.unideb.inf.Client.Control;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client extends Thread{
	
	private final String host;
	private final Integer port;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	Channel channel;
	EventLoopGroup group;
	
	public void run() {
		group = new NioEventLoopGroup();
		
		try {
			Bootstrap bootstrap = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ClientInitializer());
			
			channel = bootstrap.connect(host, port).sync().channel();
			
		} catch (InterruptedException e) {
		} catch (Exception e) {
			System.err.println("A szerver nem elérhető");
			System.exit(1);
		}
	}
	
}
