package studio.jjm.facedetect.gray;

import java.io.Serializable;
import java.util.Arrays;
/**
 *	�Ҷ� ͼ
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class GrayMap implements Serializable, Cloneable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public int id ;		//ͼid
    public int width ;	//�Ҷ�ͼ�Ŀ��
    public int height ;	//�Ҷ�ͼ�ĸ߶�
    public int[][] grays ;	//�Ҷ�ͼ����
    public GrayMap() {
        // TODO Auto-generated constructor stub
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getWidth() {
        return this.width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int[][] getGrays() {
        return this.grays;
    }
    public void setGrays(int[][] grays) {
        this.grays = grays;
    }
    public GrayMap(int id, int width, int height, int[][] grays) {
        super();
        this.id = id ;
        this.width = width;
        this.height = height;
        this.grays = grays;
    }
    public GrayMap(int id, int[][] grays){
        if (grays != null) {
            this.id = id ;
            this.width = grays[0].length ;
            this.height = grays.length ;           
        }
    }
    @Override
    public String toString() {
        return "GrayMap [id="+id+", width=" + this.width + ", height=" + this.height
                + ", grays=" + Arrays.toString(this.grays) + "]";
    }    
   
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone() ;
    }

}
