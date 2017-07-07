import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ReceiveData extends Thread {
    public void run(){
        try {
            if(!SocketData.getInstance().isConnected()){
                System.out.println("\u001B[1m"+"\u001B[31m" + "[Error] 서버가 연결되어있지않습니다." + "\u001B[0m");
                return;
            }

            InputStream inputStream = SocketData.getInstance().getSocket().getInputStream();
            while(true){
                ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
                Object object=objectInputStream.readObject();   //입력스트림으로 부터 데이터 수신

                // instanceof 연산자를 이용하여 데이터 구분 후 처리
                if(object instanceof PaintData){
                    PaintData paintData=(PaintData)object;

                    if (paintData.getSize() == -10) {
                        PaintCanvas.getInstance().clearBackground();
                    } else if (paintData.getSize() > 0) {
                        PaintCanvas.getInstance().addPaint(paintData);
                    }
                }
                else if(object instanceof ChatData){
                    UserInterface.getInstance().AddChat((ChatData)object);
                }
                else if(object instanceof ImageIcon){
                    PaintCanvas.getInstance().setBackgroundImage(((ImageIcon)object).getImage(),0,0,null);
                }
            }
            //inputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
