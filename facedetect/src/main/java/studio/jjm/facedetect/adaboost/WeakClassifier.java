package studio.jjm.facedetect.adaboost;

import java.io.Serializable;

import studio.jjm.facedetect.haar.FeatureTemplate;
/**
 *	弱分类器类
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class WeakClassifier implements Serializable, Cloneable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public FeatureTemplate feature ;
    public double result1, result2 ;       //分类结果
    public double value ;      //阈值
    public double   minError;
    public int     offset;
    
    public WeakClassifier() {
        // TODO Auto-generated constructor stub
        feature = new FeatureTemplate(0, 0, 0, 0, 0) ;
        result1 = 0.0 ;
        result2 = 0.0 ;
        value = 0.0 ;
        minError = 1.0 ;
        offset = 1 ;
    }
    public WeakClassifier(FeatureTemplate feature, double result1,
            double result2, double value, double minError, int offset) {
        super();
        this.feature = feature;
        this.result1 = result1;
        this.result2 = result2;
        this.value = value;
        this.minError = minError;
        this.offset = offset;
    }
    @Override
    public String toString() {
        return "WeakClassifier [feature=" + this.feature + ", result1="
                + this.result1 + ", result2=" + this.result2 + ", value="
                + this.value + ", minError=" + this.minError + ", offset="
                + this.offset + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
       return super.clone() ;
    }

}
