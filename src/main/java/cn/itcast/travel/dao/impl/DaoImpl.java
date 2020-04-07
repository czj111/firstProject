package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

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
    public Boolean registerUser(User user) {
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
}
