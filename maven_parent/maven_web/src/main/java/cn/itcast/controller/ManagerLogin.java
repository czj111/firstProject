package cn.itcast.controller;

import cn.itcast.domain.Manager;
import cn.itcast.domain.ResultInfo;
import cn.itcast.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ManagerLogin {

    @Autowired
    ServiceManager service;
    @RequestMapping("/managerlogin")
    @ResponseBody
    public ResultInfo login(Manager manager, HttpServletRequest request){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo=service.login(manager);
        if(resultInfo.getFlag())
        {
            request.getSession().setAttribute("manager",manager.getManager());
        }
        return resultInfo;
    }
}
