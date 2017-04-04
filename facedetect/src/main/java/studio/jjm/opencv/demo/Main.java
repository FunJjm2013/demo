package studio.jjm.opencv.demo;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
 /*       Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("m = " + m.dump());
*/        Mat mat = new Mat();
        VideoCapture capture = new VideoCapture();
        capture.open(1); 
        int i = 0;
        if(capture.isOpened()){
        	
        	for(i = 0; i < 10; i++){
		        if(capture.grab()){
		        	capture.read(mat);
		        if(!mat.empty())
		        Highgui.imwrite("F:\\"+"image"+i+".jpeg",mat);
		        }
		        else System.out.println("mat is null");
        	}
        }
    }
}
