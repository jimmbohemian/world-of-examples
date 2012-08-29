package org.lombardos.example.restlet.server.hello;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloServerResource extends ServerResource{
	@Get 
	public String represent(){
		return "hello, world";
	}
}