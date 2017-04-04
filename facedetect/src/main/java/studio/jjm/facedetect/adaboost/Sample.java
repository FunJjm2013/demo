package studio.jjm.facedetect.adaboost;

import java.io.Serializable;
/**
 *	������
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class Sample implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected int id ;          //������ţ�Ψһ
    protected byte type ;        //�������1������������-1��������
    protected byte result ;      //����ʶ��true����������false������
    protected double weight ;    //������Ȩ��
    public Sample() {
        // TODO Auto-generated constructor stub
        this.id = 0 ;
        this.type = -1 ;
        this.result = -1 ;
        this.weight = 0.0 ;
    }
    public Sample(int id, byte type, double weight) {
        super();
        this.id = id;
        this.type = type;
        this.weight = weight;
        this.result = -1 ;
    }
    public Sample(int id, byte type, byte result, double weight) {
        super();
        this.id = id;
        this.type = type;
        this.result = result;
        this.weight = weight;
    }
    @Override
    public String toString() {
        return "Sample [id=" + this.id + ", type=" + this.type + ", result="
                + this.result + ", weight=" + this.weight + "]";
    } 
    
}
