import java.io.ObjectInputStream;

public class ReceiveData extends Thread {
    public void run(){
        try {
            if(!SocketData.getInstance().isConnected()){
                System.out.println("\u001B[1m"+"\u001B[31m" + "[Error] 서버가 연결되어있지않습니다." + "\u001B[0m");
                return;
            }
            ObjectInputStream objectInputStream=new ObjectInputStream(SocketData.getInstance().getSocket().getInputStream());

            while(true){
                Object object=objectInputStream.readObject();   //입력스트림으로 부터 데이터 수신

                // instanceof 연산자를 이용하여 데이터 구분 후 처리
                if(object instanceof PaintData){
                    PaintData paintData=(PaintData)object;
                    UpdatePaint(paintData);
                }
                else if(object instanceof ChatData){
                    ChatData chatData=(ChatData)object;
                    UpdateChat(chatData);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UpdatePaint(PaintData paintData){
        PaintCanvas.getInstance().addPaint(paintData);
    }

    private void UpdateChat(ChatData chatData){
        UserInterface.getInstance().AddChat(chatData);
    }
}
