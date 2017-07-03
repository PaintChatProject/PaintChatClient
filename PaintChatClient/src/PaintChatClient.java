public class PaintChatClient {
    public static void main(String args[]) {
        SocketData.getInstance().connect("192.168.0.33",7777);
        UserInterface.getInstance().showUI();
        new ReceiveData().run();
    }
}