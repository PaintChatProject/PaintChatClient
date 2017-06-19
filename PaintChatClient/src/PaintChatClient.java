import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class PaintChatClient {
    public static final String SERVER_IP = "127.0.0.1"; //접속할 서버 아이피
    public static final int SERVER_PORT = 7777; //접속할 서버 포트
    public static void main(String args[]) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT); //소켓 생성 & 접속 >> 실패시 ConnectException 에외 발생
            Scanner scanner =new Scanner(System.in); //스케너 생성

            ChatData chatData=new ChatData(scanner.nextLine()); //스케너로 입력데이터 받아 chatData에  넣음

            OutputStream outputStream = socket.getOutputStream();   //출력스트림 생성
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream); //Object 를 출력할 스트림 생성
            objectOutputStream.writeObject(chatData);  // ChatData Object 전송

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

            objectInputStream.close();
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