package hu.unideb.inf.Client.Control;

import hu.unideb.inf.Client.Main;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

public class ClientHandler extends ChannelInboundMessageHandlerAdapter<String> {

	public void messageReceived(ChannelHandlerContext arg0, String arg1) throws Exception {
		Main.clientService.newMessage(arg1);
	}

}
