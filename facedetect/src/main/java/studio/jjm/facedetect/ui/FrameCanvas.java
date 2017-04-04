package studio.jjm.facedetect.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.Objdetect;

import studio.jjm.facedetect.haar.HaarDetect;
import studio.jjm.facedetect.video.MyCamshift;

public class FrameCanvas extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String IMAGE_PATH = "D:\\image.jpg"; //临时存储图像帧的默认路径
	private VideoCapture capture = null;
	private BufferedImage frame = null;
	private volatile boolean stop = false;		//视频终止标志
	
	public FrameCanvas() {}

	public FrameCanvas(BufferedImage image) {
		if (image != null)
			frame = image;
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void init() {
		capture = new VideoCapture();
		capture.open(1);
		if (capture.isOpened()) {
			Mat mat = new Mat();
			HaarDetect detector = new HaarDetect(new File(this.getClass().getClassLoader().getResource("").getFile()+"haarcascade_frontalface_alt2.xml").getAbsolutePath());
//			HaarDetect detector = new HaarDetect(
//					"haarcascade_frontalface_alt2.xml");
			MyCamshift camshift = null;
			if (capture.grab())
				capture.read(mat);

			Rect[] rects = null;
			rects = detector.haarDetected(mat, 1.2, 2,
					Objdetect.CASCADE_DO_CANNY_PRUNING,
					new Size(10, 10), new Size());

			if(rects.length > 0){
				camshift = new MyCamshift(mat, rects);				
			}
			int sign = 0;
			do {
				if(sign == 5){
					rects = detector.haarDetected(mat, 1.2, 2,
							Objdetect.CASCADE_DO_CANNY_PRUNING,
							new Size(10, 10), new Size());	
					if(rects.length > 0){
						camshift = new MyCamshift(mat, rects);										
					}/*else{
						camshift = null;
					}*/
					sign = 0;
				}
				if(camshift != null){
					ArrayList<RotatedRect> rRectList = camshift
							.startCamshift();
					Highgui.imwrite(IMAGE_PATH, mat);
					try {
						frame = ImageIO.read(new File(IMAGE_PATH));
						if (rRectList != null)
							frame = drawRotatedRects(frame,
									rRectList);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}else{
					Highgui.imwrite(IMAGE_PATH, mat);
					try {
						frame = ImageIO.read(new File(IMAGE_PATH));
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}

				this.repaint();
				capture.read(mat);
				if (camshift != null)
					camshift.setSrc(mat);
				sign++;
			} while (capture.grab() && !stop);
		}
		capture.release();
		
	}	
	
	@Override
	public void update(Graphics g){
		if (frame != null)
			g.drawImage((Image) frame,
					(this.getWidth() - frame.getWidth()) / 2,
					(this.getHeight() - frame.getHeight()) / 2, this);
		g.dispose();
	}
	
	/**
	 *	在图像中绘制旋转矩形的方法 
	 *  @param	image	要绘制的图像
	 *  @param	rRectList	需要在图像中绘制出的旋转矩形
	 *  
	 *  @return 绘制好旋转矩形的图像
	 */
	public BufferedImage drawRotatedRects(BufferedImage image, ArrayList<RotatedRect> rRectList)
	{
		Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.red);
		for(int i = 0; i < rRectList.size(); i++)
		{
			Point[] points = new Point[4] ;
			rRectList.get(i).points(points) ;
			int[] xPoints = new int[4] ;
			int[] yPoints = new int[4] ;
			for(int j = 0; j < points.length; j++)
			{
				xPoints[j] = (int) points[j].x ;
				yPoints[j] = (int) points[j].y ;
			}
	        g2.drawPolygon(xPoints, yPoints, points.length);
		}
		return image ;
	}

	/**
	 *	在图像中绘制矩形的方法 
	 *  @param	image	要绘制的图像
	 *  @param	rectList	需要在图像中绘制出的矩形
	 *  
	 *  @return 绘制好旋转矩形的图像
	 */
	public BufferedImage drawRects(BufferedImage image, Rect[] rectList)
	{
		Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.red);
		for(int i = 0; i < rectList.length; i++)
		{
			g2.drawRect(rectList[i].x, rectList[i].y, rectList[i].width, rectList[i].height);
		}
		return image ;
	}

	
	public BufferedImage getFrame() {
		return frame;
	}

	public void setFrame(BufferedImage frame) {
		this.frame = frame;
	}

	public boolean getStop() {
		return this.stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}



}
