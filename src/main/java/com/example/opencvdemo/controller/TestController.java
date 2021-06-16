package com.example.opencvdemo.controller;

import com.example.opencvdemo.entity.DetectFaceParam;
import com.example.opencvdemo.util.OpenCvUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author aaron
 * @since 2021-06-08
 */

@RestController
@RequestMapping("/face")
public class TestController {


//    /**
//     * 检测人脸图片信息是否合法
//     *
//     * @param detectFaceParam 图片信息
//     * @return
//     */
//    @PostMapping("/detectFace")
//    void detectFace(@RequestBody DetectFaceParam detectFaceParam) throws Exception {
//        OpenCvUtils.checkFace(detectFaceParam.getPicture());
//    }

    /**
     * 检测人脸图片信息是否合法
     *
     * @param file 图片
     * @return
     */
    @PostMapping("/detectFace")
    void detectFace(@RequestParam("file") MultipartFile file) throws Exception {
        OpenCvUtils.checkFaceMultipartFile(file);
    }

}
