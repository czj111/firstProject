package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class AllDaoImpl implements AllDao{
    JdbcTemplate jdbc=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Files> findAllFiles(int start, int end) {
        String sql="select * from (select rownum num,files.* from files) u where u.num between ? and ?";

        return jdbc.query(sql, new BeanPropertyRowMapper<Files>(Files.class),start,end);
    }

    @Override
    public int countFiles() {
        String sql="select count(*) from files";
        try {
            return jdbc.queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public int countFilesByName(String name) {
        String sql="select count(*) from files where name like ?";
        try {
            return jdbc.queryForObject(sql, Integer.class,name);
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public List<Files> findFilesByName(int start, int end, String name) {
        String sql="select * from (select rownum num,files.* from files where name like ?) u where u.num between ? and ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<Files>(Files.class),name,start,end);
    }

    @Override
    public List<problemName> findProblemName() {
        String sql="select * from 题库名";
        return jdbc.query(sql,new BeanPropertyRowMapper<problemName>(problemName.class));
    }

    @Override
    public int findFre(String username) {
        String sql="select count(*) from 用户测试 where username=?";
        return jdbc.queryForObject(sql,Integer.class,username);
    }

    @Override
    public void save(String username, ExamResult examResult) {
        String sql="insert into 用户测试 values(?,?,?,?)";
        jdbc.update(sql,username,examResult.getExamScore().getFrequency(),
                examResult.getExamScore().getTime(),
                examResult.getExamScore().getScore());
        String sql1="insert into 用户测试题目 values(?,?,?,?,?,?,?,?,?)";
        for(int i=0;i<20;i++)
        {
            jdbc.update(sql1,username,examResult.getExamScore().getFrequency(),
                    examResult.getExamThemes().get(i).getTheme(),
                    examResult.getExamThemes().get(i).getAnswerA(),
                    examResult.getExamThemes().get(i).getAnswerB(),
                    examResult.getExamThemes().get(i).getAnswerC(),
                    examResult.getExamThemes().get(i).getAnswerD(),
                    examResult.getExamThemes().get(i).getAnswer(),
                    examResult.getExamThemes().get(i).getUserAnswer());
        }
    }

    @Override
    public List<ExamScore> showExamScore(String name) {
        String sql="select * from 用户测试 where username=? order by frequency";
        List<ExamScore> query = jdbc.query(sql, new BeanPropertyRowMapper<ExamScore>(ExamScore.class), name);
        return query;
    }

    @Override
    public List<ExamTheme> showExam(String name, int fre) {
        String sql="select * from 用户测试题目 where username=? and frequency=?";
        List<ExamTheme> examTheme = jdbc.query(sql, new BeanPropertyRowMapper<ExamTheme>(ExamTheme.class), name, fre);
        return examTheme;
    }

    @Override
    public ExamScore oneExam(String name, int fre) {
        String sql="select * from 用户测试 where username=? and frequency=?";
        ExamScore examScore = jdbc.queryForObject(sql, new BeanPropertyRowMapper<ExamScore>(ExamScore.class), name, fre);
        return examScore;
    }
}
