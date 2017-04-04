package studio.jjm.facedetect.haar;

import java.io.Serializable;

/**
 *	矩形特征类
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class FeatureTemplate implements Serializable, Cloneable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public  int             type;                 // 特征类型
    public  int             x;                    // 矩形特征左上角点x坐标
    public  int             y;                    // 矩形特征左上角点y坐标
    public  int             w;                    // 矩形特征的宽
    public  int             h;                    // 矩形特征的高
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
