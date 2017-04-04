package studio.jjm.facedetect.adaboost;

import java.io.Serializable;
/**
 *	样本类
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class Sample implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected int id ;          //样本编号，唯一
    protected byte type ;        //样本类别：1代表正样本，-1代表负样本
    protected byte result ;      //类别标识：true代表正例，false代表反例
    protected double weight ;    //样本的权重
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
