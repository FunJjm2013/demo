package studio.jjm.facedetect.gray;

import java.awt.image.BufferedImage;

/**
 *	求灰度值的相关方法类
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class GrayTool {

    
	 /**
     *  求灰度图的灰度值
     *	@param	image	原图像
     *
     *	@return	图像的灰度矩阵
     */

    // value >= 0 && value <= 255 
    public static int[][] grayValuesByMatrix1(BufferedImage image){
        if (image == null) {
            return null ;
        }
        int width = image.getWidth() ;
        int height = image.getHeight() ;
        int[][] grays = new int[height][width] ;
        int rgb, r, g, b, value;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgb = image.getRGB(j,i) ;
                r = (rgb >> 16) & 0xff;
                g = (rgb >> 8) & 0xff;
                b = rgb & 0xff;
                value = (77 * r + 151 * g + 28 * b) >> 8;           
                grays[i][j] = value ;           // value >=0 && value <= 255
            }
        }
        return grays ;
    }
	 /**
     *  求灰度图的灰度值
     *	@param	image	原图像
     *
     *	@return	图像的灰度图
     */

    // value >= 0 && value <= 255 
    public static int[] grayValuesByArray1(BufferedImage image){
        if (image == null) {
            return null ;
        }

        int width = image.getWidth() ;
        int height = image.getHeight() ;
        int[] grays = new int[height*width] ;
        int rgb, r, g, b, value;
        int k = 0 ;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgb = image.getRGB(j,i) ;
                r = (rgb >> 16) & 0xff;
                g = (rgb >> 8) & 0xff;
                b = rgb & 0xff;
                value = (77 * r + 151 * g + 28 * b) >> 8;           
                grays[k++] = value ;        // value >=0 && value <= 255
            }
        }
        return grays ;
    }
	 /**
     *  求灰度图的灰度值
     *	@param	image	原图像
     *
     *	@return	图像的灰度矩阵
     */

    // value >= 0 && value <= 255 
    public static int[][] grayValuesByMatrix2(BufferedImage image){
        if (image == null) {
            return null ;
        }
        int width = image.getWidth() ;
        int height = image.getHeight() ;
        int[][] grays = new int[height][width] ;
        int rgb, r, g, b, value;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgb = image.getRGB(j,i) ;
                r = (rgb >> 16) & 0xff;
                g = (rgb >> 8) & 0xff;
                b = rgb & 0xff;
                value = (77 * r + 151 * g + 28 * b) >> 8;           
                grays[i][j] = value << 16 | value << 8 | value;        //计算灰度值
            }
        }
        return grays ;
    }
	 /**
     *  求灰度图的灰度值
     *	@param	image	原图像
     *
     *	@return	图像的灰度图
     */

    // value >= 0 && value <= 255 
    public static int[] grayValuesByArray2(BufferedImage image){
        if (image == null) {
            return null ;
        }

        int width = image.getWidth() ;
        int height = image.getHeight() ;
        int[] grays = new int[height*width] ;
        int rgb, r, g, b, value;
        int k = 0 ;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgb = image.getRGB(j,i) ;
                r = (rgb >> 16) & 0xff;
                g = (rgb >> 8) & 0xff;
                b = rgb & 0xff;
                value = (77 * r + 151 * g + 28 * b) >> 8;           
                grays[k++] = value << 16 | value << 8 | value;        //计算灰度值
            }
        }
        return grays ;
    }


}
