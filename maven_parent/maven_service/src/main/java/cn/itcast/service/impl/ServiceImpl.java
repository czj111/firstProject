package cn.itcast.service.impl;

import cn.itcast.dao.Dao;
import cn.itcast.domain.ExamTheme;
import cn.itcast.domain.Function;
import cn.itcast.domain.ResultInfo;
import cn.itcast.domain.User;
import cn.itcast.service.ServiceUser;
import cn.itcast.utils.MailUtils;
import cn.itcast.utils.UuidUtil;
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
//    DaoImpl daoImpl=new DaoImpl();
//    ResultInfo result=new ResultInfo();
//    ObjectMapper objectMapper = new ObjectMapper();
//    @Override
//    public String rgUserName(String username) {
//        Map<String,String> result_map=new HashMap<>();
//        String result=null;
////        1.校验名字长度
//        if(username.length()<2)
//        {
//            result_map.put("code","40006");
//            result_map.put("error","名称太短");
//        }
//        else
//        {
////            2.校验名字字符
//            String regex="[\\w|-|\\u4e00-\\u9fa5]{2,}";
//            Pattern compile = Pattern.compile(regex);
//            Matcher matcher = compile.matcher(username);
//            boolean matches = matcher.matches();
//            if(matches)
//            {
////                3.查看数据库
//                boolean find=daoImpl.findUserName(username);
//                if(find)
//                    result_map.put("code","0");
//                else
//                {
//                    result_map.put("code","40014");
//                    result_map.put("error","用户名已存在");
//                }
//            }
//            else
//            {
//                result_map.put("code","40004");
//                result_map.put("error","不可以包含除-与_之外的特殊字符");
//            }
//
//
//        }
//        try {
//             result=objectMapper.writeValueAsString(result_map);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    @Override
//    public Boolean registerUser(User user) {
//////        1.设置状态码
////        user.setStatus("N");
//////        2.设置激活码
////        user.setCode(UuidUtil.getUuid());
////        if(daoImpl.registerUser(user))
////        {
//////            3.发送邮件
////            String text="<span>"+user.getUsername()+",<a href='http://192.168.1.11:80/travel/user/active?code="+user.getCode()+"'>请点击激活</a></span>";
////            MailUtils.sendMail(user.getEmail(),text,"激活邮件");
////            return true;
////        }
//        return false;
//    }
//
//    @Override
//    public boolean activeCode(String code) {
////        return daoImpl.activeCode(code);
//        return true;
//    }
//
//    @Override
//    public ResultInfo login(User user) {
//
//////        1.查看用户和密码是否正确
////        User exitUser=daoImpl.exitUser(user);
//////        System.out.println(exitUser);
////        if(exitUser==null)
////        {
////            result.setFlag(false);
////            result.setErrorMsg("用户名或密码错误");
////        }
////        else {
////            //        2.判断是否激活账号
////            if ( exitUser.getStatus().equals("Y")) {
////                result.setFlag(true);
////            } else {
////                result.setFlag(false);
////                result.setErrorMsg("未激活");
////            }
////        }
//////        System.out.println(result);
//
////        return result;
//    }
//
//    @Override
//    public List<Function> findAllFunc() {
////        查询redis是否有此信息
//        Jedis jedis = JedisUtil.getJedis();
//        Long size = jedis.zcard("function");
//        List<Function> allFunc=null;//返回的功能信息
//        if(size!=null && size!=0 )
//        {
//            allFunc=new ArrayList<>();
//            Set<Tuple> funcName = jedis.zrangeWithScores("function", 0, -1);
//            for (Tuple s : funcName) {
//                Function func=new Function();
//                func.setFuncName(s.getElement());
//                func.setCid(String.valueOf((int)s.getScore()));
//                allFunc.add(func);
//            }
//            Function func=new Function();
//            func.setFuncName("测试");
//            allFunc.add(func);
//        }
//        else{
//            allFunc = daoImpl.findAllFunc();
//            for (Function function : allFunc) {
//                jedis.zadd("function",Double.valueOf(function.getCid()),function.getFuncName());
//            }
//        }
//        return allFunc;
//    }
//
//    @Override
//    public List<ExamTheme> acceptExam(String name) {
////        1.判断题库题目足够
//        if(daoImpl.examCount(name))
//        {
////            2.获取单选题
//            List<ExamTheme> sin=daoImpl.examSin(name);
////            3.获取多选题
//            List<ExamTheme> mul=daoImpl.examMul(name);
//            sin.addAll(mul);
//            return sin;
//        }
//        return null;
//    }
}
