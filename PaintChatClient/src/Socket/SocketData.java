import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketData {
    private static final String DEFAULT_SERVER_IP = "127.0.0.1"; //접속할 서버 아이피
    private static final int DEFAULT_SERVER_PORT = 7777; //접속할 서버 포트

    private static SocketData socketData=new SocketData();

    private String SERVER_IP;
    private int SERVER_PORT;

    private Socket socket=null;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public static SocketData getInstance(){
        return socketData;
    }

    public void connect(){
        try{
            socket=new Socket(DEFAULT_SERVER_IP,DEFAULT_SERVER_PORT);
            SERVER_IP=DEFAULT_SERVER_IP;
            SERVER_PORT=DEFAULT_SERVER_PORT;

            objectInputStream=new ObjectInputStream(socket.getInputStream());
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void connect(String ip, int port){
        try{
            socket=new Socket(ip,port);
            SERVER_IP=ip;
            SERVER_PORT=port;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void reConnect(){
        if(!socket.isConnected()) {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected(){
        if(socket==null) return false;
        return socket.isConnected();
    }

    public Socket getSocket(){
        return socket;
    }

    public ObjectInputStream getObjectInputStream(){
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream(){
        return objectOutputStream;
    }
}
