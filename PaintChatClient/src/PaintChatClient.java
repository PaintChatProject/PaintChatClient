public class PaintChatClient {
    public static void main(String args[]) {
        SocketData.getInstance().connect();
        UserInterface.getInstance().showUI();
        new ReceiveData().run();
    }
}