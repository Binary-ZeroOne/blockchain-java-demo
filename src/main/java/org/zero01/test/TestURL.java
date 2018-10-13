package org.zero01.test;

import java.net.MalformedURLException;
import java.net.URL;

public class TestURL {

	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("http://192.168.77.130");
		System.out.println(url.getHost() + ":" + (url.getPort() == -1 ? url.getDefaultPort() : url.getPort()));
	}

}
