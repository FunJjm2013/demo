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

/**
 *	CamshiftÀ„∑® 
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class MyCamshift {
	
	
	private ArrayList<Rect> selectedAreaList = null ;	//—°‘Ò«¯”Ú
	private Mat _src = null ;		//ºÏ≤‚ÕºœÒ
	
	public MyCamshift(Mat src, Rect[] selectedArea){
		this._src = src ;
		this.selectedAreaList = new ArrayList<Rect>();
		for(int i = 0; i < selectedArea.length; i++)
		{
			this.selectedAreaList.add(selectedArea[i]);
		}
	}
	
	public ArrayList<RotatedRect> startCamshift(){
		ArrayList<RotatedRect> rRotatedRectList = new ArrayList<RotatedRect>(selectedAreaList.size());
		for(int i = 0; i < selectedAreaList.size(); i++)
		{
			RotatedRect rRect = camshift(selectedAreaList.get(i));
			if(rRect != null)
				rRotatedRectList.add(rRect);
		}
		return rRotatedRectList;
	}
	
	public RotatedRect camshift(Rect trackArea){
		Mat hsv = new Mat();
		Imgproc.cvtColor(this._src, hsv, Imgproc.COLOR_RGB2HSV);

		int vmin = 10, vmax = 256, smin = 30 ;
		int _vmin = vmin, _vmax = vmax ;
		Scalar scalar1 = new Scalar(0, smin, OperateTool.minValue(_vmin, _vmax));
		Scalar scalar2 =  new Scalar(180, 256, OperateTool.maxValue(_vmin, _vmax));

		Mat mask = new Mat();
		Core.inRange(hsv, scalar1, scalar2, mask) ;

		Mat hue = new Mat();
		hue.create(hsv.size(), hsv.depth()) ;

		List<Mat> srclist = new ArrayList<Mat>();
		List<Mat> dstlist = new ArrayList<Mat>();
		srclist.add(hsv);
		dstlist.add(hue);
		int ch[] = {0,0} ;		
		MatOfInt fromTo = new MatOfInt(ch);	
		Core.mixChannels(srclist, dstlist, fromTo);
	
		Mat roi = new Mat(hue, trackArea) ;

		Mat maskroi = new Mat(mask, trackArea);

		Mat hist = new Mat();
		List<Mat> roilist = new ArrayList<Mat>();
		roilist.add(roi);
		MatOfInt channels = new MatOfInt(0);
		int hsize = 16 ;
		MatOfInt histSize = new MatOfInt(hsize);
		float hranges[] = {0,180} ;
		MatOfFloat ranges = new MatOfFloat(hranges);		
		Imgproc.calcHist(roilist, channels, maskroi, hist, histSize, ranges) ;

		Core.normalize(hist, hist, 0, 255, Core.NORM_MINMAX);

		ArrayList<Mat> huelist = new ArrayList<Mat>();
		huelist.add(hue);
		int[] ii = {0} ;
		MatOfInt mm = new MatOfInt(ii);
		Mat backproj = new Mat();
		Imgproc.calcBackProject(huelist, mm, hist, backproj, ranges,1) ;
		
		Core.bitwise_and(backproj, mask, backproj) ;
		
		RotatedRect trackbox = Video.CamShift(backproj, trackArea, new TermCriteria(TermCriteria.EPS | TermCriteria.MAX_ITER, 10, 1));
		return trackbox ;
		
	}
	public ArrayList<Rect> getSelectedAreaList() {
		return selectedAreaList;
	}

	public void setSelectedAreaList(ArrayList<Rect> selectedAreaList) {
		this.selectedAreaList = selectedAreaList;
	}
	
	public Mat getSrc() {
		return _src;
	}

	public void setSrc(Mat _src) {
		this._src = _src;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
