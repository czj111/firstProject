package cn.itcast.controller;


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
        file.transferTo(new File("E:\\IdeaProjects\\travel\\maven_parent\\maven_web\\src\\main\\webapp\\files\\"+name));
        service.addFileName(name);
    }
}
