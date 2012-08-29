package org.lombardos.example.restlet.mail;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;

public class MailServerApplication extends Application {
	
	public MailServerApplication(){
		setName("RESTful Mail Server");
		setDescription("Example for 'Restlet in Action' book");
		setOwner("Restlet SAS");
		setAuthor("The Restlet Team");
	}

	public static void main(String[] args) throws Exception {
		Server mailServer = new Server(Protocol.HTTP,8111);
		mailServer.setNext(new MailServerApplication());
		mailServer.start();
	}
	
	public Restlet createInboundRoot(){
		return new Restlet() {

			@Override
			public void handle(Request request, Response response) {
				StringBuilder sb = new StringBuilder();
				sb.append("Method       : ").append(request.getMethod()).append('\n');
				sb.append("Resource URI : ").append(request.getResourceRef()).append('\n');
				sb.append("IP address   : ").append(request.getClientInfo().getAddress()).append('\n');
				sb.append("Agent name   : ").append(request.getClientInfo().getAgentName()).append('\n');
				sb.append("Agent version: ").append(request.getClientInfo().getAgentVersion()).append('\n');
				response.setEntity(sb.toString(), MediaType.TEXT_PLAIN);
			}
		};
	}
}
