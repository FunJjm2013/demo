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
     *  标出图像中人脸的位置
     *	@param	rectangles	人脸区域
     *	@param	src	检测的图像
     *
     *	@return	标出了人脸区域的图像
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
     * 	图像规一化
     *	@param	grays 灰度图
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
     *  求灰度值
     *	@param	image	图像
     *
     *	@return	灰度图
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
                  //  grays[i][j] = value << 16 | value << 8 | value;        //计算灰度值
                }
            }
            return grays ;
        }else {
            throw new NullPointerException("计算灰度值时，图像对象为空！") ;
        }
    }
    
    /**
     *  从文件路径中读取图像
     *	@param	url		文件路径
     *
     *	@return	被读取的图像
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
	     *  从文件中读取图像
	     *	@param	url		文件
	     *
	     *	@return	被读取的图像
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
			 * 获得指定图片的所有像素的RGB值
			 * 
			 * @param url   源图片的路径
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
			System.out.println("==========\n图片的RGB值：");
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
	     *  获取RGB图像
	     *	@return	图像
	     */

		public static void RBGS(BufferedImage image) {
			System.out.println("++++++++++++++++++++\n获得图片默认的RGB值：");
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
		 * 获得图片上某一特定的像素的RGB值
		 * 
		 * @param image  源图片的BuffferedImage对象
		 * @param x		BufferedImage 中方法getRGB(int x,int y)要设置的像素的 X 坐标
		 * @param y		BufferedImage 中方法getRGB(int x,int y)要设置的像素的 Y 坐标 
		 * 
		 * @return int[] 一个像素单元的RGB值
		 */
		public static int[] getPixelRGBValue(BufferedImage image, int x, int y) {
			 int [] rgb  =   null ;
		        
	         if (image  !=   null   &&  x  <  image.getWidth()  &&  y  <  image.getHeight()) {
	            rgb  =   new   int [ 3 ];
	            int  pixel  =  image.getRGB(x,y);
	            System.out.print("\n特定像素的RGB值：\nrgb = "+pixel+"\t");	            
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
		 * 向指定路径创建一个与源图片相同的图片文件
		 * 
		 * @param image  源图片的BuffferedImage对象
		 * @param objectURL	 	输出图片路径
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
	     *  获取RGB图像
	     *  @param	image	源图像
	     *  @param	startX	获取的起始点x坐标
	     *  @param	startY	获取的起始点y坐标
	     *  @param	width	图像的宽度
	     *  @param	height 	图像的高度
	     *  @param	pixels	存储rgb像素值数组
	     *  @param	offset	偏移量
	     *  @param	scanSIze	每次扫描的范围大小
	     *  
	     *	@return	rgb数组
	     */
	public static int[] getImageRGB(BufferedImage image, int startX, int startY, int width, int height, int[]pixels, int offset, int scanSize) {
			int type= image.getType();
			if ( type ==BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			     return (int [])image.getRaster().getDataElements(startX, startY, width, height, pixels);
			else
			    return image.getRGB(startX, startY, width, height, pixels, offset, scanSize);
		}
		
	  /**
     *  获取RGB像素值颜色分量数组
     *  @param	rgb 32位整型RGB颜色值
     *  
     *	@return	颜色分量数组
     */

		public static int[] read32bitRGBValue(int rgb) {
			int[] rgbArray = new int[4];
			int alpha = (rgb >> 24)& 0xff; //透明度通道
			rgbArray[0] = alpha;
			int red = (rgb >> 16) &0xff;
			rgbArray[1] = red ;
			int green = (rgb >> 8) &0xff;
			rgbArray[2] = green ;
			int blue = rgb & 0xff;
			rgbArray[3] = blue ;
			
			System.out.println("\n32字节的RGB值：");
			System.out.print("alpha = "+alpha+"\t red = "+red+"\t green = "+green+"\t blue = "+blue);
			return rgbArray;
		}
		/**
	     *  得到32位整型rgb颜色值
	     *  @param	alpha	亮度分量
	     *  @param	red		红色分量
	     *  @param	green	绿色分量
	     *  @param	blue	蓝色分量
	     *  
	     *	@return rgb颜色值
	     */
		
		public static int write32bitRGBValue(int alpha, int red, int green, int blue) {
			int rgb ;
			rgb = (alpha << 24) | (red<< 16) | (green << 8) | blue;
			System.out.println("rgb="+rgb);
			return  rgb;
		}
		
		  /**
	     *  获取RGB值
	     *  @param	bufferdImage	源图像
	     *  @param	x	获取的起始点x坐标
	     *  @param	y	获取的起始点y坐标
	     *  @param	w	图像的宽度
	     *  @param	h 	图像的高度
	     *  @param	pixels	存储rgb像素值数组
	     *  
	     *	@return	rgb数组
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
	     *  获取RGB值
	     *  @param	src	源图像
	     *  @param	pixels	存储rgb像素值数组
	     *  
	     *	@return	rgb数组
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
	     *  设置RGB值
	     *  @param	src	源图像
	     *  @param	pixels	存储rgb像素值数组
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
