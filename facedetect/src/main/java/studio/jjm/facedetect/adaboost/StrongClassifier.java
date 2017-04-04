package studio.jjm.facedetect.adaboost;

import java.io.Serializable;
import java.util.ArrayList;
/**
 *	ǿ��������
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class StrongClassifier implements Serializable, Cloneable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public double threshold ;    //ǿ��������ֵ
    public ArrayList<WeakClassifier> weakClassifiers ;      //������������
    public StrongClassifier() {
        // TODO Auto-generated constructor stub
        threshold = -1 ;
        weakClassifiers = new ArrayList<WeakClassifier>(1) ;
    }
    public StrongClassifier(double threshold,
            ArrayList<WeakClassifier> weakClassifiers) {
        super();
        this.threshold = threshold;
        this.weakClassifiers = weakClassifiers;
    }
    
    @Override
    public String toString() {
        return "StrongClassifier [threshold="
                + this.threshold + ", weakClassifiers=" + this.weakClassifiers
                + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone() ;
    }
}
