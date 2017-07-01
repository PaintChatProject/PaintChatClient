import java.io.Serializable;

public class PaintData implements Serializable {
    private int x;
    private int y;
    private int w;

    public PaintData(int x, int y, int w){
        this.x=x;
        this.y=y;
        this.w=w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }
}