package studio.jjm.facedetect.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class DisplayCanvas extends Canvas implements ComponentListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 442372432363378192L;
	private BufferedImage bi;
	private Image im;
	private int image_width;
	private int image_height;

	public void setImage(BufferedImage bi) {
		this.bi = bi;
		this.zoom();
	}

	public void paint(Graphics g) {
		g.drawImage(im, (this.getWidth() - image_width) / 2,
				(this.getHeight() - image_height) / 2, this);
	}

	public void componentResized(ComponentEvent e) {
		if (bi != null) {
			System.out.println("resize!!");

			this.zoom();
			this.repaint();
		}
	}

	
	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void zoom() {
		if (bi == null)
			return;
		int screen_width = this.getWidth();
		int screen_height = this.getHeight();
		double screen_proportion = 1.0 * screen_height / screen_width;

		image_width = bi.getWidth(this);
		image_height = bi.getHeight(this);
		double image_proportion = 1.0 * image_height / image_width;

		if (image_proportion > screen_proportion) {
			image_height = screen_height;
			image_width = (int) (image_height / image_proportion);
		} else {
			image_width = screen_width;
			image_height = (int) (image_width * image_proportion);
		}
		im = bi.getScaledInstance(image_width, image_height, Image.SCALE_SMOOTH);
	}

}