package studio.jjm.facedetect.adaboost;

import java.util.ArrayList;
import java.util.Arrays;

import studio.jjm.facedetect.haar.FeatureTemplate;
import studio.jjm.facedetect.haar.FeatureTool;
import studio.jjm.facedetect.haar.FeatureValue;
import studio.jjm.facedetect.integral.Integrogram;
/**
 *	Adaboost�㷨��������Է�����
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class AdaboostFast {

    /**
     * ���������������������
     * 
     * @param weakClassifiers        ϵ����������
     * @param features       ��������ֵ
     * @param values     ��������ֵ
     * @param samples    ������
     * @param integralImage ����ͼ
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
            // ���
            try {
                weakClassifiers.add((WeakClassifier) weakClassifier.clone());
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * ���������С��������������
     * 
     * @param weakClassifier        ��С��������������
     * @param features              ��������ֵ
     * @param values     ��������ֵ
     * @param samples    ������
     * @param integralImage ����ͼ
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
     *  ��þ�����С����������������
     *  
     *  @param  weakClassifiers      ������������������
     *  @param  samples              ������
     *  @param  integralImages       ����ͼ
     *  @param  rotatedIntegralImages   ��ת����ͼ
     *  
     *  @return ������С����������������
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

    // װ�ػ���ͼ��װ�������㣬���ã���ȡ����
    public static StrongClassifier adaboost(double detectMin, double faultMax,
            ArrayList<Sample> samples, ArrayList<FeatureTemplate> features,
            ArrayList<Integrogram> integralImage) {
        int sampleCount = samples.size(); // ��������
        int featureCount = features.size();
        int posCount = 0, negCount = 0;
        double f = 1.0, threshold = 0.0;
        StrongClassifier strongClassifier = new StrongClassifier();

        WeakClassifier bestWeakClassifier = null;
        ArrayList<WeakClassifier> weakClassifiers1 = new ArrayList<WeakClassifier>(
                featureCount);  // ÿһ������ѵ���õ�����������
        ArrayList<FeatureValue> featureValues = new ArrayList<FeatureValue>(
                sampleCount);
        int i = 0;

        for (i = 0; i < featureCount; i++) {
            weakClassifiers1.add(new WeakClassifier());
        }
        for (i = 0; i < sampleCount; i++) {
            featureValues.add(new FeatureValue(i + 1, 0));
        }
        for (i = 0; i < sampleCount; i++) { // ͳ���������͸���������Ŀ
            if (samples.get(i).type == 1) {
                posCount++;
            } else {
                negCount++;
            }
        }

        // ��ʼ��Ȩ��
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
            // Ȩ�ع�һ��
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
     *  @param features ���������� 
     */
    public static ArrayList<WeakClassifier> adaboost1(
            ArrayList<Sample> samples, ArrayList<FeatureTemplate> fTemplates,
            ArrayList<Integrogram> integralImage ,int iterCount) {
        int sampleCount = samples.size(); // ��������
        int featureCount = fTemplates.size();
        int posCount = 0, negCount = 0;
        ArrayList<WeakClassifier> weakClassifiers = new ArrayList<WeakClassifier>(
                iterCount);     // ÿһ��ѵ���õ��ĵ���������
        ArrayList<WeakClassifier> weakClassifiers1 = new ArrayList<WeakClassifier>(
                featureCount);  // ÿһ������ѵ���õ�����������
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
        for (i = 0; i < sampleCount; i++) { // ͳ���������͸���������Ŀ
            if (samples.get(i).type == 1) {
                posCount++;
            } else {
                negCount++;
            }
        }

        // ��ʼ��Ȩ��
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

        // ѵ������
        for (int t = 0; t < iterCount; t++) {
            // ����ǰ����֮ǰ�ģ���������Ȩֵ��
            float totalWeight = 0.0f, totalPosWei = 0.0f, totalNegWei = 0.0f, curTotPosW = 0.0f, curTotNegW = 0.0f;
            float e = 1.0f, threshold = 0.0f, classifyErr = 1.0f;     // ѵ������������ʱ�õ��Ĵ����ʣ���ֵ���������
            float minErr = 1.0f; // ������������С������
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
            // Ȩ�ع�һ��
            for (i = 0; i < sampleCount; i++) {
                samples.get(i).weight /= totalWeight;
            }

            // ��һѵ����������
            for (i = 0; i < featureCount; i++) {
                e = 1.0f;
                threshold = 0.0f;
                classifyErr = 1.0f;
                /* ������i���� */
                
                FeatureTool.sortFeatureValue(fTemplates.get(i), integralImage,features);
                /* �󵥸�������������ֵ */
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

            }// end ��һѵ����������
/*            System.out.println("++++++++++++++++++++");
            for (i = 0; i < weakClassifiers1.size(); i++) {
                System.out.println(weakClassifiers1.get(i));
            }
            System.out.println("++++++++++++++++++++++++");
*/
            k = 3;
            /* �Ӳ�ͬ�����������������ҳ�һ��������С������e�ļ򵥷����� */
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
                        weightError += samples.get(j).weight;  // �������������ļ�Ȩ������
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

            // ����Ȩֵ
            float beiTa = minErr / (1 - minErr);
            System.out.println("minErr = " + minErr);
            for (i = 0; i < sampleCount; i++) {
                if (samples.get(i).result == 1) {
                    samples.get(i).weight *= beiTa;
                }
            }

            System.out.println(weakClassifiers.get(t));
        }// end ѵ������

        return weakClassifiers;

    }

}

//WeakClassifier [feature=FeatureTemplate [type=1, x=8, y=-1, w=2, h=1], result1=0.1560253387243311, result2=-0.1383010647010489, value=0.0, minError=0.7772893381564554, offset=1]

//182657
