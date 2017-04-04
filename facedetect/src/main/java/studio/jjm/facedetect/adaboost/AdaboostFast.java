package studio.jjm.facedetect.adaboost;

import java.util.ArrayList;
import java.util.Arrays;

import studio.jjm.facedetect.haar.FeatureTemplate;
import studio.jjm.facedetect.haar.FeatureTool;
import studio.jjm.facedetect.haar.FeatureValue;
import studio.jjm.facedetect.integral.Integrogram;
/**
 *	Adaboost算法及相关属性方法类
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class AdaboostFast {

    /**
     * 求得所有特征的弱分类器
     * 
     * @param weakClassifiers        系列弱分类器
     * @param features       所有特征值
     * @param values     辅助特征值
     * @param samples    样本集
     * @param integralImage 积分图
     * 
     */
    public static void weakClassifiers(
            ArrayList<WeakClassifier> weakClassifiers,
            ArrayList<FeatureTemplate> features,
            ArrayList<FeatureValue> values, ArrayList<Sample> samples,
            ArrayList<Integrogram> integralImage) {
        FeatureTemplate feature = null;
        double curlerror = Double.MAX_VALUE, currerror = Double.MAX_VALUE, minerror = Double.MAX_VALUE;
        double wl = 0.0, wr = 0.0, wyl = 0.0, wyr = 0.0, curleft = 0.0, curright = 0.0, w = 0.0, wy = 0.0;
        int samplesCount = samples.size(), index = 0, index1 = 0, k = 0;
        for (int j = 0; j < features.size(); j++) {
            long time = System.currentTimeMillis() ;
            WeakClassifier weakClassifier = new WeakClassifier();
            curlerror = currerror = wl = wr = wyl = wyr = curleft = curright = w = wy = 0.0;
            k = 0;
            minerror = Double.MAX_VALUE;
            feature = features.get(j);
            FeatureTool.sortFeatureValue(feature, integralImage, values);

            
            for (int i = 0; i < samplesCount; i++) {
                index = values.get(i).id - 1;
                w += samples.get(index).weight;
                wy += samples.get(index).weight * samples.get(index).type;
            }

            for (int i = 0; i < samplesCount; i++) {
                //
                index = values.get(i).id - 1;
                wl += samples.get(index).weight;
                wr = w - wl;

                wyl += samples.get(index).weight * samples.get(index).type;
                wyr = wy - wyl;

                curleft = wyl / wl;
                curright = wyr / wr;

                curlerror += samples.get(index).weight
                        * Math.pow(samples.get(index).type - curleft, 2);

                currerror = 0.0;
                k = i + 1;
                while (k < samplesCount) {
                    index1 = samples.get(k).id - 1;
                    currerror += samples.get(index1).weight
                            * Math.pow(samples.get(index1).type - curright, 2);
                    k++ ;
                }

                if (curlerror + currerror < minerror) {
                    minerror = curlerror + currerror;
                    weakClassifier.minError = minerror;
                    weakClassifier.value = values.get(i).value;
                    weakClassifier.result1 = curleft;
                    weakClassifier.result2 = curright;
                    try {
                        weakClassifier.feature = (FeatureTemplate) feature
                                .clone();
                    } catch (CloneNotSupportedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(System.currentTimeMillis()-time);         
            System.out.println("feature -> " + j +"\t"+weakClassifier);
            // 添加
            try {
                weakClassifiers.add((WeakClassifier) weakClassifier.clone());
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * 求出具有最小错误率弱分类器
     * 
     * @param weakClassifier        最小错误率弱分类器
     * @param features              所有特征值
     * @param values     辅助特征值
     * @param samples    样本集
     * @param integralImage 积分图
     * 
     */

    public static void bestWeakClassifier(WeakClassifier weakClassifier,
            ArrayList<FeatureTemplate> features,
            ArrayList<FeatureValue> values, ArrayList<Sample> samples,
            ArrayList<Integrogram> integralImage) {
        FeatureTemplate feature = null;
        double curlerror = Double.MAX_VALUE, currerror = Double.MAX_VALUE, minerror = Double.MAX_VALUE;
        double wl = 0.0, wr = 0.0, wyl = 0.0, wyr = 0.0, curleft = 0.0, curright = 0.0, w = 0.0, wy = 0.0;
        int samplesCount = samples.size(), index = 0, index1 = 0, k = 0;
        for (int j = 0; j < features.size(); j++) {
            curlerror = currerror = wl = wr = wyl = wyr = curleft = curright = w = wy = 0.0;
            k = 0;
            feature = features.get(j);
            FeatureTool.sortFeatureValue(feature, integralImage,values);

            for (int i = 0; i < samplesCount; i++) {
                index = values.get(i).id - 1;
                w += samples.get(index).weight;
                wy += samples.get(index).weight * samples.get(index).type;
            }

            for (int i = 0; i < samplesCount; i++) {
                //
                index = values.get(i).id - 1;
                wl += samples.get(index).weight;
                wr = w - wl;

                wyl += samples.get(index).weight * samples.get(index).type;
                wyr = wy - wyl;

                curleft = wyl / wl;
                curright = wyr / wr;

                curlerror += samples.get(index).weight
                        * Math.pow(samples.get(index).type - curleft, 2);

                currerror = 0.0;
                k = i + 1;
                while (k < samplesCount) {
                    index1 = samples.get(k).id - 1;
                    currerror += samples.get(index1).weight
                            * Math.pow(samples.get(index1).type - curright, 2);
                    k++ ;
                }

                if (curlerror + currerror < minerror) {
                    minerror = curlerror + currerror;
                    weakClassifier.minError = minerror;
                    weakClassifier.value = values.get(i).value;
                    weakClassifier.result1 = curleft;
                    weakClassifier.result2 = curright;
                    try {
                        weakClassifier.feature = (FeatureTemplate) feature
                                .clone();
                    } catch (CloneNotSupportedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     *  求得具有最小分类错误的弱分类器
     *  
     *  @param  weakClassifiers      各个特征的弱分类器
     *  @param  samples              样本集
     *  @param  integralImages       积分图
     *  @param  rotatedIntegralImages   旋转积分图
     *  
     *  @return 具有最小分类错误的弱分类器
     * 
     */

    public static WeakClassifier minClassifierError(
            ArrayList<WeakClassifier> weakClassifiers,
            ArrayList<Sample> samples, ArrayList<Integrogram> integralImages) {
        WeakClassifier bestWeakClassifier = null;
        WeakClassifier weakClassifier = null;
        double minClassifierError = Double.MAX_VALUE;
        double value = 0.0;
        for (int i = 0; i < weakClassifiers.size(); i++) {
            System.out.println(i);
            weakClassifier = weakClassifiers.get(i);
            double error = 0.0;
            for (int j = 0; j < samples.size(); j++) {
                value = FeatureTool.featureValue(weakClassifier.feature,
                        integralImages.get(j).integrogram);
                if (value < weakClassifier.value) {
                    error += samples.get(j).weight
                            * Math.pow(weakClassifier.result1
                                    - samples.get(j).result, 2);
                } else {
                    error += samples.get(j).weight
                            * Math.pow(weakClassifier.result2
                                    - samples.get(j).result, 2);
                }
            }
            if (error < minClassifierError) {
                minClassifierError = error;
                try {
                    bestWeakClassifier = (WeakClassifier) weakClassifier
                            .clone();
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return bestWeakClassifier;
    }

    // 装载积分图，装载特征点，现用，现取特征
    public static StrongClassifier adaboost(double detectMin, double faultMax,
            ArrayList<Sample> samples, ArrayList<FeatureTemplate> features,
            ArrayList<Integrogram> integralImage) {
        int sampleCount = samples.size(); // 样本个数
        int featureCount = features.size();
        int posCount = 0, negCount = 0;
        double f = 1.0, threshold = 0.0;
        StrongClassifier strongClassifier = new StrongClassifier();

        WeakClassifier bestWeakClassifier = null;
        ArrayList<WeakClassifier> weakClassifiers1 = new ArrayList<WeakClassifier>(
                featureCount);  // 每一种特征训练得到的弱分类器
        ArrayList<FeatureValue> featureValues = new ArrayList<FeatureValue>(
                sampleCount);
        int i = 0;

        for (i = 0; i < featureCount; i++) {
            weakClassifiers1.add(new WeakClassifier());
        }
        for (i = 0; i < sampleCount; i++) {
            featureValues.add(new FeatureValue(i + 1, 0));
        }
        for (i = 0; i < sampleCount; i++) { // 统计正样本和负样本的数目
            if (samples.get(i).type == 1) {
                posCount++;
            } else {
                negCount++;
            }
        }

        // 初始化权重
        for (i = 0; i < sampleCount; i++) {
            if (samples.get(i).type == 1) {
                samples.get(i).weight = 1.0 / posCount;
            } else {
                samples.get(i).weight = 1.0 / negCount;
            }
        }

        while (f > faultMax) {

            double totalWei = 0.0;
            double[] pv = new double[posCount];
            double[] nv = new double[negCount];

            for (i = 0; i < sampleCount; i++) {
                totalWei += samples.get(i).weight;
            }
            // 权重归一化
            for (i = 0; i < sampleCount; i++) {
                samples.get(i).weight /= totalWei;
            }

            weakClassifiers(weakClassifiers1, features, featureValues,
                    samples, integralImage);
            bestWeakClassifier = minClassifierError(weakClassifiers1,
                    samples, integralImage);

            System.out.println(bestWeakClassifier);
            strongClassifier.weakClassifiers.add(bestWeakClassifier);
            double value = 0.0;
            WeakClassifier weakClassifier = null;
            int c = 0 ;
            for (i = 0; i < sampleCount; i++) {
                if (samples.get(i).type == 1) {
                    for (int j = 0; j < strongClassifier.weakClassifiers.size(); j++) {
                        weakClassifier = strongClassifier.weakClassifiers
                                .get(j);
                        value = FeatureTool.featureValue(
                                weakClassifier.feature,
                                integralImage.get(i).integrogram);
                        if (value < weakClassifier.value) {
                            pv[i] += weakClassifier.result1;
                        } else {
                            pv[i] += weakClassifier.result2;
                        }
                    }
                } else {
                    for (int j = 0; j < strongClassifier.weakClassifiers.size(); j++) {
                        weakClassifier = strongClassifier.weakClassifiers
                                .get(j);
                        value = FeatureTool.featureValue(
                                weakClassifier.feature,
                                integralImage.get(i).integrogram);
                        if (value < weakClassifier.value) {
                            nv[c++] += weakClassifier.result1;
                        } else {
                            nv[c++] += weakClassifier.result2;
                        }
                    }
                }
            }

            Arrays.sort(pv);
            System.out.println();
            for (int j = 0; j < pv.length; j++) {
                System.out.print(pv[j] + " ");
            }

            threshold = pv[(int) Math.floor(posCount * (1 - detectMin))];

            int numfalse = 0;

            for (i = 0; i < negCount; i++) {
                if (nv[i] >= threshold) {
                    numfalse++;
                }
            }
            f = numfalse / negCount;

            for (i = 0; i < sampleCount; i++) {
                value = FeatureTool.featureValue(bestWeakClassifier.feature,
                        integralImage.get(i).integrogram);
                if (value < bestWeakClassifier.value) {
                    samples.get(i).weight *= Math
                            .log(-bestWeakClassifier.result1
                                    * samples.get(i).type);
                }
            }

        }

        strongClassifier.threshold = threshold;

        return strongClassifier;

    }

    public static double calculateDetectRate(
            ArrayList<WeakClassifier> weakClassifiers,
            ArrayList<Integrogram> integralImage,
            ArrayList<Integrogram> rotatedIntegralImage,
            ArrayList<Sample> posSamples) {
        int sampleCount = posSamples.size();
        int weakClassifierCount = weakClassifiers.size();
        int detectCount = 0;
        boolean isDetected = true;
        for (int i = 0; i < sampleCount; i++) {

            isDetected = true;
            for (int j = 0; j < weakClassifierCount; j++) {
                int value = FeatureTool.featureValue(
                        weakClassifiers.get(j).feature,
                        integralImage.get(i).integrogram);
                if (value > weakClassifiers.get(j).value) {
                    isDetected = false;
                }
            }

            if (isDetected) {
                detectCount++;
            }
        }

        return detectCount * 1.0 / sampleCount;
    }

    public static double calculateErrorRate(
            ArrayList<WeakClassifier> weakClassifiers,
            ArrayList<Integrogram> integralImage,
            ArrayList<Integrogram> rotatedIntegralImage,
            ArrayList<Sample> errorSamples, ArrayList<Sample> negSamples) {
        int sampleCount = negSamples.size();
        int weakClassifierCount = weakClassifiers.size();
        int detectCount = 0;
        boolean isDetected = false;
        for (int i = 0; i < sampleCount; i++) {

            isDetected = false;
            for (int j = 0; j < weakClassifierCount; j++) {
                int value = FeatureTool.featureValue(
                        weakClassifiers.get(j).feature,
                        integralImage.get(i).integrogram);
                if (value > weakClassifiers.get(j).value) {
                    isDetected = true;
                }
            }

            if (isDetected) {
                detectCount++;
            }
        }

        return (sampleCount - detectCount) * 1.0 / sampleCount;

    }

    public static void cascadeClassifier(float maxErrorRate,
            float minDetectRate, float errorRate, ArrayList<Sample> posSamples,
            ArrayList<Sample> negSamples,
            ArrayList<FeatureTemplate> fTemplates,
            ArrayList<Integrogram> integralImage,
            ArrayList<Integrogram> rotatedIntegralImage,
            ArrayList<WeakClassifier> weakClassifiers) {
        
        
    }

    public static WeakClassifier trainningWeakClassifier(
            ArrayList<FeatureTemplate> features,
            ArrayList<FeatureValue> values, ArrayList<Sample> samples,
            ArrayList<Integrogram> integralImage,
            ArrayList<Integrogram> rotatedIntegralImage) {
        WeakClassifier weakClassifier = new WeakClassifier();
        FeatureTemplate feature = null;
        for (int i = 0; i < features.size(); i++) {
            feature = features.get(i);
            FeatureTool.sortFeatureValue(feature, integralImage, values);
            singleFeatureClassifier(weakClassifier, feature, values, samples);
        }

        return weakClassifier;
    }

    public static void singleFeatureClassifier(WeakClassifier weakClassifier,
            FeatureTemplate feature, ArrayList<FeatureValue> values,
            ArrayList<Sample> samples) {
        double curlerror = Double.MAX_VALUE, currerror = Double.MAX_VALUE, minerror = Double.MAX_VALUE;
        double wl = 0.0, wr = 0.0, wyl = 0.0, wyr = 0.0, curleft = 0.0, curright = 0.0, w = 0.0, wy = 0.0;
        int samplesCount = samples.size(), index = 0, index1 = 0, k = 0;

        for (int i = 0; i < samplesCount; i++) {
            index = values.get(i).id - 1;
            w += samples.get(index).weight;
            wy += samples.get(index).weight * samples.get(index).type;
        }

        for (int i = 0; i < samplesCount; i++) {
            //
            index = values.get(i).id - 1;
            wl += samples.get(index).weight;
            wr = w - wl;

            wyl += samples.get(index).weight * samples.get(index).type;
            wyr = wy - wyl;

            curleft = wyl / wl;
            curright = wyr / wr;

            curlerror += samples.get(index).weight
                    * Math.pow(samples.get(index).type - curleft, 2);

            currerror = 0.0;
            k = i + 1;
            while (k < samplesCount) {
                index1 = samples.get(k).id - 1;
                currerror += samples.get(index1).weight
                        * Math.pow(samples.get(index1).type - curright, 2);
            }

            if (curlerror + currerror < minerror) {
                weakClassifier.minError = curlerror + currerror;
                weakClassifier.value = values.get(i).value;
                weakClassifier.result1 = curleft;
                weakClassifier.result2 = curright;
                try {
                    weakClassifier.feature = (FeatureTemplate) feature.clone();
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    

    /**
     *  @param features 所有特征点 
     */
    public static ArrayList<WeakClassifier> adaboost1(
            ArrayList<Sample> samples, ArrayList<FeatureTemplate> fTemplates,
            ArrayList<Integrogram> integralImage ,int iterCount) {
        int sampleCount = samples.size(); // 样本个数
        int featureCount = fTemplates.size();
        int posCount = 0, negCount = 0;
        ArrayList<WeakClassifier> weakClassifiers = new ArrayList<WeakClassifier>(
                iterCount);     // 每一轮训练得到的的弱分类器
        ArrayList<WeakClassifier> weakClassifiers1 = new ArrayList<WeakClassifier>(
                featureCount);  // 每一种特征训练得到的弱分类器
        ArrayList<FeatureValue> features = new ArrayList<FeatureValue>(sampleCount);
        int i = 0;
        for (i = 0; i < iterCount; i++) {
            weakClassifiers.add(new WeakClassifier());
        }
        for (i = 0; i < featureCount; i++) {
            weakClassifiers1.add(new WeakClassifier());
        }
        for (i = 0; i < sampleCount; i++) {
            features.add(new FeatureValue(i + 1, 0));
        }
        for (i = 0; i < sampleCount; i++) { // 统计正样本和负样本的数目
            if (samples.get(i).type == 1) {
                posCount++;
            } else {
                negCount++;
            }
        }

        // 初始化权重
        int denominator1 = posCount << 1;
        int denominator2 = negCount << 1;
        for (i = 0; i < sampleCount; i++) {
            if (samples.get(i).type==1) {
                samples.get(i).weight = 1.0f / denominator1;
            } else {
                samples.get(i).weight = 1.0f / denominator2;
            }
        }

        int k = 0;

        // 训练样本
        for (int t = 0; t < iterCount; t++) {
            // （当前样本之前的）正负样本权值和
            float totalWeight = 0.0f, totalPosWei = 0.0f, totalNegWei = 0.0f, curTotPosW = 0.0f, curTotNegW = 0.0f;
            float e = 1.0f, threshold = 0.0f, classifyErr = 1.0f;     // 训练单个分类器时用到的错误率，阈值，分类误差
            float minErr = 1.0f; // 所有特征的最小错误率
            float bestPosWeiThreshold = 0.0f, bestNegWeiThreshold = 0.0f;

            for (i = 0; i < sampleCount; i++) {
                totalWeight += samples.get(i).weight;
                if (samples.get(i).type==1) {
                    totalPosWei += samples.get(i).weight;
                } else {
                    totalNegWei += samples.get(i).weight;
                }
            }

            k = 2;
            // 权重归一化
            for (i = 0; i < sampleCount; i++) {
                samples.get(i).weight /= totalWeight;
            }

            // 逐一训练弱分类器
            for (i = 0; i < featureCount; i++) {
                e = 1.0f;
                threshold = 0.0f;
                classifyErr = 1.0f;
                /* 按特征i排序 */
                
                FeatureTool.sortFeatureValue(fTemplates.get(i), integralImage,features);
                /* 求单个弱分类器的阈值 */
                float posWeiThreshold = 0.0f;
                float negWeiThreshold = 0.0f;
                int index = 0;
                for (int j = 0; j < sampleCount; j++) {
                    index = features.get(j).id - 1;
                    if (samples.get(index).type==1) {
                        curTotPosW += samples.get(index).weight;
                    } else {
                        curTotNegW += samples.get(index).weight;
                    }
                    posWeiThreshold = curTotPosW + totalNegWei - curTotNegW;
                    negWeiThreshold = curTotNegW + totalPosWei - curTotPosW;
                    e = Math.min(posWeiThreshold, negWeiThreshold);
                    if (classifyErr > e) {
                        classifyErr = e;
                        bestPosWeiThreshold = posWeiThreshold;
                        bestNegWeiThreshold = negWeiThreshold;
                        if (j == 0) {
                            threshold = features.get(j).value - 0.5f;
                        } else {
                            if (j == sampleCount - 1) {
                                threshold = features.get(j).value + 0.5f;
                            } else {
                                threshold = (features.get(j).value + features
                                        .get(j - 1).value) / 2;
                            }
                        }
                    }
                }
                weakClassifiers1.get(i).value = threshold;
                try {
                    weakClassifiers1.get(i).feature = (FeatureTemplate) fTemplates
                            .get(i).clone();
                } catch (CloneNotSupportedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                weakClassifiers1.get(i).minError = classifyErr;
                weakClassifiers1.get(i).offset = (int) (2 * ((bestPosWeiThreshold > bestNegWeiThreshold ? 1
                        : 0) - 0.5f));

            }// end 逐一训练弱分类器
/*            System.out.println("++++++++++++++++++++");
            for (i = 0; i < weakClassifiers1.size(); i++) {
                System.out.println(weakClassifiers1.get(i));
            }
            System.out.println("++++++++++++++++++++++++");
*/
            k = 3;
            /* 从不同特征的弱分类器中找出一个具有最小错误率e的简单分类器 */
            for (i = 0; i < featureCount; i++) {
                float weightError = 0.0f;
                for (int j = 0; j < sampleCount; j++) {
                    int value = FeatureTool.featureValue(fTemplates.get(i),integralImage.get(j).integrogram);
                    if (value * weakClassifiers1.get(i).offset <= weakClassifiers1
                            .get(i).value
                            * weakClassifiers1.get(i).offset) {
                        samples.get(j).result = -1;
                    } else {
                        samples.get(j).result = -1;
                    }
                    if (samples.get(j).type != samples.get(j).result) {
                        weightError += samples.get(j).weight;  // 计算弱分类器的加权错误率
                    }
                }
                k = 4;
                if (weightError < minErr) {
                    minErr = weightError;
                    try {
                        weakClassifiers.get(t).feature = (FeatureTemplate) weakClassifiers1
                                .get(i).feature.clone();
                    } catch (CloneNotSupportedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    weakClassifiers.get(t).value = weakClassifiers1
                            .get(i).value;
                    weakClassifiers.get(t).minError = minErr;
                }
            }

            // 更新权值
            float beiTa = minErr / (1 - minErr);
            System.out.println("minErr = " + minErr);
            for (i = 0; i < sampleCount; i++) {
                if (samples.get(i).result == 1) {
                    samples.get(i).weight *= beiTa;
                }
            }

            System.out.println(weakClassifiers.get(t));
        }// end 训练样本

        return weakClassifiers;

    }

}

//WeakClassifier [feature=FeatureTemplate [type=1, x=8, y=-1, w=2, h=1], result1=0.1560253387243311, result2=-0.1383010647010489, value=0.0, minError=0.7772893381564554, offset=1]

//182657
