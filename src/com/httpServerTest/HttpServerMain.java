package com.httpServerTest;

public class HttpServerMain {

	public static void main(String[] args) {
		SimpleHttpServer shs = new SimpleHttpServer();
		shs.setBasePath("D:/testDir");
		try {
			shs.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}