package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.ExamTheme;
import cn.itcast.travel.domain.Function;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DaoImpl implements Dao{
    JdbcTemplate jdbc=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean findUserName(String username) {
        String sql="select * from tab_user where username=?";
        try {
            Map<String, Object> stringObjectMap = jdbc.queryForMap(sql, username);
        }catch (EmptyResultDataAccessException e)
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean registerUser(User user) {
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?)";
        jdbc.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());

        return true;
    }

    @Override
    public boolean activeCode(String code) {
        String sql="update tab_user set status='Y' where code=? and status='N'";
        int update = jdbc.update(sql, code);
        System.out.println("update:"+update);
        System.out.println("-------------------------");
        if(update!=0)
            {
                System.out.println("true");
            return true;
        }
        System.out.println("false");
        return false;
    }

    @Override
    public User exitUser(User user) {
        User user1=null;
        String sql="select * from tab_user where username=? and password=?";
        try {
            user1 = jdbc.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        } catch (DataAccessException e) {

        }
        return user1;

    }

    @Override
    public List<Function> findAllFunc() {
        String sql="select * from tab_function";
        return jdbc.query(sql,new BeanPropertyRowMapper<Function>(Function.class));
    }

    @Override
    public boolean examCount(String name) {
        System.out.println("----------------");
        String sql="select count(*) from "+name+" where type = ?";
        Integer sin=null;
        Integer Mul=null;
        try {
            sin = jdbc.queryForObject(sql, Integer.class,  "单");
            Mul = jdbc.queryForObject(sql, Integer.class,  "多");
        } catch (DataAccessException e) {
            System.out.println(sin+"---"+Mul);
            return false;
        }
        System.out.println(sin+"====="+Mul);
        if(sin>=10 && Mul>=10) {
            return true;
        }
        else
            return false;
    }

    @Override
    public List<ExamTheme> examSin(String name) {
        String sql="select * from (select * from (select * from "+name+" where type='单') order by dbms_random.value) where rownum< 11";
        return jdbc.query(sql, new BeanPropertyRowMapper<ExamTheme>(ExamTheme.class));
    }

    @Override
    public List<ExamTheme> examMul(String name) {
        String sql="select * from (select * from (select * from "+name+" where type='多') order by dbms_random.value) where rownum< 11";
        return jdbc.query(sql, new BeanPropertyRowMapper<ExamTheme>(ExamTheme.class));
    }

}
