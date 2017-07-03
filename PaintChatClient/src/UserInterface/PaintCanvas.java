import java.awt.*;
import java.awt.image.ImageObserver;

public class PaintCanvas extends Canvas {
    private static PaintCanvas paintCanvas=new PaintCanvas();

    private int x = -30;
    private int y = -30;
    Color c = Color.green;
    int size = 20;

    public static PaintCanvas getInstance(){
        return paintCanvas;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(c);
        g.fillOval(x, y, size, size);
    }

    public int getSiz() {
        return size;
    }

    public Color getC() {
        return c;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setC(Color c) {
        this.c = c;
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addPaint(PaintData paintData){
        setX(paintData.getX());
        setY(paintData.getY());
        setC(paintData.getColor());
        setSize(paintData.getSize());
        repaint();
    }

    public void setBackgroundColor(Color color){
        Graphics g = getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setBackgroundImage(Image image, int x, int y, ImageObserver imageObserver){
        Graphics g = paintCanvas.getGraphics();
        g.drawImage(image, x, y, imageObserver);
    }
}