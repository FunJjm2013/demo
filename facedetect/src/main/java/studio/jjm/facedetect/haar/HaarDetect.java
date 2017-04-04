package studio.jjm.facedetect.haar;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
/**
 *	Haar特征检测方法
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class HaarDetect {

	private CascadeClassifier classifier= null ;
	public HaarDetect(String cascadeXmlFilePath){
		classifier = new CascadeClassifier(cascadeXmlFilePath);
	}
	/**
     *  Haar检测方法
     * 	@param	image	检测图像
     * 	@param	scaleFactor	范围参数
     * 	@param	minNeighbors		最小邻接数目
     * 	@param	flag 	标志
     * 	@param 	minSize	检测的最小人脸区域大小
     *	@param	maxSize	检测的最大人脸区域大小
     *	
     *	@return 检测的人脸
     */

	public ArrayList<Rectangle> haarDetecting(Mat image, double scaleFactor, int minNeighbors, int flag, Size minSize, Size maxSize){
		MatOfRect objectRects = new MatOfRect();		
		MatOfInt rejectLevels = new MatOfInt();
		MatOfDouble levelWeights = new MatOfDouble();
		if(!classifier.empty())
			classifier.detectMultiScale(image, objectRects, rejectLevels, levelWeights, scaleFactor, minNeighbors, Objdetect.CASCADE_DO_CANNY_PRUNING, new Size(5,5), new Size(), false);
		else
			System.out.println("Not Load cascader");
		Rect[] rect = objectRects.toArray();
		ArrayList<Rectangle> rectangles = null ;
        if(rect.length > 0){
            //返回的人脸总数
        	rectangles = new ArrayList<Rectangle>(rect.length); 
        	for(int i = 0; i < rect.length; i++)
        		rectangles.add(new Rectangle(rect[i].x, rect[i].y, rect[i].width, rect[i].height)) ;
        }
        return rectangles ;

	}
	/**
     *  Haar检测方法
     * 	@param	image	检测图像
     * 	@param	scaleFactor	范围参数
     * 	@param	minNeighbors		最小邻接数目
     * 	@param	flag 	标志
     * 	@param 	minSize	检测的最小人脸区域大小
     *	@param	maxSize	检测的最大人脸区域大小
     *	
     *	@return 检测的人脸
     */
	
	public Rect[] haarDetected(Mat image, double scaleFactor, int minNeighbors, int flag, Size minSize, Size maxSize){
		MatOfRect objectRects = new MatOfRect();		
		MatOfInt rejectLevels = new MatOfInt();
		MatOfDouble levelWeights = new MatOfDouble();
		if(!classifier.empty())
			classifier.detectMultiScale(image, objectRects, rejectLevels, levelWeights, scaleFactor, minNeighbors, Objdetect.CASCADE_DO_CANNY_PRUNING, new Size(5,5), new Size(), false);
		else
			System.out.println("Not Load cascader");
		Rect[] rect = objectRects.toArray();
        return rect ;

	}

}
