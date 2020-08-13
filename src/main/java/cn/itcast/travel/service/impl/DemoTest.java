package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.AllDaoImpl;
import cn.itcast.travel.dao.impl.ManagerImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DemoTest {
    @Test
    public void func(){
        ManagerImpl service = new ManagerImpl();
//        serviceImpl service = new serviceImpl();
//        System.out.println(service.findAllFunc());
//        AllServiceImpl allService = new AllServiceImpl();
        String table="数据库";
        ExamTheme examTheme=new ExamTheme();
        examTheme.setTheme("test1");
        examTheme.setAnswerA("testA");
        examTheme.setAnswerB("testB");
        examTheme.setAnswerC("testC");
        examTheme.setAnswerD("testD");
        examTheme.setAnswer("A");
        examTheme.setType("单");
        service.addProblem(table,examTheme);


    }
}
