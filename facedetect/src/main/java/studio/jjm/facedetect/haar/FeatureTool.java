package studio.jjm.facedetect.haar;

import java.util.ArrayList;
import java.util.Collections;

import studio.jjm.facedetect.integral.Integrogram;

/**
 *	求矩形特征的相关方法
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class FeatureTool {
	/**
     *  求特征矩形值
     * 	@param	width	特征矩形宽
     * 	@param	height	特征矩形高
     * 	@param	w		黑/白矩形宽
     * 	@param	h		黑白矩形的高		
     *	@param	type	Haar特征类型
     *	@param	fTemplates	特征模板数组
     */

    public static void featureTemplates(int width, int height, int w, int h,
            int type, ArrayList<FeatureTemplate> fTemplates) {
        int xRatio = 0, yRatio = 0;
        int w1 = 0, h1 = 0;
        int i = 0;
        if (type == Haar_Like.A1 | type == Haar_Like.A2 | type == Haar_Like.B1
                | type == Haar_Like.B2 | type == Haar_Like.B3
                | type == Haar_Like.B4 | type == Haar_Like.C1) {
            xRatio = width / w;
            yRatio = height / h;
            System.out.println("X = " + xRatio + ",\tY = " + yRatio);
            for (int m = 1; m <= xRatio; m++) {
                for (int n = 1; n <= yRatio; n++) {
                    w1 = m * w;
                    h1 = n * h;
                    int xm = width - w1 - 1;
                    int yn = height - h1 - 1;
                    for (int x = -1; x <= xm; x++) {
                        for (int y = -1; y <= yn; y++) {
                            fTemplates.get(i).w = w1;
                            fTemplates.get(i).h = h1;
                            fTemplates.get(i).x = x;
                            fTemplates.get(i).y = y;
                            fTemplates.get(i).type = type;
                            i++;
                        }
                    }
                }
            }

        }
    }
    /**
     *  求矩形特征值
     * 	@param	featrue	特征模板
     *	@param	integralImage	积分图
     *
     *	@return	矩形特征值
     */

    public static int featureValue(FeatureTemplate feature, int[][] integralImage) {
        int value = 0;
        switch (feature.type) {
            case Haar_Like.A1:
                value = -1
                        * rectSum(feature.x, feature.y, feature.w, feature.h,
                                feature.type, integralImage)
                        + 2
                        * rectSum(feature.x + feature.w / 2, feature.y,
                                feature.w / 2, feature.h, feature.type,
                                integralImage);
                break;
            case Haar_Like.A2:
                value = -1
                        * rectSum(feature.x, feature.y, feature.w, feature.h,
                                feature.type, integralImage)
                        + 2
                        * rectSum(feature.x, feature.y + feature.h / 2,
                                feature.w, feature.h / 2, feature.type,
                                integralImage);
                break;
            case Haar_Like.B1:
                value = -1
                        * rectSum(feature.x, feature.y, feature.w, feature.h,
                                feature.type, integralImage)
                        + 3
                        * rectSum(feature.x + feature.w / 3, feature.y,
                                feature.w / 3, feature.h, feature.type,
                                integralImage);
                break;
            case Haar_Like.B2:
                value = -1
                        * rectSum(feature.x, feature.y, feature.w, feature.h,
                                feature.type, integralImage)
                        + 3
                        * rectSum(feature.x, feature.y + feature.h / 3,
                                feature.w, feature.h / 3, feature.type,
                                integralImage);
                break;
            case Haar_Like.B3:
                value = -1
                        * rectSum(feature.x, feature.y, feature.w, feature.h,
                                feature.type, integralImage)
                        + 2
                        * rectSum(feature.x + feature.w / 4, feature.y,
                                feature.w / 2, feature.h, feature.type,
                                integralImage);
                break;
            case Haar_Like.B4:
                value = -1
                        * rectSum(feature.x, feature.y, feature.w, feature.h,
                                feature.type, integralImage)
                        + 2
                        * rectSum(feature.x, feature.y + feature.h / 4,
                                feature.w, feature.h / 2, feature.type,
                                integralImage);
                break;
            case Haar_Like.C1:
                value = -1
                        * rectSum(feature.x, feature.y, feature.w, feature.h,
                                feature.type, integralImage)
                        + 9
                        * rectSum(feature.x + feature.w / 3, feature.y
                                + feature.h / 3, feature.w / 3, feature.h / 3,
                                feature.type, integralImage);

                break;
            default:
                break;
        }
        return value;
    }
    /**
     *  矩形特征值排序
     * 	@param	fTemplate	特征模板类型
     * 	@param	integralImage	积分图
     * 	@param	features		特征值
     *	
     */

    public static void sortFeatureValue(FeatureTemplate fTemplate,
            ArrayList<Integrogram> integralImage,
            ArrayList<FeatureValue> features) {
        int size = integralImage.size();
        // System.out.println("Size = " + size);
        for (int i = 0; i < size; i++) {
            features.get(i).value = featureValue(fTemplate,
                    integralImage.get(i).integrogram);
            features.get(i).id = integralImage.get(i).id;
        }
        Collections.sort(features);
    }

    /**
     *  求矩形特征数目
     * 	@param	x	矩形特征在积分图中的x坐标位置
     * 	@param	y  矩形特征在积分图中的y坐标位置
     * 	@param	w		黑/白矩形宽
     *	@param	h		黑/白矩形高
     *	@param	type	矩形特征的Haar特征模板类型
     *	@param	integrogram	积分图
     *
     *	@return	矩形特征数目
     *  x >= -1 , y > -1 
     */
    public static int rectSum(int x, int y, int w, int h, int type,
            int[][] integrogram) {
        int sum = 0;
        if (type == Haar_Like.A1 | type == Haar_Like.A2 | type == Haar_Like.B1
                | type == Haar_Like.B2 | type == Haar_Like.B3
                | type == Haar_Like.B4 | type == Haar_Like.C1) {
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
        } 
        return sum;
    }
    /**
     *  求矩形特征数目
     * 	@param	width	矩形特征宽
     * 	@param	height  矩形特征高
     * 	@param	w		黑/白矩形宽
     *	@param	h		黑/白矩形高
     *	@param	type	矩形特征的Haar特征模板类型
     *
     *	@return	矩形特征数目
     */

    public static int featureCount(int width, int height, int w, int h, int type) {
        int xRatio = 0, yRatio = 0;
        int count = 0;
        if (type == Haar_Like.A1 | type == Haar_Like.A2 | type == Haar_Like.B1
                | type == Haar_Like.B2 | type == Haar_Like.B3
                | type == Haar_Like.B4 | type == Haar_Like.C1) {
            xRatio = width / w;
            yRatio = height / h;
            System.out.println("X = " + xRatio + ",\tY = " + yRatio);
            for (int m = 1; m <= xRatio; m++) {
                for (int n = 1; n <= yRatio; n++) {
                    int xm = width - m * w - 1;
                    int yn = height - n * h - 1;
                    for (int x = -1; x <= xm; x++) {
                        for (int y = -1; y <= yn; y++) {
                            count++;
                        }
                    }
                }
            }
            System.out.println("count = " + count);
        } 
        return count;
    }

}
