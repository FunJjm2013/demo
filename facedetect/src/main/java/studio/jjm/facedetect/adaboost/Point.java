package studio.jjm.facedetect.adaboost;
/**
 *	µ„¿‡
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class Point {

    protected int x ;
    protected int y ;
    public Point() {
        // TODO Auto-generated constructor stub
        this.x = 0 ;
        this.y = 0 ;
    }
    public Point(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    @Override
    public String toString() {
        return "Point [x=" + this.x + ", y=" + this.y + "]";
    }
    
    
}
