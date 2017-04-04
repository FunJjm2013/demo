package studio.jjm.facedetect.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
/**
 *	
 * 	@author Jiang Junming
 * 
 * 	@version 1.0
 */

public final class ImageTool {
	
	
	 /**
     *  ���ͼ����������λ��
     *	@param	rectangles	��������
     *	@param	src	����ͼ��
     *
     *	@return	��������������ͼ��
     */

    public static BufferedImage drawFace(ArrayList<Rectangle> rectangles, BufferedImage image){
        if (rectangles == null || image == null) {
            return null;
        }
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.red);
        for (int i = 0; i < rectangles.size(); i++) {
            g2.drawRect(rectangles.get(i).x, rectangles.get(i).y, rectangles.get(i).width, rectangles.get(i).height) ;
        }
        
        return image ;
    }
    
    /**
     * 	ͼ���һ��
     *	@param	grays �Ҷ�ͼ
     */

    public static void imageNormalization(int[][] grays){
        int minValue = Integer.MAX_VALUE ;
        int maxValue = Integer.MIN_VALUE ;
        for (int i = 0; i < grays.length; i++) {
            for (int j = 0; j < grays[0].length; j++) {
                if (grays[i][j] < minValue) {
                    minValue = grays[i][j] ;
                }else if (maxValue < grays[i][j]){
                    maxValue = grays[i][j] ;
                }
            }
        }
        for (int i = 0; i < grays.length; i++) {
            for (int j = 0; j < grays[0].length; j++) {
                grays[i][j] = (grays[i][j] - minValue) / (maxValue - minValue) ;
            }        }      
    }
    
    /**
     *  ��Ҷ�ֵ
     *	@param	image	ͼ��
     *
     *	@return	�Ҷ�ͼ
     *
     */

    public static int[][] getGrayValues(BufferedImage image){
        if (image != null) {
            int width = image.getWidth() ;
            int height = image.getHeight() ;
            int[][] grays = new int[height][width] ;
            int rgb, r, g, b, value;
          //  System.out.println("Width = " + width + "\tHeight = " + height);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    rgb = image.getRGB(j,i) ;
                    r = (rgb >> 16) & 0xff;
                    g = (rgb >> 8) & 0xff;
                    b = rgb & 0xff;
                    value = (77 * r + 151 * g + 28 * b) >> 8;           
                    grays[i][j] = value ;
                  //  grays[i][j] = value << 16 | value << 8 | value;        //����Ҷ�ֵ
                }
            }
            return grays ;
        }else {
            throw new NullPointerException("����Ҷ�ֵʱ��ͼ�����Ϊ�գ�") ;
        }
    }
    
    /**
     *  ���ļ�·���ж�ȡͼ��
     *	@param	url		�ļ�·��
     *
     *	@return	����ȡ��ͼ��
     */

		public static BufferedImage getSourceImage(String url) {
		/*
		 * Method 01 
		 * 		Image image = Toolkit.getDefaultToolkit().createImage(url);
				return image ;
		*/	
				
		/* Method 02		
		   		Image image = Toolkit.getDefaultToolkit().getImage(url);
				return image ;
		*/
		/*
			Method 03	
		*/	
				File file = new File(url);
				BufferedImage image = null ;
				try {
					image = ImageIO.read(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return image ;
			}
		  /**
	     *  ���ļ��ж�ȡͼ��
	     *	@param	url		�ļ�
	     *
	     *	@return	����ȡ��ͼ��
	     */

		public static BufferedImage getSourceImage(File file){
		    BufferedImage image = null ;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return image ;
		}
			/**
			 * ���ָ��ͼƬ���������ص�RGBֵ
			 * 
			 * @param url   ԴͼƬ��·��
			 * 
			 */

		public static void getPixels(String url,int pixels[]) {
			File file = new File(url);
			BufferedImage image = null ;
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("==========\nͼƬ��RGBֵ��");
			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					pixels = getPixelRGBValue(image, i, j);
/*					System.out.print("[");
					for (int k = 0; k < rgb.length; k++) {
						if (k<2) {
							System.out.print(rgb[k]+",");					
						}else {
							System.out.print(rgb[k]);
						}
					}
					System.out.print("]\t");
*/				}
				System.out.println();
			}
		}
		
		  /**
	     *  ��ȡRGBͼ��
	     *	@return	ͼ��
	     */

		public static void RBGS(BufferedImage image) {
			System.out.println("++++++++++++++++++++\n���ͼƬĬ�ϵ�RGBֵ��");
			int count = 0 ;
			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
				//	System.out.print(image.getRGB(i, j)+" , ");
					count++;
				}
			//	System.out.println();
			}
			System.out.println("count->"+count);
		}
		/**
		 * ���ͼƬ��ĳһ�ض������ص�RGBֵ
		 * 
		 * @param image  ԴͼƬ��BuffferedImage����
		 * @param x		BufferedImage �з���getRGB(int x,int y)Ҫ���õ����ص� X ����
		 * @param y		BufferedImage �з���getRGB(int x,int y)Ҫ���õ����ص� Y ���� 
		 * 
		 * @return int[] һ�����ص�Ԫ��RGBֵ
		 */
		public static int[] getPixelRGBValue(BufferedImage image, int x, int y) {
			 int [] rgb  =   null ;
		        
	         if (image  !=   null   &&  x  <  image.getWidth()  &&  y  <  image.getHeight()) {
	            rgb  =   new   int [ 3 ];
	            int  pixel  =  image.getRGB(x,y);
	            System.out.print("\n�ض����ص�RGBֵ��\nrgb = "+pixel+"\t");	            
	            rgb[ 0 ]  =  (pixel  &   0xff0000 )  >>   16 ;
	            rgb[ 1 ]  =  (pixel  &   0xff00 )  >>   8 ;
	            rgb[ 2 ]  =  (pixel  &   0xff );
	            for (int i = 0; i < rgb.length; i++) {
					System.out.print("rgb["+i+"]="+rgb[i]+"\t");
				}
	            
	            System.out.println(pixel/65535);
	        } 
	        return rgb;
		}
		
		/**
		 * ��ָ��·������һ����ԴͼƬ��ͬ��ͼƬ�ļ�
		 * 
		 * @param image  ԴͼƬ��BuffferedImage����
		 * @param objectURL	 	���ͼƬ·��
		 * 
		 */
		public static void writeImage(BufferedImage image, String objectURL) {
			File outFile = new File(objectURL);
			try {
				ImageIO.write(image, "jpg", outFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		  /**
	     *  ��ȡRGBͼ��
	     *  @param	image	Դͼ��
	     *  @param	startX	��ȡ����ʼ��x����
	     *  @param	startY	��ȡ����ʼ��y����
	     *  @param	width	ͼ��Ŀ��
	     *  @param	height 	ͼ��ĸ߶�
	     *  @param	pixels	�洢rgb����ֵ����
	     *  @param	offset	ƫ����
	     *  @param	scanSIze	ÿ��ɨ��ķ�Χ��С
	     *  
	     *	@return	rgb����
	     */
	public static int[] getImageRGB(BufferedImage image, int startX, int startY, int width, int height, int[]pixels, int offset, int scanSize) {
			int type= image.getType();
			if ( type ==BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			     return (int [])image.getRaster().getDataElements(startX, startY, width, height, pixels);
			else
			    return image.getRGB(startX, startY, width, height, pixels, offset, scanSize);
		}
		
	  /**
     *  ��ȡRGB����ֵ��ɫ��������
     *  @param	rgb 32λ����RGB��ɫֵ
     *  
     *	@return	��ɫ��������
     */

		public static int[] read32bitRGBValue(int rgb) {
			int[] rgbArray = new int[4];
			int alpha = (rgb >> 24)& 0xff; //͸����ͨ��
			rgbArray[0] = alpha;
			int red = (rgb >> 16) &0xff;
			rgbArray[1] = red ;
			int green = (rgb >> 8) &0xff;
			rgbArray[2] = green ;
			int blue = rgb & 0xff;
			rgbArray[3] = blue ;
			
			System.out.println("\n32�ֽڵ�RGBֵ��");
			System.out.print("alpha = "+alpha+"\t red = "+red+"\t green = "+green+"\t blue = "+blue);
			return rgbArray;
		}
		/**
	     *  �õ�32λ����rgb��ɫֵ
	     *  @param	alpha	���ȷ���
	     *  @param	red		��ɫ����
	     *  @param	green	��ɫ����
	     *  @param	blue	��ɫ����
	     *  
	     *	@return rgb��ɫֵ
	     */
		
		public static int write32bitRGBValue(int alpha, int red, int green, int blue) {
			int rgb ;
			rgb = (alpha << 24) | (red<< 16) | (green << 8) | blue;
			System.out.println("rgb="+rgb);
			return  rgb;
		}
		
		  /**
	     *  ��ȡRGBֵ
	     *  @param	bufferdImage	Դͼ��
	     *  @param	x	��ȡ����ʼ��x����
	     *  @param	y	��ȡ����ʼ��y����
	     *  @param	w	ͼ��Ŀ��
	     *  @param	h 	ͼ��ĸ߶�
	     *  @param	pixels	�洢rgb����ֵ����
	     *  
	     *	@return	rgb����
	     */
	public static int[] getPixelsDataElement(BufferedImage bufferedImage, int x, int y, int w, int h, int pixels[]){
			int type = bufferedImage.getType() ;
			if (!(BufferedImage.TYPE_INT_ARGB==type||BufferedImage.TYPE_INT_BGR==type)) {
				return (int[])bufferedImage.getRaster().getDataElements(x, y, w, h, pixels);
			}else {
				return bufferedImage.getRGB(x, y, w, h, pixels, 0, w);
			}
		}
		public static int getAlpha(int pixel) {
			return (pixel >> 24) & 0xff;
		}

		public static int getBlue(int pixel) {
			return pixel & 0xff;
		}

		public static int getGreen(int pixel) {
			return (pixel >> 8) & 0xff;
		}

		public static int getRed(int pixel) {
			return (pixel >> 16) & 0xff;
		}
		  /**
	     *  ��ȡRGBֵ
	     *  @param	src	Դͼ��
	     *  @param	pixels	�洢rgb����ֵ����
	     *  
	     *	@return	rgb����
	     */

		public static int[] getRGB(BufferedImage src, int[] pixels) {
			int type = src.getType();
			int width = src.getWidth();
			int height = src.getHeight();
			if (type == BufferedImage.TYPE_INT_ARGB
					|| type == BufferedImage.TYPE_INT_RGB)
				return (int[]) src.getRaster().getDataElements(0, 0, width, height,
						pixels);
			return src.getRGB(0, 0, width, height, pixels, 0, width);
		}
		  /**
	     *  ����RGBֵ
	     *  @param	src	Դͼ��
	     *  @param	pixels	�洢rgb����ֵ����
	     */

		public static void setRGB(BufferedImage src, int[] pixels) {
			int type = src.getType();
			int width = src.getWidth();
			int height = src.getHeight();
			if (type == BufferedImage.TYPE_INT_ARGB
					|| type == BufferedImage.TYPE_INT_RGB)
				src.getRaster().setDataElements(0, 0, width, height, pixels);
			else
				src.setRGB(0, 0, width, height, pixels, 0, width);
		}
		
		
}
