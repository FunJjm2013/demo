package studio.jjm.facedetect.ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.Objdetect;

import studio.jjm.facedetect.haar.HaarDetect;
import studio.jjm.facedetect.util.ImageFileFilter;
import studio.jjm.facedetect.util.ImageTool;

public class FaceDetectorClient implements ActionListener {
	static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME) ;        
	}   

	public static void main(String[] args) {
		new FaceDetectorClient().init();
	}

	private Frame frame;
	private DisplayCanvas mc;
	private String fpath;
	private String fname;
	private File[] files;
	private int findex;
	private FileDialog fd_load;
	private Button previous;
	private Button next;
	private String[] myButtonStrings = { "高清人脸"};
	private Button[] myButtons;
	private ImageFileFilter fileFilter ;
	private BufferedImage curImage;
	private BufferedImage oriImage;
	private HaarDetect detector ;

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("选择图片")) {
			fd_load.setVisible(true);
			fpath = fd_load.getDirectory();
			fname = fd_load.getFile();
			if ((fpath != null) && (fname != null)) {
				this.display(new File(fpath + fname));
				files = new File(fpath).listFiles(fileFilter);
				this.setIndex();
			}
		} else if (command.equals("上一张")) {
			findex--;
			if (findex < 0)
				findex = 0;
			this.display(files[findex]);
		} else if (command.equals("下一张")) {
			findex++;
			if (findex >= files.length)
				findex = files.length - 1;
			this.display(files[findex]);
		} else {
			if (command.equals("原图")) {
				curImage = oriImage;
			} else {
			    ArrayList<Rectangle> rectangles = null;
			    Mat image = Highgui.imread(files[findex].getAbsolutePath());
			    rectangles = detector.haarDetecting(image, 1.2, 2, Objdetect.CASCADE_DO_CANNY_PRUNING, new Size(10, 10), new Size());
			    ImageTool.drawFace(rectangles, curImage) ;
			}
			this.display(curImage);
		}

		this.validateButton();
	}

	public void display(BufferedImage img) {
		mc.setImage(img);
		mc.repaint();
	}

	public void display(File f) {
		// waveletImage = null;
		try {
			oriImage = ImageIO.read(f);
			curImage = oriImage;
			mc.setImage(curImage);
			frame.setTitle("PictureViewer - [" + f.getName() + "]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		mc.repaint();
	}

	public void init() {
	    
		frame = new Frame("人脸检测系统");
		
		detector = new HaarDetect("haarcascade_frontalface_alt2.xml");
		
		Panel pb = new Panel();
		Button select = new Button("选择图片");
		previous = new Button("上一张");
		next = new Button("下一张");
		//ori = new Button("原图");
		select.addActionListener(this);
		previous.addActionListener(this);
		next.addActionListener(this);
		//ori.addActionListener(this);

		pb.add(select);
		pb.add(previous);
		pb.add(next);

		myButtons = new Button[myButtonStrings.length];
		for (int i = 0; i < myButtons.length; i++) {
			myButtons[i] = new Button(myButtonStrings[i]);
			myButtons[i].addActionListener(this);
			pb.add(myButtons[i]);
		}

		mc = new DisplayCanvas();
		mc.setBackground(new Color(200, 210, 230));
		mc.addComponentListener(mc);
		frame.add(pb, "North");
		frame.add(mc, "Center");
		frame.setSize(1000, 600);
		frame.setLocation(400, 200);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
		this.validateButton();
		ImageFileFilter filter= new ImageFileFilter();
		fd_load = new FileDialog(frame, "打开文件", FileDialog.LOAD);
		fd_load.setFilenameFilter(filter);
	}

	public void setIndex() {
		File current = new File(fpath + fname);
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (current.equals(files[i])) {
					findex = i;
				}
			}
		}
	}

	public void validateButton() {
		previous.setEnabled((files != null) && (findex > 0));
		next.setEnabled((files != null) && (findex < (files.length - 1)));
	}

}
