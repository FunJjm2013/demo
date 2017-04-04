package studio.jjm.facedetect.adaboost;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import studio.jjm.facedetect.gray.GrayMap;
import studio.jjm.facedetect.haar.FeatureTemplate;
import studio.jjm.facedetect.haar.FeatureTool;
import studio.jjm.facedetect.haar.FeatureValue;
import studio.jjm.facedetect.integral.IntegralTool;
import studio.jjm.facedetect.integral.Integrogram;
import studio.jjm.facedetect.util.ImageFileFilter;
import studio.jjm.facedetect.util.ImageTool;
/**
 *	训练工具类
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class TrainningTool {

    
    
    /**
     *  初始化样本
     * 	@param	sampleFile	样本文件夹
     * 	@param	type	样本类型
     * 	@param	offset		样本文件偏移量
     *	@param	sampleDataFile	样本文件数据存储文件
     */
    public static void initSamples(File sampleFile, byte type, int offset, File sampleDataFile){
        if (sampleFile == null || sampleDataFile == null) {
            return ;
        }
        if (sampleFile.isDirectory()) {
            File[] sampleFiles = sampleFile
                    .listFiles(new ImageFileFilter());
            ArrayList<Sample> samples = new ArrayList<Sample>(sampleFiles.length);
            float denominator = sampleFiles.length << 1;
            for (int i = 0; i < sampleFiles.length; i++) {
                samples.add(new Sample(i+offset, type, 1.0 / denominator)) ;
            }
            try {
                serialize(samples, sampleDataFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    
    /**
     *  初始化样本的灰度图
     * 
     */
    public static void initGrayImage(File sampleFile, int offset,
            File grayDataFile) {
        if (sampleFile.isDirectory()) {
            File[] sampleFiles = sampleFile
                    .listFiles(new ImageFileFilter());
            ArrayList<GrayMap> grayMaps = new ArrayList<GrayMap>(
                    sampleFiles.length);
            GrayMap grayMap;
            BufferedImage bufferedImage;
            for (int i = 0; i < sampleFiles.length; i++) {
                bufferedImage = ImageTool.getSourceImage(sampleFiles[i]);
                grayMap = new GrayMap(i + offset, bufferedImage.getWidth(),
                        bufferedImage.getHeight(),
                        ImageTool.getGrayValues(bufferedImage));
                grayMaps.add(grayMap);
            }
            try {
                serialize(grayMaps, grayDataFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    
    /**
     * 
     * 
     */
    public static void initIntegralImage(File grayDataFile, File integralImageDataFile) {
        
        if (grayDataFile == null || integralImageDataFile == null) {
            return ;
        }
        ArrayList<GrayMap> grayMaps = null;
        try {
            grayMaps = (ArrayList<GrayMap>) deserialize(grayDataFile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (grayMaps != null) {
            ArrayList<Integrogram> integrograms = new ArrayList<Integrogram>(
                    grayMaps.size());
            GrayMap grayMap = null;
            int width = 0;
            int height = 0;
            int[][] integrogram = null;
            for (int i = 0; i < grayMaps.size(); i++) {
                grayMap = grayMaps.get(i);
                width = grayMap.getWidth();
                height = grayMap.getHeight();
                integrogram = new int[height][width];
                IntegralTool.integralImage(grayMap.grays, integrogram) ;
                integrograms.add(new Integrogram(grayMap.getId(), integrogram));
            }
            try {
                serialize(integrograms, integralImageDataFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void initFeatureTemplates(File featureTemplateFile){
        ArrayList<FeatureTemplate> featureTemplates = new ArrayList<FeatureTemplate>(94000);
        ArrayList<FeatureTemplate> subFeatureTemplates = null ;
        int count = 0 ;
        Point[] points = {new Point(2, 1),new Point(1, 2),new Point(3, 1),
                new Point(1, 3),new Point(4, 1), new Point(1, 4),new Point(3, 3)} ;
   
        for (int i = 1; i <= points.length; i++) {
            count = FeatureTool.featureCount(20, 20, points[i-1].x, points[i-1].y, i) ;
            System.out.println("type = " + i + ", count = " + count);
            subFeatureTemplates = new ArrayList<FeatureTemplate>(count) ;
            for (int j = 0; j < count; j++) {
                subFeatureTemplates.add(new FeatureTemplate()) ;
            }
            FeatureTool.featureTemplates(20, 20, points[i-1].x, points[i-1].y, i, subFeatureTemplates) ;
            featureTemplates.addAll(subFeatureTemplates) ;
        }
        
        try {
            serialize(featureTemplates, featureTemplateFile) ;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     }
    
    // 序列化
    public static void serialize(Object object, File file) throws IOException {

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.flush();
        oos.close();
    }

    // 解序列化
    public static Object deserialize(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        ois.close();
        return object;
    }

    public static void operate1(){
        File posSamplesFile = new File("F:\\training_images\\MIT\\faces");
        File negSamplesFile = new File("F:\\training_images\\MIT\\nonfaces");
        File posSamplesDataFile = new File("F:\\训练数据\\posSamplesData.txt");
        File negSamplesDataFile = new File("F:\\训练数据\\negSamplesData.txt");
        File samplesDataFile = new File("F:\\训练数据\\samplesData.txt");
        byte type = 1;
        // 初始化样本
        initSamples(posSamplesFile, type, 1, posSamplesDataFile) ;
        type = -1;
        initSamples(negSamplesFile, type, 2466, negSamplesDataFile) ;
        try {
            ArrayList<Sample> posSamples = (ArrayList<Sample>) deserialize(posSamplesDataFile);
            ArrayList<Sample> negSamples = (ArrayList<Sample>) deserialize(negSamplesDataFile);
            System.out.println("posSampleSize = " + posSamples.size()
                    + "\t negSampleSize = " + negSamples.size());
            System.out.println(posSamples.get(10).id + "\t"
                    + negSamples.get(200).id);
            ArrayList<Sample> samples = new ArrayList<Sample>(posSamples.size()
                    + negSamples.size());
            samples.addAll(posSamples);
            samples.addAll(negSamples);
            double totalWei = 0.0 ;
            for (int i = 0; i < samples.size(); i++) {
                totalWei += samples.get(i).weight;
            }
            // 权重归一化
            for (int i = 0; i < samples.size(); i++) {
                samples.get(i).weight /= totalWei;
            }

            serialize(samples, samplesDataFile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public static void operate2(){
        // 初始化样本灰度图
        File posSamplesFile = new File("F:\\training_images\\MIT\\faces");
        File negSamplesFile = new File("F:\\training_images\\MIT\\nonfaces");
        File posGrayDataFile = new File("F:\\训练数据\\posGrayDataFile.txt");
        File negGrayDataFile = new File("F:\\训练数据\\negGrayDataFile.txt");
        File grayDataFile = new File("F:\\训练数据\\grayDataFile.txt");
        initGrayImage(posSamplesFile, 1, posGrayDataFile);
        initGrayImage(negSamplesFile, 2466, negGrayDataFile);
        try {
            ArrayList<GrayMap> posGrayMaps = (ArrayList<GrayMap>) deserialize(posGrayDataFile);
            ArrayList<GrayMap> negGrayMaps = (ArrayList<GrayMap>) deserialize(negGrayDataFile);
            System.out.println("posSampleSize = " + posGrayMaps.size()
                    + "\t negSampleSize = " + negGrayMaps.size());
            System.out.println(posGrayMaps.get(10).getId() + "\t"
                    + negGrayMaps.get(200).getId());
            System.out.println(posGrayMaps.get(22));
            ArrayList<GrayMap> grayMaps = new ArrayList<GrayMap>(
                    posGrayMaps.size() + negGrayMaps.size());
            grayMaps.addAll(posGrayMaps);
            grayMaps.addAll(negGrayMaps);
            serialize(grayMaps, grayDataFile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public static void operate3(){
        File integralImageFile = new File("F:\\训练数据\\integralImageFile.txt");
        File rotatedIntegralImageFile = new File(
                "F:\\训练数据\\rotatedIntegralImageFile.txt");
        File grayDataFile = new File("F:\\训练数据\\grayDataFile.txt");

        initIntegralImage(grayDataFile, integralImageFile);
        ArrayList<Integrogram> integrograms;
        try {
            integrograms = (ArrayList<Integrogram>) deserialize(integralImageFile);
            Integrogram integrogram = integrograms.get(11);
            for (int i = 0; i < 5; i++) {
                System.out.print(integrogram.integrogram[i][i] + "\t");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public static void operate4(){
        File integralImageFile = new File("F:\\训练数据\\integralImageFile.txt");
        File samplesDataFile = new File("F:\\训练数据\\samplesData.txt");
        File featureTemplatesFile = new File("F:\\训练数据\\featureTemplates.txt") ;
        File strongClassifierFile = new File("F:\\训练数据\\strongClassifier.txt") ;
        File weakClassifiersFile = new File("F:\\训练数据\\weakClassifiers.txt") ;
        ArrayList<Integrogram> integralImage = null ;
        ArrayList<WeakClassifier> weakClassifiers = null ;
        StrongClassifier strongClassifier = null ;
        ArrayList<FeatureValue> values = null ;
        ArrayList<Sample> samples = null ;
        ArrayList<FeatureTemplate> features = null ;
        
        try {
            samples = (ArrayList<Sample>) deserialize(samplesDataFile) ;
            integralImage = (ArrayList<Integrogram>) deserialize(integralImageFile) ;
            features = (ArrayList<FeatureTemplate>) deserialize(featureTemplatesFile) ;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        weakClassifiers = new ArrayList<WeakClassifier>(features.size()) ;
        
     //   weakClassifiers = new ArrayList<WeakClassifier>(features.size()) ;
        
        values = new ArrayList<FeatureValue>(samples.size()) ;
        for (int i = 0; i < samples.size(); i++) {
            values.add(new FeatureValue(i+1, 0)) ;
        }

       // Adaboost.weakClassifiers(weakClassifiers, features, values, samples, integralImage) ;
        strongClassifier = AdaboostFast.adaboost(0.95, 0.05, samples, features, integralImage) ;
      // weakClassifiers = Adaboost.adaboost1(samples, features, integralImage, 5) ;
        System.out.println(strongClassifier);
        try {
           // serialize(weakClassifiers, weakClassifiersFile); 
            serialize(strongClassifier, strongClassifierFile) ;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
  /*      operate1() ;
      operate2() ;
       operate3() ;
       initFeatureTemplates(new File("F:\\训练数据\\featureTemplates.txt")) ;
    
        ArrayList<FeatureTemplate> featureTemplates = null;
        try {
            featureTemplates = (ArrayList<FeatureTemplate>) deserialize(new File("F:\\训练数据\\featureTemplates.txt")) ;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/
        operate4() ;
    }
    
}
