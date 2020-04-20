package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.impl.ManagerService;
import cn.itcast.travel.service.impl.ManagerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import static java.lang.System.out;

@WebServlet("/upLoadServlet")
public class upLoadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        ManagerServiceImpl managerService = new ManagerServiceImpl();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
