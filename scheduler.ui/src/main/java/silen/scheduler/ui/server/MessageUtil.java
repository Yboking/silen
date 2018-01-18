package silen.scheduler.ui.server;

import javax.annotation.Resource;

import silen.scheduler.ui.valuebean.ClientMessage;

public class MessageUtil {

	static SocketServer server;

	@Resource
	public void setServer(SocketServer s) {
		server = s;
	}

	public static void sendMessage(ClientMessage m) {

		server.broadcast(m.getEventName(), m.getMessage());
	}

}
