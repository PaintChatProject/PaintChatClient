import java.awt.*;

public class PaintCanvas extends Canvas {
    private int x = -30;
    private int y = -30;
    Color c = Color.green;
    int size = 20;

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
}