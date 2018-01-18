package silen.scheduler.ui.server;

import silen.scheduler.ui.valuebean.ClientMessage;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

public class SocketServer {

	private static SocketIOServer server = null;

	public void broadcast(String eventName, String content) {

		
		if(server == null){
			try {
				Thread.currentThread().join(1000 * 10 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		for (SocketIOClient client : server.getAllClients()) {

			client.sendEvent(eventName, new AckCallback<String>(String.class) {

				@Override
				public void onSuccess(String result) {

					System.out.println("client " + result);
				}

			}, content);
		}
	}
	

	private Thread tasker = new Thread(new Runnable() {

		public void run() {
			System.out.println("socket Server .. Init .. ");
			Configuration config = new Configuration();
			config.setHostname("localhost");
			config.setPort(10015);
			if (server == null) {
				server = new SocketIOServer(config);

				server.addConnectListener(new ConnectListener() {
					// @Override
					public void onConnect(SocketIOClient client) {
						System.out.println(client.getRemoteAddress() + " web客户端接入");
						// logger.info(client.getRemoteAddress() + " web客户端接入");
						client.sendEvent("helloPush", "hello");

					}
				});
			}

			// 握手请求
			server.addEventListener("taskDesc", ClientMessage.class,
					new DataListener<ClientMessage>() {
						// @Override
						public void onData(final SocketIOClient client,
								ClientMessage data, AckRequest ackRequest) {

							System.out.println("receive client message "
									+ data.getMessage());
							// 握手
							if (data.getMessage().equals("hello")) {
								int userid = data.getUid();
								// logger.info(Thread.currentThread().getName()
								// +
								// "web读取到的userid：" + userid);
								System.out
										.println((Thread.currentThread()
												.getName() + "web读取到的userid：" + userid));

								// send message back to client with ack callback
								// WITH data
								client.sendEvent("hellopush",
										new AckCallback<String>(String.class) {
											@Override
											public void onSuccess(String result) {
												// logger.info("ack from client: "
												// +
												// client.getSessionId() +
												// " data: "
												// + result);
												System.out.println(("ack from client: "
														+ client.getSessionId()
														+ " data: " + result));
											}
										}, 20);

							}  
						}
					});

			server.start();

			try {
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			server.stop();

		}
	});

	{
		tasker.start();

	}

}
