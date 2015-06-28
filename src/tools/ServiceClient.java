package tools;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServiceClient {
	private Socket socket;
	private Integer port = 2809;
	private ObjectOutputStream out;
	
	public ServiceClient(String ip, Message obj){
		try{
			this.socket = new Socket(ip, port);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(obj);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			try{
				socket.close();
				out.close();
			}catch(Exception e){
				
			}
		}
	}
}
