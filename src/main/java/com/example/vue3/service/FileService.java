package com.example.vue3.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.vue3.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

//文件操作
@Service
public class FileService {
    //服务器存放静态资源地址
    @Value("${fileRoot}")
    private String FileRoot;

    //服务器地址
    @Value("${ip}")
    private String ip;

    //本地写入
    public Result<?> imgUpload(MultipartFile file, String uuid) {
        try {
            //获取目标文件夹路径
            String target = FileRoot;
            //获取文件后缀
            String type = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            //设置文件唯一名称
            String fileName = uuid + "_" + IdUtil.fastUUID() + type;
            //设置文件写入路径
            String rootPath = target + fileName;
            //调取工具类写入文件
            FileUtil.writeBytes(file.getBytes(), rootPath);
            //返回文件浏览路径
            return Result.success("http://" + ip + "/" + fileName);
        } catch (IOException e) {
            return Result.error("-1", "上传错误！");
        }
    }

//    //下载接口
//    public void getImg(HttpServletResponse response, String uuid) {
//        //文件输出流
//        OutputStream os;
//        //获取文件唯一路径
//        String rootPath = System.getProperty("user.dir") + "/src/main/resources/files/" + uuid + "/avatar/";
//        //目标文件路径遍历获取所有文件
//        List<String> list = FileUtil.listFileNames(rootPath);
//        //查询文件名称
//        String s = list.stream().filter(name -> name.contains("avatar")).findAny().orElse("");
//        try {
//            //判断目标是否存在
//            if (StrUtil.isNotEmpty(s)) {
//                //设置返回头
//                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(s, "UTF-8"));
//                //设置连接类别
//                response.setContentType("application/octet-stream");
//                //进行输出
//                byte[] bytes = FileUtil.readBytes(rootPath + s);
//                os = response.getOutputStream();
//                os.write(bytes);
//                os.flush();
//                os.close();
//            }
//        } catch (Exception e) {
//            System.out.println("文件下载失败！");
//        }
//    }
}
