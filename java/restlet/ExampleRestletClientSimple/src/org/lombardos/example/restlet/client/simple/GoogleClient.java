package org.lombardos.example.restlet.client.simple;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class GoogleClient {

	public static void main(String[] args) {
		ClientResource resource = new ClientResource("http://www.google.com");
		Representation rep = resource.get();
		try {
			rep.write(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
