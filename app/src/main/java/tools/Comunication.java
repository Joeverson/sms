package tools;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by M2 on 04/05/2015.
 */
public class Comunication {
    private String ip;
    private Integer port;
    private Socket socket;
    private Context context;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public Comunication(String ip, Integer port, Context context){
        this.port = port;
        this.ip = ip;
        this.context = context;
    }

    public boolean conn(){
        try{
            this.socket = new Socket(this.ip, this.port);
            if(this.socket.isConnected()){
                this.in = new ObjectInputStream(socket.getInputStream());
                this.out = new ObjectOutputStream(socket.getOutputStream());
                return true;
            }

            return false;

        } catch(UnknownHostException f){
            Toast.makeText(context, "Error ao conectar com o servidor", Toast.LENGTH_SHORT).show();
            return false;
        } catch(Exception e){
            Toast.makeText(context, "Error ao conectar", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void send(Message obj) throws IOException {
        out.writeObject(obj);
    }

    public Message receved() throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }

    public String ipServer(){
        return this.socket.getInetAddress().getHostAddress();
    }

    public void sendService(String type, String userName){// envia as ~informaçoes basicas para configuração do servico
        try{
            this.conn();
            this.send(new Message(type, userName ));
            this.closeAll();
        }catch(Exception e){
            Toast.makeText(context, "Erro ao enviar o serviço", Toast.LENGTH_SHORT).show();
        }
    }

    public void closeAll(){
        try{
            this.socket.close();
            this.in.close();
            this.out.close();
        }catch(Exception e){
            Toast.makeText(this.context, "error ao fechar o socket", Toast.LENGTH_SHORT).show();
        }
    }
}
