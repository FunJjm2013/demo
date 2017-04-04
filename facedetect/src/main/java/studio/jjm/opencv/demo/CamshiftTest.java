package studio.jjm.opencv.demo;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;

import studio.jjm.facedetect.util.OperateTool;

public class CamshiftTest {

	static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME) ;        
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		camshift();
	}
	
	public static void camshift(){
		Mat src = Highgui.imread(OperateTool.SRC_IMAGE_PATH);
		Mat hsv = new Mat();
		Imgproc.cvtColor(src, hsv, Imgproc.COLOR_RGB2HSV);
		//OperateTool.printMatData(hsv);
		
		int vmin = 10, vmax = 256, smin = 30 ;
		int _vmin = vmin, _vmax = vmax ;
		Scalar scalar1 = new Scalar(0, smin, OperateTool.minValue(_vmin, _vmax));
		Scalar scalar2 =  new Scalar(180, 256, OperateTool.maxValue(_vmin, _vmax));
		//System.out.println("scalar1 = " + scalar1);
		//System.out.println("scalar2 = " + scalar2);
		Mat mask = new Mat();
		Core.inRange(hsv, scalar1, scalar2, mask) ;
		//System.out.println(mask.type());
		//OperateTool.printMatData(mask);
		
		Mat hue = new Mat();
		//OperateTool.printMatData(hue);
		//System.out.println("hsv -> size = " + hsv.size() + ", depth = " + hsv.depth());	
		hue.create(hsv.size(), hsv.depth()) ;
		//System.out.println("hue -> channels "+hue.channels());	
		//OperateTool.printMatData(hue);

		int ch[] = {0,0} ;		
		List<Mat> srclist = new ArrayList<Mat>();
		List<Mat> dstlist = new ArrayList<Mat>();
		srclist.add(hsv);
		dstlist.add(hue);
		MatOfInt fromTo = new MatOfInt(ch);	
		Core.mixChannels(srclist, dstlist, fromTo);
		//OperateTool.printMatData(hue);
	
		Rect selection = new Rect(50, 50, 100, 100);
		Mat roi = new Mat(hue, selection) ;
		//System.out.println("roi : ");
		//OperateTool.printMatData(roi);
		Mat maskroi = new Mat(mask, selection);
		//System.out.println("maskroi : ");
		//OperateTool.printMatData(maskroi);

		Mat hist = new Mat();
		int hsize = 16 ;
		float hranges[] = {0,180} ;
		List<Mat> roilist = new ArrayList<Mat>();
		roilist.add(roi);
		MatOfInt channels = new MatOfInt(0);
		MatOfInt histSize = new MatOfInt(hsize);
		MatOfFloat ranges = new MatOfFloat(hranges);		
		Imgproc.calcHist(roilist, channels, maskroi, hist, histSize, ranges) ;
		//OperateTool.printMatData(hist);

		Core.normalize(hist, hist, 0, 255, Core.NORM_MINMAX);
		//OperateTool.printMatData(hist);

		//System.out.println(CvType.CV_8UC3);
		Mat histimg = Mat.zeros(new Size(20, 32), CvType.CV_8UC3);
		//OperateTool.printMatData(histimg);
		//System.out.println(histimg);

		Mat backproj = new Mat();
		ArrayList<Mat> huelist = new ArrayList<Mat>();
		huelist.add(hue);
		int[] ii = {0} ;
		MatOfInt mm = new MatOfInt(ii);
		Imgproc.calcBackProject(huelist, mm, hist, backproj, ranges,1) ;
//		OperateTool.printMatData(backproj);
		
		Core.bitwise_and(backproj, mask, backproj) ;
	//	OperateTool.printMatData(backproj);	
		
		Rect trackWindow = selection ;
		//System.out.println("trackWindow : " + trackWindow);
		RotatedRect trackbox = Video.CamShift(backproj, trackWindow, new TermCriteria(TermCriteria.EPS | TermCriteria.MAX_ITER, 10, 1));
		System.out.println(trackbox);
		
/*		if(trackWindow.area() <= 1)
		{
			int cols = backproj.cols();
			int rows = backproj.rows();
			int r = (OperateTool.minValue(cols, rows) + 5) / 6 ;
			Rect rect1 = new Rect(trackWindow.x - r, trackWindow.y - r, trackWindow.x + r, trackWindow.y + r);
			Rect rect2 = new Rect(0, 0, cols, rows);
			trackWindow = OperateTool.crossRect(rect1, rect2) ;
			System.out.println("kdsls");
		}
*/
		
	}
}
