package com.example.vue3.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.example.vue3.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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

    //图片类型
    private final String[] imgType = {".jpg", ".png", ".gif", ".jpeg"};

    //视频类型
    private final String[] mp4Type = {".mp4", ".mkv", ".mp3"};

    //本地写入
    public Result<?> Upload(MultipartFile file, String nickName) {
        try {
            //设置用户后端资源仓库位置
            String target = FileRoot + nickName + "/";
            //获取文件后缀
            String type = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            //设置文件唯一名称
            String fileName = file.getOriginalFilename();
            //设置文件写入路径
            String rootPath;
            //判断文件是否为图片
            if (Arrays.asList(imgType).contains(type)) {
                //用户文件仓库位置
                String finalTarget = target + "image/";
                //创建文件目录
                Files.createDirectories(Paths.get(finalTarget));
                //文件目标写入路径
                rootPath = finalTarget + fileName;
            } else if (Arrays.asList(mp4Type).contains(type)) {
                //用户文件仓库位置
                String finalTarget = target + "mp4/";
                //创建文件目录
                Files.createDirectories(Paths.get(finalTarget));
                //文件目标写入路径
                rootPath = finalTarget + fileName;
            } else {
                //用户文件仓库位置
                String finalTarget = target + "files/";
                //创建文件目录
                Files.createDirectories(Paths.get(finalTarget));
                //文件目标写入路径
                rootPath = finalTarget + fileName;
            }
            //调取工具类写入文件
            FileUtil.writeBytes(file.getBytes(), rootPath);
            //返回图片的浏览路径
            return Result.success("http://" + ip + "/" + fileName);
        } catch (IOException e) {
            return Result.error("-1", "上传错误！");
        }
    }

    public Result<?> imgUpload(MultipartFile file, String nickName) {
        try {
            //获取文件后缀
            String type = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            //设置文件唯一名称
            String fileName = IdUtil.fastUUID() + "_" + nickName + type;
            //文件目标写入路径
            String rootPath = FileRoot + "image/" + fileName;
            //调取工具类写入文件
            FileUtil.writeBytes(file.getBytes(), rootPath);
            //返回图片的浏览路径
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
