package socket;

import java.net.ServerSocket;

import tools.Dns;

public class Listen {
	private static ServerSocket server;
	private static Dns dns;

	public static void main(String[] args)throws Exception {
		server = new ServerSocket(2808);
		dns = new Dns();
		
		while(true){
			System.out.println("Wait...\n\n");
			new ThreadsServer(server.accept(), dns).start();			
		}
	}

}
