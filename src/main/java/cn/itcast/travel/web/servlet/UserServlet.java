package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.impl.AllServiceImpl;
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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    serviceImpl service = new serviceImpl();
    AllServiceImpl allService=new AllServiceImpl();

    /**
     * 检查验证码是否正确
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
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
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(resultInfo));

    }
    /**
     * 检查用户名是否存在
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void rgUserName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        String username = URLDecoder.decode(request.getParameter("username"));
        System.out.println(username);
        String result=service.rgUserName(username);
//        System.out.println(result);
        response.getWriter().write(result);

    }

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    /**
     * 激活账户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code!=null || code!="")
        {
            boolean flag=service.activeCode(code);
            if(flag)
            {
                response.getWriter().write("激活成功！");
            }
        }
    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        response.setContentType("text/html;charset=utf-8");
//        6.返回结果
        response.getWriter().write(result);
    }

    /**
     * 登录后用户名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void userName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        System.out.println(username);
        ResultInfo result=new ResultInfo();
        if(username!=null)
        {
            result.setFlag(true);
            result.setErrorMsg(username);
        }
        else {
            result.setFlag(false);
        }
        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    /**
     * 文件下载页（文件显示）
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void downLoad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Paging paging = new Paging();
        try {
            BeanUtils.populate(paging,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        System.out.println("页号"+paging.getNowPage());
//        System.out.println("页数"+paging.getItems());
        if(paging.getMsg()==null || paging.getMsg()=="") {
            paging = allService.findAll(paging);
        }
        else{
            paging = allService.findFilesByName(paging);
        }
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(paging));
    }

    /**
     * 获取考试题目
     */
    public void acceptExam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String name = request.getParameter("name");
//        1.获取试题
        List<ExamTheme> examThemes = service.acceptExam(name);
//        System.out.println(examThemes);
//        传给用户的信息
        ExamInfo examInfo = new ExamInfo();
        if(examThemes!=null) {
            HttpSession session = request.getSession();
            session.setAttribute("exam",examThemes);
//        2.传给用户的题目
            List<Exam> exam = new ArrayList<>();
            for (ExamTheme examTheme : examThemes) {
                exam.add(new Exam(examTheme.getTheme(),
                        examTheme.getAnswerA(),
                        examTheme.getAnswerB(),
                        examTheme.getAnswerC(),
                        examTheme.getAnswerD()));
            }
            examInfo.setFlag(true);
            examInfo.setExam(exam);
        }
        else {
            examInfo.setFlag(false);
            examInfo.setMsg("题库还未准备好，请稍后再试");
        }
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(examInfo));
    }

    /**
     * 获取功能内容
     */
    public void findAllFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Function> allFunc = service.findAllFunc();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(writeValueAsString(allFunc));

    }
}
