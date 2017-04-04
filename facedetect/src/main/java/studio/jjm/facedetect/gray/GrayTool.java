package studio.jjm.facedetect.gray;

import java.awt.image.BufferedImage;

/**
 *	��Ҷ�ֵ����ط�����
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class GrayTool {

    
	 /**
     *  ��Ҷ�ͼ�ĻҶ�ֵ
     *	@param	image	ԭͼ��
     *
     *	@return	ͼ��ĻҶȾ���
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
     *  ��Ҷ�ͼ�ĻҶ�ֵ
     *	@param	image	ԭͼ��
     *
     *	@return	ͼ��ĻҶ�ͼ
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
     *  ��Ҷ�ͼ�ĻҶ�ֵ
     *	@param	image	ԭͼ��
     *
     *	@return	ͼ��ĻҶȾ���
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
                grays[i][j] = value << 16 | value << 8 | value;        //����Ҷ�ֵ
            }
        }
        return grays ;
    }
	 /**
     *  ��Ҷ�ͼ�ĻҶ�ֵ
     *	@param	image	ԭͼ��
     *
     *	@return	ͼ��ĻҶ�ͼ
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
                grays[k++] = value << 16 | value << 8 | value;        //����Ҷ�ֵ
            }
        }
        return grays ;
    }


}
