package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CharacterFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        1.将父接口转为子接口
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
//        2.获取请求方法
        String method=request.getMethod();
//        3.解决post中文乱码
        if(method.equalsIgnoreCase("post"))
        {
            request.setCharacterEncoding("utf-8");
        }
        response.setContentType("text/html;charset=utf-8");
//        4.判断是否放行
        String requestURI = request.getRequestURI();
        if(requestURI.contains("/login.html") || requestURI.contains("/register.html") ||
                requestURI.contains("/js/") ||requestURI.contains("/css/") ||
                requestURI.contains("/img/") || requestURI.contains("/checkCode") ||
                requestURI.contains("/user/") || requestURI.contains("/fonts/") ||
                requestURI.contains("/register_ok.html") || requestURI.contains("/managerLogin.html")
                ||requestURI.contains("/managerLoginServlet"))
        {
            System.out.println("1");
            System.out.println(requestURI);
            chain.doFilter(request, response);
        }
        else
        {
            if(requestURI.contains("/manager/") || requestURI.contains("/manager.html") || requestURI.contains("/upLoadTest.html")
            || requestURI.contains("/parseExcelTest.html") || requestURI.contains("/createTable.html"))
            {
                String manager=(String)request.getSession().getAttribute("manager");
                if (manager != null && manager != "") {
                    System.out.println("2");
                    System.out.println(requestURI);
                    chain.doFilter(request, response);
                } else {
                    response.sendRedirect("managerLogin.html");
                }
            }
            else {
                String username = (String) request.getSession().getAttribute("username");
                if (username != null && username != "") {
                    System.out.println("3");
                    System.out.println(requestURI);
                    chain.doFilter(request, response);
                } else {
                    response.sendRedirect("login.html");
                }
            }
        }
//
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
