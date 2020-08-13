package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ExamTheme;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.problemName;
import cn.itcast.travel.service.impl.AllServiceImpl;
import cn.itcast.travel.service.impl.ManagerServiceImpl;
import cn.itcast.travel.util.Excel2003Utils;
import cn.itcast.travel.util.Excel2007Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/manager/*")
public class ManagerServlet extends BaseServlet {
    ManagerServiceImpl managerService = new ManagerServiceImpl();
    AllServiceImpl service= new AllServiceImpl();
    /**
     *管理员上传文件功能
     */
    public void upLoadServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断请求是否为Multipart
        if(!ServletFileUpload.isMultipartContent(request))
        {
            throw new RuntimeException("当前请求不支持文件上传");
        }
        String path_temp=this.getServletContext().getRealPath("/temp");
        File file_temp=new File(path_temp);
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory(1024*1024*600,file_temp);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解决文件名乱码问题。
        upload.setHeaderEncoding("UTF-8");
        //限制每个文件上传大小
        upload.setFileSizeMax(1024*1024*100);
        // Parse the request
        List<FileItem> items=null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        //文件名
        String projectName=null;
        String examTime=null;
        String Name=null;
        for(FileItem item:items)
        {
            //判断是否为普通表单项
            if(item.isFormField())
            {
                if("projectName".equals(item.getFieldName()))
                {
                    projectName=item.getString("UTF-8");
                }
                if("examTime".equals(item.getFieldName()))
                {
                    examTime=item.getString("UTF-8");
                }
            }
            else
            {
                //防止文件名重复
                Name=projectName+examTime+System.currentTimeMillis()+item.getName();
                String path=this.getServletContext().getRealPath("/images");
                try{
                    item.write(new File(path,Name));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                item.delete();
            }
        }
        //将文件名存储到数据库
        ResultInfo resultInfo = new ResultInfo();
        boolean flag=managerService.addFileName(Name);
        if(flag)
        {
            resultInfo.setErrorMsg("上传成功");
        }
        else{
            resultInfo.setErrorMsg("上传失败");
        }
        resultInfo.setFlag(flag);
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(resultInfo));
    }

    /**
     * 创建题库功能
     */
    public void createTableServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String name = request.getParameter("name");
        ResultInfo resultInfo = new ResultInfo();
        boolean table = managerService.createTable(name);
        resultInfo.setFlag(table);
        if(table)
        {
            resultInfo.setErrorMsg("创建题库成功");
        }
        else
            resultInfo.setErrorMsg("此表已存在，请重新创建");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(writeValueAsString(resultInfo));
    }

    /**
     * 获取题库名
     */
    public void problemName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("test--port");
        List<problemName> problemName = service.findProblemName();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(writeValueAsString(problemName));
    }
    /**
     *填充题库试题
     */
    public void parseExcelServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //判断请求是否为Multipart
        if(!ServletFileUpload.isMultipartContent(request))
        {
            throw new RuntimeException("当前请求不支持文件上传");
        }
        String path_temp=this.getServletContext().getRealPath("/temp");
        File file_temp=new File(path_temp);
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory(1024*1024*600,file_temp);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解决文件名乱码问题。
        upload.setHeaderEncoding("UTF-8");
        //限制每个文件上传大小
        upload.setFileSizeMax(1024*1024*100);
        // Parse the request
        List<FileItem> items=null;
        String tableName=null;//存放的数据的表名
        List<ExamTheme> read=new ArrayList<>();//存放数据
        //读取数据
        try {
            items = upload.parseRequest(request);
            for (FileItem item : items) {
                if(item.isFormField())
                {
                    tableName = item.getString("utf-8");
                    System.out.println("tableName="+tableName);
                }
                else{
                    String name = item.getName();
                    if(name.contains(".xlsx")) {
                        read= Excel2007Utils.read(item.getInputStream());
                    }
                    else{
                        read = Excel2003Utils.read(item.getInputStream());
                    }
                    System.out.println("parseExcel:"+read);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        ResultInfo info = new ResultInfo();

        //将读出的数据写入数据库
        info.setFlag(managerService.addProblem(tableName,read));
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(writeValueAsString(info));
    }

}
