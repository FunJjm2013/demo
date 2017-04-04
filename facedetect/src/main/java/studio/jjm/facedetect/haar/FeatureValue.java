package studio.jjm.facedetect.haar;

import java.io.Serializable;
/**
 *	矩形特征值
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class FeatureValue implements Comparable<FeatureValue>, Serializable {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public int             id;                   // 样本的编号
    public int             value;                // 特征值
    public FeatureValue() {
        // TODO Auto-generated constructor stub
    }
    public FeatureValue(int id, int value) {
        super();
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Feature [id=" + this.id + ", value=" + this.value + "]";
    }
    public int compareTo(FeatureValue o) {
        // TODO Auto-generated method stub
        if (this.value < o.value) {
            return -1;
        } else if (this.value > o.value) {
            return 1;
        } else {
            return 0;
        }
    }

}
