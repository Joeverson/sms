package tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService {
    private ServerSocket socketServer;
    private Socket socket;
    private ObjectInputStream in;
    private Message sms;
    private Handler handle = new Handler();

    public ServerService(final Context context, final TextView outText){
        new Thread(){
           public void run(){
               try{
                   Looper.prepare();
                   socketServer = new ServerSocket(2809);

                   while(true){
                       try{
                           socket = socketServer.accept();
                       }catch(IOException e){
                           Toast.makeText(context, "Erro no ao ouvir", Toast.LENGTH_SHORT).show();
                       }

                       in = new ObjectInputStream(socket.getInputStream());

                       sms = (Message) in.readObject();

                      if(handle != null){
                          handle.post(new Runnable(){ // chama a thread principal
                              public void run(){
                                  outText.append("\n" + sms.getMyName() + " - " + sms.getSms());
                              }
                          });
                      }
                   }

               }catch(Exception e){
                   Toast.makeText(context, "Erro abrir o socket servidor", Toast.LENGTH_SHORT).show();
               }finally{
                   try{
                       in.close();
                       socket.close();
                   }catch(Exception e){
                       Toast.makeText(context, "Erro a fechar", Toast.LENGTH_SHORT).show();
                   }
               }
           }
        }.start();
    }
}
