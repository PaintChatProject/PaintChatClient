import java.io.IOException;
import java.io.ObjectOutputStream;

public class SendData {
    private static SendData sendData=new SendData();

    public static SendData getInstance(){
        return sendData;
    }

    public void sendChatData(ChatData chatData){
        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(SocketData.getInstance().getSocket().getOutputStream()); //Object 를 출력할 스트림 생성 //출력스트림 생성
            objectOutputStream.writeObject(chatData);  // ChatData Object 전송
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPaintData(PaintData paintData){
        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(SocketData.getInstance().getSocket().getOutputStream()); //Object 를 출력할 스트림 생성 //출력스트림 생성
            objectOutputStream.writeObject(paintData);  // ChatData Object 전송
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}