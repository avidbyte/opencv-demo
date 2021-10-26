# 前言
项目中检测人脸图片是否合法的功能，之前用的是百度的人脸识别接口，由于成本高昂不得不寻求替代方案。
# 什么是opencv？
OpenCV是一个基于BSD许可（开源）发行的跨平台计算机视觉和机器学习软件库，可以运行在Linux、Windows、Android和Mac OS操作系统上。轻量级而且高效——由一系列 C 函数和少量 C++ 类构成，同时提供了Python、Java、MATLAB等语言的接口，实现了图像处理和计算机视觉方面的很多通用算法。


# 项目集成步骤
由于项目是放在Linux系统中跑的，开发环境是Windows10，所以项目中涉及到opencv的要分两套。
## 准备工作
### Windows安装opencv
opencv官网下载安装包[https://opencv.org/releases/](https://opencv.org/releases/)
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021060917234471.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MDM5MjA1Mw==,size_16,color_FFFFFF,t_70)
我这里选择的是4.1.1版本
分别下载了Windows版本和源码
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210609172523838.png)

### Windows环境下集成
安装opencv，没什么说的，指定一个路径安装即可，注意安装路径不能是中文。
项目中集成的**三个关键点**。

 - 引入jar依赖
 - 读取OpenCV自带的人脸识别特征XML文件
 - 配置opencv的库文件地址



#### 关键点1：引入jar包
jar包位置在安装路径下的java文件夹中
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210609201415840.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MDM5MjA1Mw==,size_16,color_FFFFFF,t_70)
两种方式引入
##### 方式一：idea添加jar
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210609202112678.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MDM5MjA1Mw==,size_16,color_FFFFFF,t_70)
或者直接在Libraries中添加二者皆可。
##### 方式二：将jar上传至私服，在maven中引入
我这里是将jar上传至私服，然后引用的。
注意Windows版的jar和Linux中的jar不一样，二者要区分开来
通过Maven配置在不同环境下加载不同的jar

```xml
<profiles>
    <profile>
        <id>dev</id>
        <dependencies>
<!--            本地引用-->
<!--                <dependency>-->
<!--                    <groupId>op</groupId>-->
<!--                    <artifactId>opencv</artifactId>-->
<!--                    <version>411</version>-->
<!--                    <scope>system</scope>-->
<!--                    <systemPath>-->
<!--                        ${project.basedir}/src/main/resources/opencv/windows/opencv-411.jar-->
<!--                    </systemPath>-->
<!--                </dependency>-->
            
<!--            仓库引用-->
            <dependency>
            <!--                这里改成自己的仓库地址-->
                <groupId>com.***.cloud.resource</groupId>
                <artifactId>opencv-window</artifactId>
                <version>411</version>
            </dependency>
        </dependencies>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
    <profile>
        <id>test</id>
        <dependencies>
            <dependency>
            <!--                这里改成自己的仓库地址-->
                <groupId>com.***.cloud.resource</groupId>
                <artifactId>opencv-linux</artifactId>
                <version>411</version>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```

#### 关键点2：配置人脸识别特征XML文件的地址
在bootstrap.yml添加如下参数

```xml
#  函数库地址 在 vm optionis中 配置
#  windows地址: -Djava.library.path=D:\software\opencv\build\java\x64
#  linux地址:   -Djava.library.path=/usr/local/opencv-4.1.1/build/lib/
opencv:
  lib:
    linuxxmlpath: /usr/local/share/opencv4/haarcascades/haarcascade_frontalface_alt.xml
    windowxmlpath: D:\software\opencv\sources\data\haarcascades\haarcascade_frontalface_alt.xml
```
测试的方法中就直接写死了

```java
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
```
注意路径！！

#### 关键点3：配置opencv的库文件地址
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210609214227904.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MDM5MjA1Mw==,size_16,color_FFFFFF,t_70)

```bash
-Djava.library.path=D:\software\opencv\build\java\x64
```
这里其实指向的就是 该目录下的 opencv_java411.dll 文件
（linux的配置见下文）

### 代码
#### 测试方法
```java
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

```
**注意！Mat image = Imgcodecs.imread(imgPath); 
imgPath中不能带有中文！** opencv安装路径中如果有中文的话就会报错。

#### 集成到Springboot
```java
package com.example.opencvdemo.util;

import com.example.opencvdemo.exception.PublicException;
import com.example.opencvdemo.result.ErrorCode;
import com.google.common.primitives.Bytes;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aaron
 * @since 2021-06-07
 */
@Component
@Slf4j
public class OpenCvUtils implements CommandLineRunner {

    @Value("${opencv.lib.linuxxmlpath}")
    private String linuxXmlPath;
    @Value("${opencv.lib.windowxmlpath}")
    private String windowXmlPath;

    /**
     * 人脸探测器对象
     */
    static CascadeClassifier faceDetector;

    /**
     * 判断是否是Windows系统
     */
    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");

    /**
     * 监测图片是否合法，是否只有一张脸
     */
    public static void checkFace(String pictureUrl) throws Exception {
//        //将在线图片保存为本地图片
//        String imgPath = saveLocal(pictureUrl);
//        //本地图片
//        File file  = new File(imgPath);
//        FileInputStream fileInputStream = new FileInputStream(file);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        byte[] localBuff = new byte[fileInputStream.available()];
//        fileInputStream.read(localBuff);
//        out.write(localBuff);
//        log.info("本地图片:"+localBuff.length);

        //在线图片
        URL url = new URL(pictureUrl);
        URLConnection uc = url.openConnection();
        InputStream inputStream = uc.getInputStream();
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc;
        while ((rc = inputStream.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] urlBuff = swapStream.toByteArray();

        log.info("在线图片:"+urlBuff.length);

        List<Byte> bs = new ArrayList<>();
        bs.addAll(Bytes.asList(urlBuff));
        log.info("buffer长度"+bs.size());
        /**
         * 不好使
         */
//        Mat image =  Converters.vector_char_to_Mat(bs);
//        Mat image  =  Converters.vector_uchar_to_Mat(bs);
        /**
         * 读取本地
         */
//        Mat image = Imgcodecs.imread(imgPath);
        /**
         * 读数据流
         */
        Mat image  = Imgcodecs.imdecode(new MatOfByte(urlBuff), Imgcodecs.IMREAD_UNCHANGED);

        if (image.empty()) {
            log.error("image 内容不存在！");
            return;
        }
        // 3 特征匹配
        MatOfRect face = new MatOfRect();
        faceDetector.detectMultiScale(image, face);
        // 4 匹配 Rect 矩阵 数组
        Rect[] rects = face.toArray();
        System.out.println("匹配到 " + rects.length + " 个人脸");
//        delFile(imgPath);
        if (rects.length == 0) {
            throw new PublicException(ErrorCode.A0430.getCode(), "没有监测到人脸");
        } else if (rects.length > 1) {
            throw new PublicException(ErrorCode.A0430.getCode(), "检测到图片有多张人脸,请重新上传");
        }
    }

    public static String saveLocal(String pictureUrl) throws IOException {
        URL url = new URL(pictureUrl);
        URLConnection uc = url.openConnection();
        InputStream inputStream = uc.getInputStream();
        String[] value = pictureUrl.split("/");
        String firstFilePath = "D:\\pictures\\";
        if (!IS_WINDOWS) {
            firstFilePath = "/tmp/tmp-picture/";
        }
        String fileName = firstFilePath + value[value.length - 1];
        FileOutputStream out = new FileOutputStream(fileName);
        int j = 0;
        while ((j = inputStream.read()) != -1) {
            out.write(j);
        }
        inputStream.close();
        return fileName;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args){
        String systemProperties = String.valueOf(System.getProperties());
        log.info(systemProperties);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String path = "";
        //如果是window系统取出路径开头的/
        if (IS_WINDOWS) {
            path = windowXmlPath;
        }else{
            path = linuxXmlPath;
        }
        /**
         * 初始化人脸探测器
         */
        faceDetector = new CascadeClassifier(path);
        log.info("==========初始化人脸探测器成功===========");
    }
}
```
OpenCV 提供的 API 是直接根据路径读取图片的，所以最开始的时候我是把图片保存到本地在读取才成功的，但是这种方式太憨了点，在实际生产环境中，大部分情况下都是直接读取网络图片。在内存就完成图片和 opencv 的 Mat 对象的转换。这里代码中已经解决了url地址图片转化的问题。
这里附上解决该问题的博客   [传送门](http://blog.joylau.cn/2019/04/03/OpenCV-ByteImage/)



### Linux安装opencv
Linux平台须要咱们手动编译，下载opencv-4.1.1.zip，解压到/user/local目录下，而后编译

```powershell
yum  install   ant    gcc  gtk2-devel   pkgconfig  zlib-devel
```
安装unzip命令
```powershell
yum install -y unzip zip
```
解压命令
```powershell
unzip opencv-4.1.1.zip
```

```powershell
yum   groupinstall "Development Tools"
```
**安装cmake**

查看cmake当前版本
```powershell
cmake --version
```
```powershell
yum -y install wget
```
下载获得cmake-3.9.2源码

```powershell
wget https://cmake.org/files/v3.9/cmake-3.9.2.tar.gz
```
解压、安装新版本

```powershell
tar -xvf cmake-3.9.2.tar.gz

cd cmake-3.9.2

./configure

sudo make && make install
```

```powershell
cd /usr/local/opencv-4.1.1
mkdir build
cd build
cmake -D CMAKE_BUILD_TYPE=RELEASE -D CMAKE_INSTALL_PREFIX=/usr/local -DBUILD_TESTS=OFF ..
make -j8
sudo make install
```
对应的jar和.so文件在

```powershell
/usr/local/share/java/opencv4/
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210610112715954.png)

人脸识别特征XML文件的地址

```powershell
/usr/local/share/opencv4/haarcascades/haarcascade_frontalface_alt.xml
```

### Linux启动
jar 启动命令添加Vm options
```powershell
nohup java -jar -Djava.library.path=/usr/local/share/java/opencv4/ opencv-demo-1.0.jar  > logs/opencv-demo-1.0.log 2>&1 &
```

