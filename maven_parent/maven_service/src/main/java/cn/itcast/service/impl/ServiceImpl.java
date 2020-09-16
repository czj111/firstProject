package cn.itcast.service.impl;

import cn.itcast.dao.Dao;
import cn.itcast.dao.UserFunctionDao;
import cn.itcast.domain.*;
import cn.itcast.service.ServiceUser;
import cn.itcast.utils.MailUtils;
import cn.itcast.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@Service
public class ServiceImpl implements ServiceUser {
    @Autowired
    Dao dao;
    @Autowired
    UserFunctionDao userFunctionDao;
    public ResultInfo exitUserName(String username) {
        ResultInfo resultInfo =new ResultInfo();
//                1.校验名字长度
        if(username.length()<2)
        {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("名称太短");
        }
        else
        {
//            2.校验名字字符
            String regex="[\\w|-|\\u4e00-\\u9fa5]{2,}";
            Pattern compile = Pattern.compile(regex);
            Matcher matcher = compile.matcher(username);
            boolean matches = matcher.matches();
            if(matches)
            {
//                3.查看数据库
                int find=dao.exitUserName(username);
                if(find==0)
                    resultInfo.setFlag(true);
                else
                {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("用户名已存在");
                }
            }
            else
            {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("不可以包含除-与_之外的特殊字符");
            }


        }

        return resultInfo;
    }

    public void registerUser(User user) {
        //        1.设置状态码
        user.setStatus("N");
//        2.设置激活码
        user.setCode(UuidUtil.getUuid());
        dao.addUser(user);
//            3.发送邮件
        String text="<span>"+user.getUsername()+",<a href='http://192.168.43.100:8080/user/active?code="+user.getCode()+"'>请点击激活</a></span>";
        MailUtils.sendMail(user.getEmail(),text,"激活邮件");
    }

    public void activeCode(String code) {
        dao.activeCode(code);
    }

    public ResultInfo login(User user) {
        ResultInfo result=new ResultInfo();
        User exitUser = dao.exitUser(user);
        if(exitUser==null)
        {
            result.setFlag(false);
            result.setErrorMsg("用户名或密码错误");
        }
        else {
            //        2.判断是否激活账号
            if ( exitUser.getStatus().equals("Y")) {
                result.setFlag(true);
            } else {
                result.setFlag(false);
                result.setErrorMsg("未激活");
            }
        }
        return result;
    }

    public List<Function> findAllFunc() {
        return null;
    }

    public List<ExamTheme> acceptExam(String name) {

        return null;
    }

    public Paging findAll(Paging page) {
        PageHelper.startPage(page.getNowPage(),page.getItems());
        String name="%";
        String msg=page.getMsg();
        for(int i=0;i<msg.length();i++)
        {
            name=name+msg.charAt(i)+"%";
        }
        List<Files> allFiles = userFunctionDao.findAllFiles(name);
        page.setPages(allFiles);
        PageInfo pageInfo=new PageInfo(allFiles);
        page.setCount(pageInfo.getPages());
        page.setCountItems(pageInfo.getTotal());

        return page;
    }

    public List<BankName> findbankName() {
        return userFunctionDao.findBankName();
    }
}
