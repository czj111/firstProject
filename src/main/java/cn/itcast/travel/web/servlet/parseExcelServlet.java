package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ExamTheme;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.impl.ManagerServiceImpl;
import cn.itcast.travel.util.Excel2003Utils;
import cn.itcast.travel.util.Excel2007Utils;
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

@WebServlet("/parseExcelServlet")
public class parseExcelServlet extends HttpServlet {
    ManagerServiceImpl manager=new ManagerServiceImpl();
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
        String tableName=null;//存放的数据的表名
        List<ExamTheme> read=new ArrayList<>();//存放数据
        //读取数据
        try {
            items = upload.parseRequest(request);
            for (FileItem item : items) {
                if(item.isFormField())
                {
                    tableName = item.getString("utf-8");
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
       info.setFlag(manager.addProblem(tableName,read));

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
