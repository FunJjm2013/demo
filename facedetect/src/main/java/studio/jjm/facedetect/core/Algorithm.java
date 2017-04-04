package studio.jjm.facedetect.core;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import studio.jjm.facedetect.util.ImageTool;

/**
 *	算法 类
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public final class Algorithm {

    /**
     * 对于倾角为0度的矩形特征，积分图的定义
     * 
     * @param x     纵坐标，对应图像的width
     * @param y     横坐标，对应图像的height
     * @param grays     灰度图
     * @param integrogram   积分图
     */
    public static int integralImage(int x, int y, int[][] grays,
            int[][] integrogram) {
        if (x > 0 && y > 0) {
            integrogram[y][x] = integrogram[y][x - 1] + integrogram[y - 1][x]
                    + grays[y][x] - integrogram[y - 1][x - 1];
            return integrogram[y][x];
        } else if (x == 0 && y == 0) {
            integrogram[y][x] = grays[y][x];
            return integrogram[y][x];
        } else if (x == 0) {
            integrogram[y][x] = integrogram[y - 1][x] + grays[y][0];
            return integrogram[y][x];
        } else if (y == 0) {
            integrogram[y][x] = integrogram[y][x - 1] + grays[0][x];
            return integrogram[y][x];
        } else {
            return 0;
        }
    }

    /**
     * 对于倾角为45度的矩形特征，积分图的定义
     * 
     * @param x     纵坐标，对应图像的width
     * @param y     横坐标，对应图像的height
     * @param grays     灰度图
     * @param integrogram   积分图
     */
    public static int rotatedIntegralImage(int x, int y, int[][] grays,
            int[][] integrogram) {
        if (y == 0) {
            integrogram[y][x] = grays[y][x];
            return integrogram[y][x];
        } else if (y > 1 && x > 0 && x < grays[0].length - 1) {
            integrogram[y][x] = integrogram[y - 1][x - 1]
                    + integrogram[y - 1][x + 1] - integrogram[y - 2][x]
                    + grays[y][x] + grays[y - 1][x];
            return integrogram[y][x];
        } else if (y > 1 && x == 0) {
            integrogram[y][x] = integrogram[y - 1][x + 1]
                    - integrogram[y - 2][x] + grays[y][x] + grays[y - 1][x];
            return integrogram[y][x];
        } else if (y > 1 && x == grays[0].length - 1) {
            integrogram[y][x] = integrogram[y - 1][x - 1]
                    - integrogram[y - 2][x] + grays[y][x] + grays[y - 1][x];
            return integrogram[y][x];
        } else if (y == 1 && x > 0 && x < grays[0].length - 1) {
            integrogram[y][x] = integrogram[y - 1][x - 1]
                    + integrogram[y - 1][x + 1] + grays[y][x] + grays[y - 1][x];
            return integrogram[y][x];
        } else if (y == 1 && x == 0) {
            integrogram[y][x] = integrogram[y - 1][x + 1] + grays[y][x]
                    + grays[y - 1][x];
            return integrogram[y][x];
        } else if (y == 1 && x == grays[0].length - 1) {
            integrogram[y][x] = integrogram[y - 1][x - 1] + grays[y][x]
                    + grays[y - 1][x];
            return integrogram[y][x];
        } else {
            return 0;
        }

    }

    /**
     *  x >= -1 , y > -1 
     *  w为正，向右；为负，向左
     */
    public static int rectSum(int x, int y, int w, int h, float angle,
            int[][] integrogram) {
        int sum = 0;
        if (angle == 0.0f) {
            if (x > -1 && y > -1) {
                sum = integrogram[y][x] + integrogram[y + h][x + w]
                        - integrogram[y][x + w] - integrogram[y + h][x];
            } else if (x == -1 && y > -1) {
                sum = integrogram[y + h][x + w] - integrogram[y][x + w];
            } else if (y == -1 && x > -1) {
                sum = integrogram[y + h][x + w] - integrogram[y + h][x];
            } else if (x == -1 && y == -1) {
                sum = integrogram[y + h][x + w];
            }
        } else if (angle == 45.0f) {
            if (w > 0) {
                if (y > -1) {
                    if (x - h > -1 && x + w < integrogram[0].length) {
                        sum = integrogram[y + w + h][x + w - h]
                                + integrogram[y][x] - integrogram[y + h][x - h]
                                - integrogram[y + w][x + w];
                    } else if (x - h == -1) {
                        sum = integrogram[y + w + h][x + w - h]
                                + integrogram[y][x] - integrogram[y + w][x + w];
                    } else if (x + w == integrogram[0].length) {
                        sum = integrogram[y + w + h][x + w - h]
                                + integrogram[y][x] - integrogram[y + h][x - h];
                    }
                } else if (y == -1) {
                    if (x - h > -1 && x + w < integrogram[0].length) {
                        sum = integrogram[y + w + h][x + w - h]
                                - integrogram[y + h][x - h]
                                - integrogram[y + w][x + w];
                    } else if (x - h == -1) {
                        sum = integrogram[y + w + h][x + w - h]
                                - integrogram[y + w][x + w];
                    } else if (x + w == integrogram[0].length) {
                        sum = integrogram[y + w + h][x + w - h]
                                - integrogram[y + h][x - h];
                    }
                }
            } else {
                if (y > -1) {
                    if (x + w > -1 && x + h < integrogram[0].length) {
                        sum = integrogram[y + h - w][x + h + w]
                                + integrogram[y][x] - integrogram[y + h][x + h]
                                - integrogram[y - w][x + w];
                    } else if (x + w == -1) {
                        sum = integrogram[y + h - w][x + h + w]
                                + integrogram[y][x] - integrogram[y + h][x + h];
                    } else if (x + h == integrogram[0].length) {
                        sum = integrogram[y + h - w][x + h + w]
                                + integrogram[y][x] - integrogram[y - w][x + w];
                    }
                } else if (y == -1) {
                    if (x + w > -1 && x + h < integrogram[0].length) {
                        sum = integrogram[y + h - w][x + h + w]
                                - integrogram[y + h][x + h]
                                - integrogram[y - w][x + w];
                    } else if (x + w == -1) {
                        sum = integrogram[y + h - w][x + h + w]
                                - integrogram[y + h][x + h];
                    } else if (x + h == integrogram[0].length) {
                        sum = integrogram[y + h - w][x + h + w]
                                - integrogram[y - w][x + w];
                    }
                }
            }
        }
        return sum;
    }

/*    public static void transform(WeakClassifier weakClassifier, FeatureTemplate feature){
        switch (feature.type) {
            case Haar_Like.A1:

                break;
            case Haar_Like.A2:

                break;
            case Haar_Like.A3:

                break;
            case Haar_Like.A4:
                break;
            case Haar_Like.B1:
               break;
            case Haar_Like.B2:
                 break;
            case Haar_Like.B3:
               break;
            case Haar_Like.B4:
               break;
            case Haar_Like.B5:
               break;
            case Haar_Like.B6:
                break;
            case Haar_Like.B7:
               break;
            case Haar_Like.B8:
                break;
            case Haar_Like.C1:
                break;
            case Haar_Like.C2:
              break;
            case Haar_Like.D1:

                break;
            default:
                break;
        }

    }
*/    
    public static void doubleLinearInserting(BufferedImage sourceImage,
            BufferedImage objectImage) {

        int sWidth, sHeight, oWidth, oHeight, yy, xx, rgb, value1, value2, value3;
        float yy1, xx1, xx2 = 0.0f, a, b;
        float t = 1 ;       //缩放因子
        sWidth = sourceImage.getWidth();
        sHeight = sourceImage.getHeight();
        oWidth = objectImage.getWidth();
        oHeight = objectImage.getHeight();

        t = oWidth / sWidth ;
        
        for (int y = 0; y < oHeight; y++) {
            yy1 = sHeight * y / oHeight;
            yy = (int) yy1;
            b = yy1 - yy;

            if (yy < sHeight - 1) {
                yy += 1;
            }

            for (int x = 0; x < oWidth; x++) {
                xx1 = sWidth * x / oWidth;
                xx = (int) xx1;
                a = xx1 - xx;

                if (xx < sWidth - 1) {
                    xx2 = xx + 1;
                }

                if (xx < sWidth - 1) {
                    value1 = sourceImage.getRGB((int) (xx2 * 3), y);
                    value2 = sourceImage.getRGB(xx * 3, y);
                    value3 = sourceImage.getRGB(xx*3, y+1) ;
                    rgb = (int) (a * (value1 - value2) + b * (value3 - value2)
                            + a * b * (value1 + value2 - value2 - value1) + value2);
                    objectImage.setRGB(x * 3, y, rgb);

                    value1 = sourceImage.getRGB((int) (xx2 * 3) + 1, y);
                    value2 = sourceImage.getRGB(xx * 3 + 1, y);
                    rgb = (int) (a * (value1 - value2) + b * (value2 - value2)
                            + a * b * (value1 + value2 - value2 - value1) + value2);
                    objectImage.setRGB(x * 3 + 1, y, rgb);

                    value1 = sourceImage.getRGB((int) (xx2 * 3) + 2, y);
                    value2 = sourceImage.getRGB(xx * 3 + 2, y);
                    rgb = (int) (a * (value1 - value2) + b * (value2 - value2)
                            + a * b * (value1 + value2 - value2 - value1) + value2);
                    objectImage.setRGB(x * 3 + 2, y, rgb);

                } else {

                    objectImage.setRGB(x * 3, y, sourceImage.getRGB(xx * 3, y));
                    objectImage.setRGB(x * 3 + 1, y,
                            sourceImage.getRGB(xx * 3 + 1, y));
                    objectImage.setRGB(x * 3 + 2, y,
                            sourceImage.getRGB(xx * 3 + 2, y));

                }
            }
        }

    }

    
    public static BufferedImage doubleLinearInserting(BufferedImage sourceImage, float t) {

        int sWidth, sHeight, oWidth, oHeight, yy, xx, rgb;
        float yy1, xx1, u, v;
        sWidth = sourceImage.getWidth();
        sHeight = sourceImage.getHeight();
        oWidth = (int)(sWidth * t);
        oHeight = (int)(sHeight * t);
        BufferedImage objectImage = new BufferedImage(oWidth, oHeight, sourceImage.getType()) ;
        
        for (int y = 0; y < oHeight; y++) {
            yy1 = y / t;
            yy = (int) yy1;
            v = yy1 - yy;

            if (yy < sHeight - 1) {
                yy += 1;
            }

            for (int x = 0; x < oWidth; x++) {
                xx1 = x / t;
                xx = (int) xx1;
                u = xx1 - xx;

                if (xx < sWidth - 1) {
                    xx += 1;
                }

                if (xx < sWidth - 1) {
                    rgb = (int)((1-u) * (1-v) * sourceImage.getRGB(xx-1, yy-1) + (1-u) * v * sourceImage.getRGB(xx-1, yy)
                            + u * (1-v) * sourceImage.getRGB(xx, yy-1) + u * v * sourceImage.getRGB(xx, yy)) ;
                    objectImage.setRGB(x, y, rgb);
                } else {
                    objectImage.setRGB(x, y, sourceImage.getRGB(xx, yy));
                }
            }
        }
        
        return objectImage ;

    }

    public static void main(String[] args) {
        BufferedImage sourceImage = ImageTool.getSourceImage("D:\\Picture\\1.jpg") ;
        BufferedImage objectBufferedImage = doubleLinearInserting(sourceImage, 5) ;
        ImageTool.writeImage(objectBufferedImage, "D:\\Picture\\3.jpg") ;
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
        int[] grays = getPixArray(source, w, h);
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
 
    public static int[] getGrayValues(BufferedImage image){
        if (image != null) {
            int width = image.getWidth() ;
            int height = image.getHeight() ;
            int[] grays = new int[height*width] ;
            int rgb, r, g, b, value;
            int k = 0 ;
          //  System.out.println("Width = " + width + "\tHeight = " + height);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    rgb = image.getRGB(j,i) ;
                    r = (rgb >> 16) & 0xff;
                    g = (rgb >> 8) & 0xff;
                    b = rgb & 0xff;
                    value = (77 * r + 151 * g + 28 * b) >> 8;           
                    grays[k++] = value << 16 | value << 8 | value ;
                  //  grays[i][j] = value << 16 | value << 8 | value;        //计算灰度值
                }
            }
            return grays ;
        }else {
            throw new NullPointerException("计算灰度值时，图像对象为空！") ;
        }
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
