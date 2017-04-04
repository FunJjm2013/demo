package studio.jjm.facedetect.integral;

import java.io.Serializable;
import java.util.Arrays;
/**
 *	����ͼ
 *
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class Integrogram implements Serializable, Cloneable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public int id ;      //�����ı��
    public int[][] integrogram ; //�����Ļ���ͼ
    public Integrogram() {
        // TODO Auto-generated constructor stub
        this.id = 0 ;
        this.integrogram = new int[1][1] ;
    }
    
    public Integrogram(int id, int[][] integrogram) {
        super();
        if (integrogram != null) {
            this.id = id;
            this.integrogram = integrogram;            
        }else {
            throw new NullPointerException("����ͼΪ�գ�") ;
        }
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.id;
        result = prime * result + Arrays.hashCode(this.integrogram);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Integrogram other = (Integrogram) obj;
        if (this.id != other.id)
            return false;
        if (!Arrays.equals(this.integrogram, other.integrogram))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Integrogram [id=" + this.id + ", integrogram="
                + Arrays.toString(this.integrogram) + "]";
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone() ;
    }
}
