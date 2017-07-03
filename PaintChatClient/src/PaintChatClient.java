import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class ClientSendThread extends Thread{
    Socket socket;
    static String name = "";
    static String temp = "";

    public ClientSendThread(Socket socket){
        this.socket=socket;
    }

    public void run(){

        try {
            Scanner scanner =new Scanner(System.in); //스케너 생성

            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream()); //Object 를 출력할 스트림 생성 //출력스트림 생성

            System.out.print("대화명을 입력해주세요:");
            name=scanner.nextLine();
            //notify();

            objectOutputStream.writeObject(name);//대화명 보내기기
           while(true){
                temp = scanner.nextLine();
                ChatData chatData=new ChatData("["+name+"]"+ temp); //스케너로 입력데이터 받아 chatData에  넣음

                objectOutputStream.writeObject(chatData);  // ChatData Object 전송

                if(temp.equals("/exit")) break;
            }
            objectOutputStream.close();
            socket.close(); //소켓 종료

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientReceiveThread extends Thread{
    //ClientReceiveThread temp = new ClientReceiveThread();
    Socket socket;


    public ClientReceiveThread(Socket socket){
        this.socket=socket;
    }

    public void run(){
        try {
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream()); //Object 입력 스트림 생성   //입력스트림 생성
            //temp.wait();

            while(true){
                if(ClientSendThread.temp.equals("/exit")) break;
                Object object=objectInputStream.readObject();   //입력스트림으로 부터 데이터 수신

                // instanceof 연산자를 이용하여 데이터 구분 후 처리
                if(object instanceof PaintData){
                    PaintData paintData=(PaintData)object;
                    System.out.println(paintData.getX()+" "+paintData.getY());
                }
                else if(object instanceof ChatData){
                    ChatData textData=(ChatData)object;
                    System.out.println(textData.getMessage());
                }
                if(object==null) break;
            }
            objectInputStream.close();
            socket.close(); //소켓 종료

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class PaintChatClient {
    public static final String SERVER_IP = "127.0.0.1"; //접속할 서버 아이피
    public static final int SERVER_PORT = 7777; //접속할 서버 포트

    public static void main(String args[]) {
        UserInterface.getInstance().showUI();
        /*try{
            Socket socket=new Socket(SERVER_IP,SERVER_PORT);
            System.out.println("서버접속성공!");

            //서버에서 보내온 데이터를 수신하는 스레드 실행
            new ClientReceiveThread(socket).start();

            //서버에 데이터를 보내는 스레드 실행
            new ClientSendThread(socket).start();



        }catch(Exception e){
            e.printStackTrace();
        }*/

    }

}