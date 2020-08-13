package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.impl.AllServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/all/*")
public class AllServlet extends BaseServlet {
    AllServiceImpl service= new AllServiceImpl();
    /**
     * 文件下载页（文件下载）
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void downLoad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获取文件下载路径和文件名
        String fileName = request.getParameter("fileName");
        String path = request.getParameter("path");
//        System.out.println(fileName);
//        System.out.println(path);
        String downloadPath=path+fileName;
//        System.out.println(downloadPath);
        //找到文件服务器路径
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath(downloadPath);
        //用字节流关联
        FileInputStream fileInputStream = new FileInputStream(realPath);
        //设置响应头类型
        String mimeType = servletContext.getMimeType(fileName);
        response.setContentType(mimeType);
        //解决文件名下载乱码的问题
        byte[] bytes = fileName.getBytes("utf-8");
        fileName = new String(bytes, "ISO8859-1");
        //修改响应打开方式
        response.setHeader("content-disposition","attachment;filename="+fileName);

        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //将输入流数据写入输出流
        int len=0;
        byte[] buff=new byte[1024];
        while((len=fileInputStream.read(buff))!=-1)
        {
            outputStream.write(buff,0,len);
        }
        //关闭流
        fileInputStream.close();
        outputStream.close();
    }

    /**
     * 获取题库名
     */
    public void problemName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<problemName> problemName = service.findProblemName();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(writeValueAsString(problemName));
    }

    /**
     * 存储用户提交的测试内容
     */
    public void saveExam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        1.获得用户名和考试题目及答案
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        List<ExamTheme> exam = (List<ExamTheme>)session.getAttribute("exam");

//        2.获取用户答案
        String data = request.getParameter("answer");

//        3.存储数据
        ExamScore score = service.Score(username, exam, data);
//        4.删除数据

        response.setContentType("application/json;charset=utf-8");

        response.getWriter().write(writeValueAsString(score));

    }

    /**
     * 查看考试信息
     */
    public void showExamScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        List<ExamScore> examScores = service.showExamScore(username);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(writeValueAsString(examScores));
    }

    /**
     * 查看考试内容
     */
    public void showExamContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String fre = request.getParameter("fre");
        ExamResult examResult = service.showExam(username, Integer.valueOf(fre));
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(writeValueAsString(examResult));
    }

        /**（删除）
         * 文件查询功能
         * @param request
         * @param response
         * @throws ServletException
         * @throws IOException
         */
//    public void findFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        Paging paging = new Paging();
//        try {
//            BeanUtils.populate(paging,parameterMap);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        paging = service.findFilesByName(paging);
//        response.setContentType("application/json;charset=utf-8");
//        ObjectMapper objectMapper = new ObjectMapper();
//       response.getWriter().write(objectMapper.writeValueAsString(paging));
//    }

}
