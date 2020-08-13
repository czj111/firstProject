package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.impl.ManagerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createTableServlet")
public class CreateTableServlet extends HttpServlet {
    ManagerServiceImpl manager=new ManagerServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        ResultInfo resultInfo = new ResultInfo();
        boolean table = manager.createTable(name);
        resultInfo.setFlag(table);
        if(table)
        {
            resultInfo.setErrorMsg("创建题库成功");
        }
        else
            resultInfo.setErrorMsg("此表已存在，请重新创建");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
