package com.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wuchangming on 17/7/3.
 */
public class FileController {
    private static final org.slf4j.Logger log =
            LoggerFactory.getLogger(FileController.class);
    private File path=new File("/changming/files/");
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        log.info("uploadFile,fileName={}",file.getName());
        File filePath=new File(path,file.getName());
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        String resultStr="";
        if(!file.isEmpty()){
            try {
                byte[] bytes=new byte[2048];
                inputStream=file.getInputStream();
                if(!path.exists()){
                    path.mkdirs();
                }
                fileOutputStream=new FileOutputStream(filePath);
                int len=0;
                while((len=inputStream.read(bytes))>0){
                    fileOutputStream.write(bytes,0,len);
                }
                resultStr="成功";
            } catch (IOException e) {
                resultStr="失败";
                log.error("exception=",e);
            }finally {
                try {
                    if(fileOutputStream!=null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    log.error("exception=",e);
                }
            }

        }else{
            resultStr="失败";
        }
        return resultStr;
    }
}
