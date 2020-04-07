package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.serviceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        1.获取数据
        Map<String, String[]> parameterMap = request.getParameterMap();
//        2.封装数据
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        System.out.println(user);
//        3.调用service
        serviceImpl service = new serviceImpl();
        ResultInfo resultInfo=service.login(user);
//        4.判断是否登录
        if(resultInfo.getFlag())
        {
            HttpSession session = request.getSession();
            session.setAttribute("username",user.getUsername());
        }
//        5.转为json
        String result=null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result=objectMapper.writeValueAsString(resultInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        System.out.println(result);

//        6.返回结果
        response.getWriter().write(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
