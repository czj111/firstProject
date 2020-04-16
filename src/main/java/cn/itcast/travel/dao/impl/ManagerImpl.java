package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Files;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

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


}
