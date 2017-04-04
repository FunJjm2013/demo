package studio.jjm.facedetect.video;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;

import studio.jjm.facedetect.util.OperateTool;

public class CamshiftThread implements Runnable{
	
	public Mat get_src() {
		return _src;
	}

	public void set_src(Mat _src) {
		this._src = _src;
	}

	public Rect get_selectedArea() {
		return _selectedArea;
	}

	public void set_selectedArea(Rect _selectedArea) {
		this._selectedArea = _selectedArea;
	}

	private Mat _src = null ;		//¼ì²âÍ¼Ïñ
	private Rect _selectedArea = null;
	private RotatedRect rRect = null;

	public CamshiftThread(Mat src, Rect selectedArea){
		this._src = src ;
		this._selectedArea = selectedArea;
	}
	
	@Override
	public void run() {
		camshift(this._selectedArea);
	}

	public RotatedRect camshift(Rect trackArea){
		Mat hsv = new Mat();
		Imgproc.cvtColor(this._src, hsv, Imgproc.COLOR_RGB2HSV);
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
		
		//OperateTool.printMatData(hue);
		//System.out.println("hsv -> size = " + hsv.size() + ", depth = " + hsv.depth());	
		Mat hue = new Mat();
		hue.create(hsv.size(), hsv.depth()) ;
		//System.out.println("hue -> channels "+hue.channels());	
		//OperateTool.printMatData(hue);

		List<Mat> srclist = new ArrayList<Mat>();
		List<Mat> dstlist = new ArrayList<Mat>();
		srclist.add(hsv);
		dstlist.add(hue);
		int ch[] = {0,0} ;		
		MatOfInt fromTo = new MatOfInt(ch);	
		Core.mixChannels(srclist, dstlist, fromTo);
		//OperateTool.printMatData(hue);
	
		Mat roi = new Mat(hue, trackArea) ;
		//System.out.println("roi : ");
		//OperateTool.printMatData(roi);
		Mat maskroi = new Mat(mask, trackArea);
		//System.out.println("maskroi : ");
		//OperateTool.printMatData(maskroi);

		Mat hist = new Mat();
		List<Mat> roilist = new ArrayList<Mat>();
		roilist.add(roi);
		MatOfInt channels = new MatOfInt(0);
		int hsize = 16 ;
		MatOfInt histSize = new MatOfInt(hsize);
		float hranges[] = {0,180} ;
		MatOfFloat ranges = new MatOfFloat(hranges);		
		Imgproc.calcHist(roilist, channels, maskroi, hist, histSize, ranges) ;
		//OperateTool.printMatData(hist);

		Core.normalize(hist, hist, 0, 255, Core.NORM_MINMAX);
		//OperateTool.printMatData(hist);

		ArrayList<Mat> huelist = new ArrayList<Mat>();
		huelist.add(hue);
		int[] ii = {0} ;
		MatOfInt mm = new MatOfInt(ii);
		Mat backproj = new Mat();
		Imgproc.calcBackProject(huelist, mm, hist, backproj, ranges,1) ;
//		OperateTool.printMatData(backproj);
		
		Core.bitwise_and(backproj, mask, backproj) ;
	//	OperateTool.printMatData(backproj);	
		
		RotatedRect trackbox = Video.CamShift(backproj, trackArea, new TermCriteria(TermCriteria.EPS | TermCriteria.MAX_ITER, 10, 1));
		//System.out.println(trackbox);
		return trackbox ;
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
