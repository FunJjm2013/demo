package studio.jjm.facedetect.adaboost;

import java.util.ArrayList;

/**
 *	级联分类器类
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class CascadeClassifier {

    public int stage ;      //强分类器个数
    public ArrayList<StrongClassifier> strongClassifiers ;      //强分类器集合

}
