package studio.jjm.opencv.demo;

import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;

public class PointDemo {

    public static void main(String[] args) {
        Point point = new Point(2, 2) ;
        System.out.println("point.dot(p) :" + point.dot(new Point(3, 3)));
        
        RotatedRect rotatedRect = new RotatedRect(new Point(0, 0), new Size(4, 2), 30.0) ;
        Point[] points = new Point[4] ;
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point() ;
        }
        rotatedRect.points(points) ;
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i]);
        }
    }
}
