package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.DaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class serviceImpl implements service {
    DaoImpl daoImpl=new DaoImpl();
    ResultInfo result=new ResultInfo();
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String rgUserName(String username) {
        Map<String,String> result_map=new HashMap<>();
        String result=null;
//        1.校验名字长度
        if(username.length()<2)
        {
            result_map.put("code","40006");
            result_map.put("error","名称太短");
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
                boolean find=daoImpl.findUserName(username);
                if(find)
                    result_map.put("code","0");
                else
                {
                    result_map.put("code","40014");
                    result_map.put("error","用户名已存在");
                }
            }
            else
            {
                result_map.put("code","40004");
                result_map.put("error","不可以包含除-与_之外的特殊字符");
            }


        }
        try {
             result=objectMapper.writeValueAsString(result_map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Boolean registerUser(User user) {
//        1.设置状态码
        user.setStatus("N");
//        2.设置激活码
        user.setCode(UuidUtil.getUuid());
        if(daoImpl.registerUser(user))
        {
//            3.发送邮件
            String text="<a href='http://192.168.1.11:80/travel/user/active?code="+user.getCode()+"'>请点击激活</a>";
            MailUtils.sendMail(user.getEmail(),text,"激活邮件");
            return true;
        }
        return false;
    }

    @Override
    public boolean activeCode(String code) {
        return daoImpl.activeCode(code);
    }

    @Override
    public ResultInfo login(User user) {

//        1.查看用户和密码是否正确
        User exitUser=daoImpl.exitUser(user);
//        System.out.println(exitUser);
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
//        System.out.println(result);

        return result;
    }
}
