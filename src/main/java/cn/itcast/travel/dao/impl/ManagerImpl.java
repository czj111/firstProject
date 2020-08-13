package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.ExamTheme;
import cn.itcast.travel.domain.Files;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class ManagerImpl implements Manager{
    JdbcTemplate jdbc=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean addFile(String name) {
        String sql="insert into files values(?)";
        int update = jdbc.update(sql, name);
        if(update==0)
        {
            return false;
        }
        return true;
    }

    @Override
    public void addProblem(String tableName, ExamTheme examTheme) {
        String sql="insert into "+tableName+" values(?,?,?,?,?,?,?)";
        jdbc.update(sql,
                examTheme.getTheme(),
                examTheme.getAnswerA(),
                examTheme.getAnswerB(),
                examTheme.getAnswerC(),
                examTheme.getAnswerD(),
                examTheme.getAnswer(),
                examTheme.getType());
    }

    @Override
    public boolean createTable(String name) {
        String sql1="create table "+name+"(\n" +
                "theme nvarchar2(100),\n" +
                "answerA nvarchar2(100),\n" +
                "answerB nvarchar2(100),\n" +
                "answerC nvarchar2(100),\n" +
                "answerD nvarchar2(100),\n" +
                "answer nvarchar2(10),\n" +
                "type char(2)\n" +
                ")";
        String sql2="insert into 题库名 values(?)";
        int update=0;
        try {
            jdbc.update(sql1);
            jdbc.update(sql2,name);
        } catch (DataAccessException e) {
            System.out.println("出错啦");
            return false;
        }
        return true;
    }

    @Override
    public boolean find(String manager, String password) {
        String sql="select count(*) from 管理员 where manager=? and passsword=?";
        try {
            jdbc.queryForObject(sql, Integer.class,manager,password);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }


}
