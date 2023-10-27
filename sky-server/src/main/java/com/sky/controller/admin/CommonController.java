package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: szw
 * @Date: 2023/10/27
 * @Description: com.sky.controller.admin
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = {"通用接口"})
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("问价上传")
    public Result<String> upload(MultipartFile file) {
        String upload = "";
        try {
            String filename = file.getOriginalFilename();
            String string = filename.substring(filename.lastIndexOf("."));
            upload = aliOssUtil.upload(file.getBytes(), UUID.randomUUID().toString() + string);
            return Result.success(upload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);

    }
}
