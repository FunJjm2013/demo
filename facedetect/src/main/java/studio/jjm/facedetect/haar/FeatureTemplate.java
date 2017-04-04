package studio.jjm.facedetect.haar;

import java.io.Serializable;

/**
 *	����������
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class FeatureTemplate implements Serializable, Cloneable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public  int             type;                 // ��������
    public  int             x;                    // �����������Ͻǵ�x����
    public  int             y;                    // �����������Ͻǵ�y����
    public  int             w;                    // ���������Ŀ�
    public  int             h;                    // ���������ĸ�
    public FeatureTemplate() {
        // TODO Auto-generated constructor stub
    }
    
    public FeatureTemplate(int type, int x, int y, int w, int h) {
        super();
        this.type = type;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public String toString() {
        return "FeatureTemplate [type=" + this.type
                + ", x=" + this.x + ", y=" + this.y + ", w=" + this.w + ", h="
                + this.h + "]";
    }
    
    @Override
    public Object clone()throws CloneNotSupportedException{
        return super.clone() ;
    }
}
