package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.impl.ManagerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/managerLoginServlet")
public class managerLoginServlet extends HttpServlet {
    ManagerServiceImpl manageService=new ManagerServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String manager=request.getParameter("manager");
        String password=request.getParameter("password");
        ResultInfo result=new ResultInfo();
        result.setFlag(manageService.find(manager,password));
        if(!result.getFlag())
        {
            result.setErrorMsg("管理员名或密码错误");
        }
        else{
            request.getSession().setAttribute("manager",manager);
        }
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
