package socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import tools.Dns;
import tools.Message;
import tools.ServiceClient;

public class ThreadsServer extends Thread{
	private Socket socket = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Message sms = null;
	private Dns dns;
	
	
	public ThreadsServer(Socket socket, Dns dns){
		this.socket = socket;
		this.dns = dns;
	}
	
	public void run(){
		System.out.println("Ouvindo...");
		
		System.out.println("Conectado ao cliente " + socket.getInetAddress().getHostAddress());

		try{			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			sms = (Message) in.readObject();
			
			if(sms.getType().equals("saveMe")){
				
				dns.newUser(socket.getInetAddress().getHostAddress(), sms.getMyName());
				
				for(String str : dns.allNames()){
					System.out.println("\n<--> "+str+"\n");
				}
				
				System.out.println("\n ->> send infos about device - "+dns.getIp(socket.getInetAddress().getHostAddress()));
				
			}else if(sms.getType().equals("bye")){	
				
				System.out.println("\n ->> Delete the user - "+dns.getIp(socket.getInetAddress().getHostAddress()));
				dns.del(socket.getInetAddress().getHostAddress());				
				
			}else if(sms.getType().equals("spinner")){			
				Message m = new Message("request", null);
				m.setData(dns.allNames());
				
				out.writeObject(m);
				System.out.println("\n ->> spinner send for user - "+dns.getIp(socket.getInetAddress().getHostAddress()));
				
			}else{
				
				new ServiceClient("192.168.0.15",new Message("192.168.0.1", "eita ta enviando", null));
				
				System.out.println("\n--> " + sms.getSms() +"\n");
			}
				
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			try{
				out.close();
				in.close();
				socket.close();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		
		System.out.println(".......fim......");
	}	
}
