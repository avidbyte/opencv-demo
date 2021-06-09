package com.example.opencvdemo.test;

import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * @author aaron
 * @since 2021-06-07
 */
@Slf4j
public class FaceVideo {

    /**
     * 初始化人脸探测器
     */
    static CascadeClassifier faceDetector;

    static {
        String systemProperties = String.valueOf(System.getProperties());
        log.info(systemProperties);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        faceDetector = new CascadeClassifier("D:\\software\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
    }


    public static void main(String[] args){
        // 3- 本地图片人脸识别，识别成功并保存人脸图片到本地
        String imgPath = "C:\\Users\\Administrator\\Pictures\\wang.jpg";
        face(imgPath);
    }

    /**
     * OpenCV-4.1.1 图片人脸识别
     *
     * @return: void
     * @date: 2019年5月7日12:16:55
     */
    public static void face(String imgPath) {
        /**
         * 读取本地
         */
        Mat image = Imgcodecs.imread(imgPath);
        if (image.empty()) {
            System.out.println("image 内容不存在！");
            return;
        }
        // 3 特征匹配
        MatOfRect face = new MatOfRect();
        faceDetector.detectMultiScale(image, face);
        // 4 匹配 Rect 矩阵 数组
        Rect[] rects = face.toArray();
        System.out.println("匹配到 " + rects.length + " 个人脸");
        // 5 为每张识别到的人脸画一个圈
        int i = 1;
        for (Rect rect : face.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
            imageCut(imgPath, "D:\\pictures\\" + i + ".jpg", rect.x, rect.y, rect.width, rect.height);// 进行图片裁剪
            i++;
        }
        // 6 展示图片
        HighGui.imshow("人脸识别", image);
        HighGui.waitKey(0);
    }

    /**
     * 裁剪人脸
     *
     * @param imagePath
     * @param outFile
     * @param posX
     * @param posY
     * @param width
     * @param height
     */
    public static void imageCut(String imagePath, String outFile, int posX, int posY, int width, int height) {
        // 原始图像
        Mat image = Imgcodecs.imread(imagePath);
        // 截取的区域：参数,坐标X,坐标Y,截图宽度,截图长度
        Rect rect = new Rect(posX, posY, width, height);
        // 两句效果一样
        Mat sub = image.submat(rect); // Mat sub = new Mat(image,rect);
        Mat mat = new Mat();
        Size size = new Size(width, height);
        Imgproc.resize(sub, mat, size);// 将人脸进行截图并保存
        Imgcodecs.imwrite(outFile, mat);
        System.out.println(String.format("图片裁切成功，裁切后图片文件为： %s", outFile));

    }







}
