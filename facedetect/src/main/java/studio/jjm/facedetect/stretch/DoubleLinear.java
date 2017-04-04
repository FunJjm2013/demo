package studio.jjm.facedetect.stretch;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import studio.jjm.facedetect.gray.GrayTool;
import studio.jjm.facedetect.util.ImageTool;

/**
 * 	双线性叉值
 *	
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class DoubleLinear {

    
    public static void main(String[] args) {
        BufferedImage source = ImageTool.getSourceImage("D:\\Picture\\640.jpg") ;
        BufferedImage objectImage = stretch(source, 1.2f, 1.0f);
        ImageTool.writeImage(objectImage,  "D:\\Picture\\5.jpg") ;
    }
                
    public static BufferedImage doubleLinearAlgorithm1(BufferedImage source, int newWidth, int newHeight){
        int a = 0, b = 0, value;
        int sourceWidth = source.getWidth() ;
        int sourceHeight = source.getHeight() ;
        int[][] grays = GrayTool.grayValuesByMatrix1(source) ;
        System.out.println(grays[50][10]);
        double tw = sourceWidth * 1.0 / newWidth ;
        double th = sourceHeight * 1.0 / newHeight ;
        System.out.println("tw = " + tw + ",\tth = " + th);
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, source.getType()) ;
        int[] rgbs = new int[newWidth*newHeight] ;
        int y = 0, x = 0 ;
        int i = 0 ;
        for (y = 0; y < newHeight-1; y++) {
            b = (int)(y*th) ;
            for (x = 0; x < newWidth; x++) {
                a = (int)(x*tw) ;
            //    System.out.println("(a,b) = " + "(" + a + "," + b + ")");
                if (a+1 < sourceWidth && b+1 < sourceHeight) {
                    rgbs[i++] = (b+1-y)*((x-a)*grays[b][a+1]+(a+1-x)*grays[b][a])
                            + (y-b)*((x-a)*grays[b+1][a+1]+(a+1-x)*grays[b+1][a]) ;                    
                }else {
                    rgbs[i++] = grays[b][a];
                }
            }
        }
        b = (int)(y*th) ;
        for (x = 0; x < newWidth; x++) {
            a = (int)(x*tw) ;
            rgbs[i++] = grays[b][a];
        }
        
        int type = newImage.getType();
        if (type == BufferedImage.TYPE_INT_ARGB
                || type == BufferedImage.TYPE_INT_RGB)
            newImage.getRaster().setDataElements(0, 0, newWidth, newHeight, rgbs);
        else
            newImage.setRGB(0, 0, newWidth, newHeight, rgbs, 0, newWidth);

        return newImage ;
    }

    
    public static BufferedImage doubleLinearAlgorithm(BufferedImage source, int newWidth, int newHeight){
        int a = 0, b = 0, value;
        int sourceWidth = source.getWidth() ;
        int sourceHeight = source.getHeight() ;
        int[][] grays = GrayTool.grayValuesByMatrix1(source) ;
        double tw = sourceWidth * 1.0 / newWidth ;
        double th = sourceHeight * 1.0 / newHeight ;
        System.out.println("tw = " + tw + ",\tth = " + th);
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, source.getType()) ;
        int[] rgbs = new int[newWidth*newHeight] ;
        int y = 0, x = 0 ;
        int i = 0 ;
        for (y = 0; y < newHeight-1; y++) {
            b = (int)(y*th) ;
            for (x = 0; x < newWidth; x++) {
                a = (int)(x*tw) ;
            //    System.out.println("(a,b) = " + "(" + a + "," + b + ")");
                if (a+1 < sourceWidth && b+1 < sourceHeight) {
                    rgbs[i++] = (b+1-y)*((x-a)*source.getRGB(a+1, b)+(a+1-x)*source.getRGB(a, b))
                            + (y-b)*((x-a)*source.getRGB(a+1, b+1)+(a+1-x)*source.getRGB(a, b+1)) ;                    
                }else {
                    rgbs[i++] = source.getRGB(a, b);
                }
            }
        }
        b = (int)(y*th) ;
        for (x = 0; x < newWidth; x++) {
            a = (int)(x*tw) ;
            rgbs[i++] = source.getRGB(a, b);
        }
        
        int type = newImage.getType();
        if (type == BufferedImage.TYPE_INT_ARGB
                || type == BufferedImage.TYPE_INT_RGB)
            newImage.getRaster().setDataElements(0, 0, newWidth, newHeight, rgbs);
        else
            newImage.setRGB(0, 0, newWidth, newHeight, rgbs, 0, newWidth);

        return newImage ;
    }

    ////////////图形伸缩， 输入参数为图象像素矩阵，和横向，纵向拉伸比例参数\\\\\\\\\\\\\\\\\\\
    public static BufferedImage stretch(BufferedImage source, float xscale, float yscale){
        
        //////////使用双线性插值算法\\\\\\\\\\\\\\\\\\\
        int[]flipArray = null;
        int i ,j, x0, y0;
        float u, v;
        int w = source.getWidth() ;
        int h = source.getHeight() ;
        int width = (int) (w * xscale);
        int height = (int) (h * yscale);
        BufferedImage newImage = new BufferedImage(width, height, source.getType()) ;    
        flipArray = new int[width * height];
        int[] grays = getPixArray(source, w, h);        //彩色图像
        //int[] grays = GrayTool.grayValuesByArray2(source) ;       //灰度图像
        if( ( source == null ) || ( xscale <=0 ) || ( yscale <= 0 ) ) 
            return null; 
        
        for( j=0; j<height; j++ ) {
            for(i=0; i<width; i++ ) {
                x0 = (int) ( i*1.0 / xscale);
                y0 = (int) ( j*1.0 / yscale );
                u = (int)(Math.floor(i*1.0) / xscale) - x0;
                v = (int)(Math.floor(j*1.0) / yscale) - y0;
                if( ((x0 + 1) < w ) && (( y0+1 )< h ) ) {
                     flipArray[i+j*width] = (int) ((1-u)*(1-v) * grays[x0 + y0*w] + u * (1-v) * grays[(x0+1)+y0*w] + 
                            v * (1-u) * grays[x0+(y0+1)*w] + u * v * grays[(x0+1) + (y0 + 1 )*w]);
                } else 
                    flipArray[i+j*width] =  grays[x0 + y0*w];
                    
              }
        }
        
        int type = newImage.getType();
        if (type == BufferedImage.TYPE_INT_ARGB
                || type == BufferedImage.TYPE_INT_RGB)
            newImage.getRaster().setDataElements(0, 0, width, height, flipArray);
        else
            newImage.setRGB(0, 0, width, height, flipArray, 0, width);

        return newImage;
     }    
 
     public static int[]getPixArray(Image im,int w,int h){
        int[] pix=new int[w*h];
        PixelGrabber pg=null;
        try{
          pg = new PixelGrabber(im, 0, 0, w, h, pix, 0, w);
          if(pg.grabPixels()!=true)
            try{
              throw new java.awt.AWTException("pg error"+pg.status());
            }catch(Exception eq){
                    eq.printStackTrace();
            }
        } catch(Exception ex){
                ex.printStackTrace();
     
        }
       return pix;
     }

}
