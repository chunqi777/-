package com.example.vue3.controller;

import com.example.vue3.common.Result;
import com.example.vue3.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//图像上传查询接口
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file, @RequestParam String nickname) {
        return fileService.Upload(file, nickname);
    }

    @PostMapping("/imgUpload")
    public Result<?> IMGUpload(MultipartFile file, @RequestParam String nickname) {
        return fileService.imgUpload(file, nickname);
    }
//    @GetMapping("/{uuid}")
//    public void getFiles(HttpServletResponse response, @PathVariable String uuid) {
//        fileService.getImg(response, uuid);
//    }
}
