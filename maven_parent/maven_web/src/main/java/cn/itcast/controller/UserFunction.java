package cn.itcast.controller;

import cn.itcast.domain.*;
import cn.itcast.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller
@RequestMapping("/userFunc")
public class UserFunction {
    @Autowired
    ServiceUser service;
//    获取下载文件
    @RequestMapping("/downLoad")
    @ResponseBody
    public Paging downLoad(Paging paging){
        if(paging.getMsg()==null)
        {
            paging.setMsg("");
        }
        paging=service.findAll(paging);
        return paging;
    }

//    下载文件
    @RequestMapping("/downloadFile")
    @ResponseBody
    public void downloadFile(Files file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName=file.getName();
        String downloadPath=file.getPath()+fileName;
        ServletContext servletContext = request.getServletContext();
        String realPath = servletContext.getRealPath(downloadPath);
        //用字节流关联
        FileInputStream fileInputStream = new FileInputStream(realPath);
        //设置响应头类型
        String mimeType = servletContext.getMimeType(file.getName());
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
    @RequestMapping("/bankName")
    @ResponseBody
    public List<BankName> bankName(){
        return service.findbankName();
    }

//    获取考试题目

    public ResultInfo acceptExam(String name){
        ResultInfo resultInfo=new ResultInfo();
        List<ExamTheme> examThemes = service.acceptExam(name);

        return null;
    }
}

