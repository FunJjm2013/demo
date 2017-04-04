package studio.jjm.facedetect.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
/**
 *	����������
 * 	@author Jiang Junming
 * 	@version 1.0
 * 
 */

public class OperateTool {
	/**Դͼ���ļ�·��*/
	public static final String SRC_IMAGE_PATH = "C:\\src.jpg" ;
	/**Ŀ��ͼ���ļ�·��*/
	public static final String DST_IMAGE_PATH = "C:\\dst.bmp" ;
	/**����ͼ���ļ�·��*/
	public static final String AUX_IMAGE_PATH = "C:\\ext.bmp" ;

	/**
	 *	��һ��ͼ�����ת����ͼ��BufferedImage������
	 *	@param	mat		ͼ��������
	 *	 
	 * 	@return		BufferedImageͼ�����
	 * 	@see		org.opencv.core.Mat
	 * 	@see		java.awt.image.BufferedImage
	 */
	public static BufferedImage convertMat2BufferedImage(Mat mat){
		BufferedImage image = null ;
		Highgui.imwrite(AUX_IMAGE_PATH, mat) ;
		try {
			image =  ImageIO.read(new File(AUX_IMAGE_PATH)) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image ;
		
	}
	
	/**
	 *	���������������ֵ
	 *	@param	a	��һ������ 
	 *	@param	b	�ڶ�������
	 *
	 *	@return		���ֵ
	 */
	public static int maxValue(int a, int b){
		return a >= b ? a : b ;
	}
	
	/**
	 *	��������������Сֵ
	 *	@param	a	��һ������ 
	 *	@param	b	�ڶ�������
	 *
	 *	@return		��Сֵ
	 */
	public static int minValue(int a, int b){
		return a <= b ? a : b ;
	}
	
	/**
	 *	���������εĽ��� 
	 * 	
	 * 	@param	rect1	��һ������
	 * 	@param	rect2	�ڶ�������
	 * 
	 *	@return		�������ν����γɵľ��� 
	 * 	
	 * 	@see	org.opencv.Core.Rect
	 */
	public static Rect crossRect(Rect rect1, Rect rect2){
		int x1 = maxValue(rect1.x, rect2.x);
		int y1 = maxValue(rect1.y, rect2.y);
		rect1.width = minValue(rect1.x + rect1.width, rect2.x + rect2.width) - x1;
		rect1.height = minValue(rect1.y + rect1.height, rect2.y + rect2.height) - y1;
		rect1.x = x1 ;
		rect1.y = y1 ;
		if(rect1.width <= 0 || rect1.height <= 0)
			rect1 = new Rect();
		return rect1 ;
	}
	
	public static void printMatData(Mat mat){
		for(int r = 0; r < mat.rows(); r++){
			for(int c = 0; c < mat.cols(); c++){
				double[] data = mat.get(r, c) ;
				for(int i = 0; i < data.length; i++)
					System.out.print(data[i]+ " ") ;
				System.out.print("\t");
			}
			System.out.println();
		}

	}
	
	public static void main(String[] args){
		Rect rect1 = new Rect(0, 0, 10, 10) ;
		Rect rect2 = new Rect(1, 1, 10, 10) ;
		Rect rect = crossRect(rect1, rect2);
		System.out.println("rect -> x ="  + rect.x + ", y = " + rect.y  + " ,width = " + rect.width + ",height = " + rect.height);
	}
	
}
