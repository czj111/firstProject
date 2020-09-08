package cn.itcast.controller;


import cn.itcast.domain.ResultInfo;
import cn.itcast.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/manage")
public class ManagerFunction {

    @Autowired
    ServiceManager service;

    @RequestMapping("/upLoad")
    @ResponseBody
    public void upLoad(String projectName, String examTime, MultipartFile file, HttpServletRequest request) throws IOException {
        String name=projectName+examTime+System.currentTimeMillis()+file.getOriginalFilename();
        String realPath = request.getServletContext().getRealPath("files");
        File f1=new File(realPath,name);
        if(!f1.getParentFile().exists())
        {
            f1.getParentFile().mkdirs();
        }
        f1.createNewFile();
        file.transferTo(f1);
        service.addFileName(name);
    }

    @RequestMapping("/createTable")
    @ResponseBody
    public ResultInfo createTable(String name){
        ResultInfo resultInfo=new ResultInfo();
        service.createTable(name);
        resultInfo.setErrorMsg("创建成功！");
        return resultInfo;
    }
}
