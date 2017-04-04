package studio.jjm.facedetect.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;

import org.opencv.core.Core;


public class VideoFrame extends JFrame implements WindowListener{

	/**
	 * 加载静态链接库文件
	 */
	static{
//      System.loadLibrary(Core.NATIVE_LIBRARY_NAME) ;        
	    System.load(System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"opencv_java246.dll");
	}
	private static final long serialVersionUID = 1L;
	FrameCanvas frameCanvas;
	Thread thread;
	
	public VideoFrame(){
		this.setTitle("VideoCapture") ;
		this.setLayout(new BorderLayout());
		frameCanvas = new FrameCanvas();
		this.add(frameCanvas,BorderLayout.CENTER);
		this.addWindowListener(this);
		this.setVisible(true);
		this.setSize(700,600);	
		frameCanvas.init();
	}
	
	public static void main(String[] args){
		new VideoFrame();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		frameCanvas.setStop(true);
		frameCanvas = null;
		this.dispose();
		System.gc();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
