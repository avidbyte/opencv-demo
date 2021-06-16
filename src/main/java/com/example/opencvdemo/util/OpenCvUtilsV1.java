//package com.example.opencvdemo.util;
//
//import com.example.opencvdemo.exception.PublicException;
//import com.example.opencvdemo.result.ErrorCode;
//import com.google.common.primitives.Bytes;
//import lombok.extern.slf4j.Slf4j;
//import org.opencv.core.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.objdetect.CascadeClassifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * 最初版  2021-06-07
// * @author aaron
// * @since 2021-06-16
// */
//@Component
//@Slf4j
//public class OpenCvUtilsV1 implements CommandLineRunner {
//
//
//    @Value("${opencv.lib.linuxxmlpath}")
//    private String linuxXmlPath;
//    @Value("${opencv.lib.windowxmlpath}")
//    private String windowXmlPath;
//
//
//    /**
//     * 人脸探测器对象
//     */
//    static CascadeClassifier faceDetector;
//
//    /**
//     * 判断是否是Windows系统
//     */
//    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");
//
//    /**
//     * 监测图片是否合法，是否只有一张脸
//     */
//    public static void checkFace(String pictureUrl) throws Exception {
////        //将在线图片保存为本地图片
////        String imgPath = saveLocal(pictureUrl);
////        //本地图片
////        File file  = new File(imgPath);
////        FileInputStream fileInputStream = new FileInputStream(file);
////        ByteArrayOutputStream out = new ByteArrayOutputStream();
////        byte[] localBuff = new byte[fileInputStream.available()];
////        fileInputStream.read(localBuff);
////        out.write(localBuff);
////        log.info("本地图片:"+localBuff.length);
//
//        //在线图片
//        URL url = new URL(pictureUrl);
//        URLConnection uc = url.openConnection();
//        InputStream inputStream = uc.getInputStream();
//        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
//        byte[] buff = new byte[1024];
//        int rc;
//        while ((rc = inputStream.read(buff, 0, 1024)) > 0) {
//            swapStream.write(buff, 0, rc);
//        }
//        byte[] urlBuff = swapStream.toByteArray();
//
//        log.info("在线图片:"+urlBuff.length);
//
//        List<Byte> bs = new ArrayList<>();
//        bs.addAll(Bytes.asList(urlBuff));
//        log.info("buffer长度"+bs.size());
//        /**
//         * 不好使
//         */
////        Mat image =  Converters.vector_char_to_Mat(bs);
////        Mat image  =  Converters.vector_uchar_to_Mat(bs);
//        /**
//         * 读取本地
//         */
////        Mat image = Imgcodecs.imread(imgPath);
//        /**
//         * 读数据流
//         */
//        Mat image  = Imgcodecs.imdecode(new MatOfByte(urlBuff), Imgcodecs.IMREAD_UNCHANGED);
//
//        if (image.empty()) {
//            log.error("image 内容不存在！");
//            return;
//        }
//        // 3 特征匹配
//        MatOfRect face = new MatOfRect();
//        faceDetector.detectMultiScale(image, face);
//        // 4 匹配 Rect 矩阵 数组
//        Rect[] rects = face.toArray();
//        System.out.println("匹配到 " + rects.length + " 个人脸");
////        delFile(imgPath);
//        if (rects.length == 0) {
//            throw new PublicException(ErrorCode.A0430.getCode(), "没有监测到人脸");
//        } else if (rects.length > 1) {
//            throw new PublicException(ErrorCode.A0430.getCode(), "检测到图片有多张人脸,请重新上传");
//        }
//    }
//
//    public static String saveLocal(String pictureUrl) throws IOException {
//        URL url = new URL(pictureUrl);
//        URLConnection uc = url.openConnection();
//        InputStream inputStream = uc.getInputStream();
//        String[] value = pictureUrl.split("/");
//        String firstFilePath = "D:\\pictures\\";
//        if (!IS_WINDOWS) {
//            firstFilePath = "/tmp/tmp-picture/";
//        }
//        String fileName = firstFilePath + value[value.length - 1];
//        FileOutputStream out = new FileOutputStream(fileName);
//        int j = 0;
//        while ((j = inputStream.read()) != -1) {
//            out.write(j);
//        }
//        inputStream.close();
//        return fileName;
//    }
//
//
//    /**
//     * Callback used to run the bean.
//     *
//     * @param args incoming main method arguments
//     * @throws Exception on error
//     */
//    @Override
//    public void run(String... args){
//        String systemProperties = String.valueOf(System.getProperties());
//        log.info(systemProperties);
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        String path = "";
//        //如果是window系统取出路径开头的/
//        if (IS_WINDOWS) {
//            path = windowXmlPath;
//        }else{
//            path = linuxXmlPath;
//        }
//        /**
//         * 初始化人脸探测器
//         */
//        faceDetector = new CascadeClassifier(path);
//        log.info("==========初始化人脸探测器成功===========");
//    }
//
//}
