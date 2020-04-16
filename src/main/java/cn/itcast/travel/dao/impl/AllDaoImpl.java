package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Files;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

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
}
