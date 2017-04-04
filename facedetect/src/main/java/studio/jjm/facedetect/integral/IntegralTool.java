package studio.jjm.facedetect.integral;
/**
 *	求积分图的方法类
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class IntegralTool {

    /**
     * 求得积分图中点(x,y)的积分值
     * 
     * @param x     纵坐标
     * @param y     横坐标
     * @param grays     灰度图
     * @param integrogram   积分图
     * 
     */

    public static void sat(int x, int y, int[][] grays,
            int[][] integrogram) {
        if (x > 0 && y > 0) {
            integrogram[y][x] = integrogram[y][x - 1] + integrogram[y - 1][x]
                    + grays[y][x] - integrogram[y - 1][x - 1];
        } else if (x == 0 && y == 0) {
            integrogram[y][x] = grays[y][x];
        } else if (x == 0) {
            integrogram[y][x] = integrogram[y - 1][x] + grays[y][0];
        } else if (y == 0) {
            integrogram[y][x] = integrogram[y][x - 1] + grays[0][x];
        } 
    }
 
    /**
     * 对于倾角为0度的矩形特征，积分图的定义
     * 
     * @param grays     灰度图
     * @param integrogram   积分图
     */

    public static void integralImage(int[][] grays, int[][] integrogram){
        int width = grays[0].length ;
        int height = grays.length ;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sat(j, i, grays, integrogram) ;
            }
        }
    }

}
