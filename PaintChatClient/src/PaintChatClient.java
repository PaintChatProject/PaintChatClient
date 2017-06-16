import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.Socket;

public class PaintChatClient {
    public static final String SERVER_IP = "127.0.0.1"; //접속할 서버 아이피
    public static final int SERVER_PORT = 7777; //접속할 서버 포트
    public static void main(String args[]) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT); //소켓 생성 & 접속 >> 실패시 ConnectException 에외 발생
            InputStream inputStream = socket.getInputStream();  //입력스트림 생성
            ObjectInputStream objectInputStream=new ObjectInputStream(inputStream); //Object 입력 스트림 생성
            Object object=objectInputStream.readObject();   //입력스트림으로 부터 데이터 수신
            // instanceof 연산자를 이용하여 데이터 구분 후 처리
            if(object instanceof PaintData){
                PaintData paintData=(PaintData)object;
                System.out.println(paintData.x+" "+paintData.y+" "+paintData.w);
            }
            else if(object instanceof ChatData){
                ChatData textData=(ChatData)object;
                System.out.println(textData.text);
            }
            objectInputStream.close();  //스트림 종료
            socket.close(); //소켓 종료
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}