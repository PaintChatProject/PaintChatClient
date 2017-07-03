import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PaintChatClient {
    public static final String SERVER_IP = "127.0.0.1"; //접속할 서버 아이피
    public static final int SERVER_PORT = 7777; //접속할 서버 포트

    public static void main(String args[]) {
        SocketData.getInstance().connect();
        UserInterface.getInstance().showUI();
        new ReceiveData().run();
    }
}