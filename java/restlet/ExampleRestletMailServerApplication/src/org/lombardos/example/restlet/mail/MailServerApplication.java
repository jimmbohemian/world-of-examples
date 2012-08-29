package org.lombardos.example.restlet.mail;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.data.Status;

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
		Restlet retval = null;
		
		// Tracer is our actual app
		Tracer tracer;
		retval = tracer = new Tracer(getContext());
		
		// Uncomment these for blocking
		Blocker blocker;
		retval = blocker = new Blocker(getContext());
		blocker.getBlockedAddresses().add("0:0:0:0:0:0:0:1%0");	// or "127.0.0.1", or comment out
		blocker.setNext(tracer);
		
		// Return the Restlet
		return retval;
	}
	
	public class Tracer extends Restlet {
		public Tracer(Context context){
			super(context);
		}
		@Override
		public void handle(Request request, Response response) {
			System.out.println("Tracer.handle");
			StringBuilder sb = new StringBuilder();
			sb.append("Method       : ").append(request.getMethod()).append('\n');
			sb.append("Resource URI : ").append(request.getResourceRef()).append('\n');
			sb.append("IP address   : ").append(request.getClientInfo().getAddress()).append('\n');
			sb.append("Agent name   : ").append(request.getClientInfo().getAgentName()).append('\n');
			sb.append("Agent version: ").append(request.getClientInfo().getAgentVersion()).append('\n');
			response.setEntity(sb.toString(), MediaType.TEXT_PLAIN);
		}
	}
	
	public class Blocker extends org.restlet.routing.Filter {
		private final Set<String> blockedAddresses;

		public Blocker(Context context) {
			super(context);
			this.blockedAddresses = new CopyOnWriteArraySet<String>();
		}

		@Override
		protected int beforeHandle(Request request, Response response) {
			int result = STOP;
			if (getBlockedAddresses().contains(request.getClientInfo().getAddress())) {
				response.setStatus(Status.CLIENT_ERROR_FORBIDDEN, "Your IP address was blocked");
				System.err.println("Blocked");
			} else {
				result = CONTINUE;
			}
			return result;
		}

		public Set<String> getBlockedAddresses() {
			return blockedAddresses;
		}
	}
}
