package studio.jjm.facedetect.core;

import java.util.ArrayList;

public class NewStrongClassifier {

    public int count ;      //弱分类器个数
    public double threshold ;    //强分类器阈值
    public ArrayList<NewWeakClassifier> weakClassifiers ;      //弱分类器集合
}
