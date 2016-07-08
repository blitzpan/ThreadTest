package com.httpServerTest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.threadpooltest.DefaultThreadPool;
import com.threadpooltest.ThreadPool;

public class SimpleHttpServer {
	//处理HttpRequest的线程池
	static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(1);
	static String basePath;
	static ServerSocket serverSocket;
	//服务监听端口
	static int port = 8181;
	
	public static void setPort(int port){
		if(port>0){
			SimpleHttpServer.port = port;
		}
	}
	public static void setBasePath(String basePath){
		if(basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()){
			SimpleHttpServer.basePath = basePath;
		}
	}
	//启动SimpleHttpServer
	public static void start() throws Exception{
		serverSocket = new ServerSocket(port);
		Socket socket = null;
		while((socket=serverSocket.accept()) != null){
			//接收一个客户端Socket，生成一个HttpRequestHandler，放入线程池中执行
			threadPool.execute(new HttpRequestHandler(socket));
		}
		serverSocket.close();
	}
	
	static class HttpRequestHandler implements Runnable{
		private Socket socket;
		public HttpRequestHandler(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			String line = null;
			BufferedReader br = null;
			BufferedReader reader = null;//读socket的输入
			PrintWriter out = null;//读socket的输出
			InputStream in = null;//读图片文件
			try{
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String header = reader.readLine();
				System.out.println("收到header=" + header);
				//由相对路径计算出绝对路径
				String filePath = basePath + header.split(" ")[1];
				out = new PrintWriter(socket.getOutputStream());
				if(! new File(filePath).exists()){
					out.flush();
					return;
				}
				//如果有请求资源的后缀是.jpg或者.ico，则读取资源并输出
				if(filePath.endsWith(".jpg")){
					in = new FileInputStream(filePath);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i = 0;
					while((i = in.read()) != -1){
						baos.write(i);
					}
					byte[] array = baos.toByteArray();
					out.println("HTTP/1.1 200 OK");
					out.println("Server: Molly");
					out.println("Content-Type:image/jpeg");
					out.println("Content-Length:" + array.length);
					out.println("");
					socket.getOutputStream().write(array, 0, array.length);
				}else{
					br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
					out.println("HTTP/1.1 200 OK");
					out.println("Server: Molly");
					out.println("Content-Type:text/html;charset=UTF-8");
					out.println("");
					while((line = br.readLine()) != null){
						out.println(line);
					}
				}
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
				out.println("HTTP/1.1 500");
				out.println("");
				out.flush();
			}finally{
				close(br, in, reader, out, socket);
			}
		}
		//关闭流或者socket
		private static void close(Closeable... closeables){
			if(closeables != null){
				for(Closeable closeable : closeables){
					try {
						if(closeable!=null){
							closeable.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}			
			}
		}
	}
}