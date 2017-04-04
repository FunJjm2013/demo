package studio.jjm.facedetect.adaboost;

/**
 *	测试规一化的类
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class TestNormalization {

    public static void main(String[] args) {
        
        
        
/*        System.out.println(Math.pow(4, 2.5)) ;
        int posCount = 4, negCount = 2 ;
        double[] weights = new double[6] ;
        double totalWei = 0.0 ;
        for (int i = 0; i < posCount; i++) {
            weights[i] = 1.0 / posCount ;
            totalWei += weights[i] ;
        }
        for (int i = posCount; i < weights.length; i++) {
            weights[i] = 1.0 / negCount ; 
            totalWei += weights[i] ;
        }
        
        for (int i = 0; i < weights.length; i++) {
            weights[i] /= totalWei ;
        }
        
        totalWei = 0.0 ;
        for (int i = 0; i < weights.length; i++) {
            totalWei += weights[i] ;
        }
        
        for (int i = 0; i < 3; i++) {
            weights[i] *= 0.7 ;
        }
        
        for (int i = 4; i < 6; i++) {
            weights[i] *= 1.2 ;
        }
        
        totalWei = 0.0 ;
        for (int i = 0; i < weights.length; i++) {
            totalWei += weights[i] ;
        }

        for (int i = 0; i < weights.length; i++) {
            weights[i] /= totalWei ;
        }

        totalWei = 0.0 ;
        for (int i = 0; i < weights.length; i++) {
            totalWei += weights[i] ;
        }
*/
 
        
    }
    
    public static int myFeatureCount(int W, int H, int w, int h, int angle){
        int count = 0 ;
        int X = 0 , Y = 0 ;
        if (angle == 0) {
            X = W / w ;
            Y = H / h ;
            count = X * Y * (W + 1 - w * (X+1)/2)*(H + 1 - h * (Y+1)/2) ;
        }else {
            X = W / (w+h) ;
            Y = H / (w+h) ;
            count = X * Y * (W + 1 - (w+h) * (X+1)/2)*(H + 1 - (w+h) * (Y+1)/2) ;

        }
        return count ;
    }
}
