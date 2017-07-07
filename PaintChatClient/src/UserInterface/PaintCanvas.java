import java.awt.*;
import java.awt.image.ImageObserver;

public class PaintCanvas extends Canvas {
    private static PaintCanvas paintCanvas=new PaintCanvas();

    public static PaintCanvas getInstance(){
        return paintCanvas;
    }

    public void addPaint(PaintData paintData){
        getGraphics().fillOval(paintData.getX(),paintData.getY(),paintData.getSize(),paintData.getSize());
    }

    public void setBackgroundColor(Color color){
        getGraphics().fillRect(0, 0, getWidth(), getHeight());
    }

    public void setBackgroundImage(Image image, int x, int y, ImageObserver imageObserver){
        getGraphics().drawImage(image, x, y, imageObserver);
    }

    public void clearBackground(){
        getGraphics().clearRect(0, 0, paintCanvas.getWidth(), paintCanvas.getHeight());
    }
}