package studio.jjm.facedetect.haar;

import java.io.Serializable;
/**
 *	��������ֵ
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class FeatureValue implements Comparable<FeatureValue>, Serializable {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public int             id;                   // �����ı��
    public int             value;                // ����ֵ
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
