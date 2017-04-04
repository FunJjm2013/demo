package studio.jjm.opencv.demo;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class MatDemo extends LoadStaticLibrary{

   
    public static void main(String[] args) {
    	MatDemo matDemo = new MatDemo() ;
       // new MatDemo().loadImage("F:\\mit_orl_yale_database\\mit_database\\f1\\f (1).bmp") ;
      //  new MatDemo().integralTest() ;
    	//matDemo.testCreateMatMethod();
    	matDemo.createMat();
    }
    public void createMat(){
        Mat mat = new Mat(10, 10, CvType.CV_16S) ;  
        Mat mat2 = new Mat(10, 10, CvType.CV_16SC3);
        printInfo(mat);
        System.out.println(Range.all());
        System.out.println(Scalar.all(10));
        Core.randn(mat, 9, 1) ;       
        printInfo(mat);
        Core.randu(mat2, 0, 10) ;

        printInfo(mat2);
    }
    
    public void loadImage(String filename){
        Mat mat = null ;
        mat = Highgui.imread(filename) ;
        Mat mat2 = mat;
        System.out.println(mat.channels());
        System.out.println("=========================");
        System.out.println(mat2.channels());
    }
    
    public void integralTest(){
        Mat src , sum, sqsum, tilted ;
        
        src = Highgui.imread("F:\\f (1).bmp") ;
        sum = new Mat(src.size(), src.type()) ;
        sqsum = new Mat(src.size(), src.type()) ;
        tilted = new Mat(src.size(), src.type()) ;
        
        Imgproc.integral3(src, sum, sqsum, tilted) ;
        System.out.println(src.dump());
        System.out.println("=================================");
        System.out.println(sum.dump());
        System.out.println("=================================");
        System.out.println(sqsum.dump());
        System.out.println("=================================");
        System.out.println(tilted.dump());
    }
    
    public void testCreateMatMethod(){
        Mat mat = new Mat(400, 400, CvType.CV_16S) ; 
/*        Mat mat2 = new Mat() ;
        mat.copyTo(mat2);
        printInfo(mat) ;
*/        Mat mat3 = Mat.ones(400, 400, CvType.CV_16SC1) ;
  //      MatDemo.printInfo(mat3);
        Mat mat4 = Mat.eye(400, 400, CvType.CV_16SC1) ;
  //      printInfo(mat4);
        Mat mat5 = Mat.zeros(400, 400, CvType.CV_16SC1);
       // printInfo(mat5);
        Core.randn(mat5, 5, 20);
       // printInfo(mat5);
/*        Highgui.imwrite("F:\\image4.jpg", mat5);
        Highgui.imwrite("F:\\image1.jpg", mat);
        Highgui.imwrite("F:\\image3.jpg", mat3);
        Highgui.imwrite("F:\\image4.jpg", mat4);
*/    }
    
    public static void printInfo(Mat mat){
    	System.out.println("type = "+mat.type());
    	System.out.println("dataAddr = "+mat.dataAddr());
    	System.out.println("NativeObjAddr = "+mat.getNativeObjAddr());
    	System.out.println("cols = "+mat.cols());
    	System.out.println("rows = " +mat.rows());
    	System.out.println("width = "+mat.width());
    	System.out.println("height = "+mat.height());
    	System.out.println("size = "+mat.size());
    	System.out.println("channels = "+mat.channels());
    	System.out.println("depth = " +mat.depth());
    	System.out.println("total = "+mat.total());
    	System.out.println("dump = " + mat.dump());


    }
}
