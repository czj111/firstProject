package cn.itcast.controller;

import cn.itcast.domain.Paging;
import cn.itcast.domain.ResultInfo;
import cn.itcast.domain.User;
import cn.itcast.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


@Controller
@RequestMapping("/userFunc")
public class UserFunction {
    @Autowired
    ServiceUser service;
    @RequestMapping("downLoad")
    @ResponseBody
    public Paging downLoad(Paging paging){
        if(paging.getMsg()==null)
        {
            paging.setMsg("");
        }
        paging=service.findAll(paging);
        return paging;
    }

}

