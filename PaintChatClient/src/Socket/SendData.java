import java.io.IOException;
import java.io.ObjectOutputStream;

public class SendData {
    public void sendChatData(ChatData chatData){
        ObjectOutputStream objectOutputStream=SocketData.getInstance().getObjectOutputStream(); //Object 를 출력할 스트림 생성 //출력스트림 생성

        try {
            objectOutputStream.writeObject(chatData);  // ChatData Object 전송
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPaintData(PaintData paintData){
        ObjectOutputStream objectOutputStream=SocketData.getInstance().getObjectOutputStream(); //Object 를 출력할 스트림 생성 //출력스트림 생성

        try {
            objectOutputStream.writeObject(paintData);  // ChatData Object 전송
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
