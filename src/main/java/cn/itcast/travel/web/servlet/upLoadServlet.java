package cn.itcast.travel.web.servlet;

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
        DiskFileItemFactory factory = new DiskFileItemFactory(1024*1024,file_temp);
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
        for(FileItem item:items)
        {
            //判断是否为普通表单项
            if(item.isFormField())
            {
                String fieldName=item.getFieldName();
                String fieldValue=item.getString("UTF-8");
                out.println(fieldName+"="+fieldValue);
            }
            else
            {
                String Name=item.getName();
                //防止文件名重复
                Name=System.currentTimeMillis()+Name;
                InputStream uploadedStream = item.getInputStream();
                byte[] buf=new byte[1024];
                String path=this.getServletContext().getRealPath("/images");
                File uploadedFile = new File(path,Name);
                FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile);
                int len=-1;
                if((len=uploadedStream.read(buf))!=-1)
                {
                    fileOutputStream.write(buf,0,len);
                }
                fileOutputStream.close();
                uploadedStream.close();
                item.delete();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
