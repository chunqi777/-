package com.example.vue3.controller;

import com.example.vue3.common.Result;
import com.example.vue3.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

//图像上传查询接口
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file, @RequestParam String uuid) {
        return fileService.imgUpload(file, uuid);
    }

//    @GetMapping("/{uuid}")
//    public void getFiles(HttpServletResponse response, @PathVariable String uuid) {
//        fileService.getImg(response, uuid);
//    }
}
