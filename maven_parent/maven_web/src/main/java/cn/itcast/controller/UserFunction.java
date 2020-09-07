package cn.itcast.controller;

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
@RequestMapping("/user")
public class UserFunction {
    @Autowired
    ServiceUser service;

    @RequestMapping("/rgUserName")
    @ResponseBody
    public ResultInfo rgUserName(String username){
        return service.exitUserName(username);
    }

    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse response, HttpServletRequest request){
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("CHECKCODE_SERVER",checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        try {
            ImageIO.write(image,"PNG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

    @RequestMapping("/check")
    @ResponseBody
    public ResultInfo check(HttpServletRequest request){
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只使用一次
        ResultInfo resultInfo = new ResultInfo();
        if(checkcode_server.equalsIgnoreCase(check)){
            resultInfo.setFlag(true);
        }
        else
        {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
        }
       return resultInfo;
    }

    @RequestMapping("/register")
    public ModelAndView register(User user, ModelAndView view){
        //        调用service完成注册
       service.registerUser(user);
       view.setViewName("/register_ok.html");

       return view;
    }

    @RequestMapping("/active")
    public void active(String code){
        service.activeCode(code);
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResultInfo login(User user,HttpServletRequest request){
        ResultInfo resultInfo=service.login(user);
//        4.判断是否登录
        if(resultInfo.getFlag())
        {
            HttpSession session = request.getSession();
            session.setAttribute("username",user.getUsername());
        }
        return resultInfo;
    }


}

