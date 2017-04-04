package studio.jjm.facedetect.integral;
/**
 *	�����ͼ�ķ�����
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class IntegralTool {

    /**
     * ��û���ͼ�е�(x,y)�Ļ���ֵ
     * 
     * @param x     ������
     * @param y     ������
     * @param grays     �Ҷ�ͼ
     * @param integrogram   ����ͼ
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
     * �������Ϊ0�ȵľ�������������ͼ�Ķ���
     * 
     * @param grays     �Ҷ�ͼ
     * @param integrogram   ����ͼ
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
