package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.serviceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("utf-8");
//        response.setContentType("application/json;charset=utf-8");
        //        1.获得对象
        Map<String, String[]> parameterMap = request.getParameterMap();
//        2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        3.调用service完成注册
        serviceImpl service = new serviceImpl();
        Boolean flag=service.registerUser(user);

//        3.响应结果
        ResultInfo resultInfo = new ResultInfo();
        if(flag)
        {
//            注册成功
            resultInfo.setFlag(true);
        }
        else
        {
//            注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
