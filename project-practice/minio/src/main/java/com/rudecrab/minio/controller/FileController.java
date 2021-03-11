package com.rudecrab.minio.controller;

import com.rudecrab.minio.service.MinioService;
import io.minio.GetObjectResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private MinioService minioService;

    @PostMapping
    public String upload(MultipartFile file) throws Exception {
        // 获取文件后缀名
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        // 为了避免文件名重复，使用UUID重命名文件，将横杠去掉
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        // 上传
        minioService.putObject(file.getInputStream(), fileName, file.getContentType());
        // 返回文件名
        return fileName;
    }

    @GetMapping("{fileName}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws Exception  {
        // 设置响应类型
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        // 获取文件流
        GetObjectResponse objectResponse = minioService.getObject(fileName);
        // 将文件流输出到响应流
        IOUtils.copy(objectResponse, response.getOutputStream());
        // 结束
        response.flushBuffer();
        objectResponse.close();
    }

    @DeleteMapping("{fileName}")
    public String remove(@PathVariable("fileName") String fileName) throws Exception  {
        minioService.removeObject(fileName);
        return "success";
    }
}
