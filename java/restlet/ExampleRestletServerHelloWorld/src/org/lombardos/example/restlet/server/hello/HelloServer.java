package org.lombardos.example.restlet.server.hello;

import org.restlet.Server;
import org.restlet.data.Protocol;

public class HelloServer {

	public static void main(String[] args) {
		Server helloServer = new Server(Protocol.HTTP,8111,HelloServerResource.class);
		try {
			helloServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
