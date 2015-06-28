package tools;



import java.io.Serializable;


public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ipDest;
    private String sms;
    private String date;
    private String type; // tipo de serviço
    private String myName;
    private Object data;


    public Message(String ipDest, String sms, String date){
        this.ipDest = ipDest;
        this.sms = sms;
        this.date = date;
        this.type = "user";
    }

    public Message(String service, String myName){
        this.type = service;
        this.myName = myName;
    }

    public String getSms(){
        return this.sms;
    }

    public void setSms(String sms){
        this.sms = sms;
    }

    public String getIpDest(){
        return this.ipDest;
    }

    public void setIpDestino(String ipDest){
        this.ipDest = ipDest;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String Date){
        this.date = Date;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getMyName(){
        return this.myName;
    }

    public void setMyName(String MyName){
        this.myName = MyName;
    }

    public Object getData(){
        return this.data;
    }

    public void setData(Object Data){
        this.data = Data;
    }
}
