package studio.jjm.opencv.demo;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MatDemo1 extends LoadStaticLibrary{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mat mat = new Mat(2,2,CvType.CV_16UC1);
		System.out.println(mat);
		short[] data = new short[10];
		mat.get(1, 1, data);
		for(short i : data)
		    System.out.println(i);
	}

}
