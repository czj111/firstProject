package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.ManagerImpl;
import cn.itcast.travel.domain.ExamTheme;
import cn.itcast.travel.domain.Paging;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

public class ManagerServiceImpl implements ManagerService {
    ManagerImpl MgerImpl=new ManagerImpl();

    @Override
    public boolean addFileName(String name) {
        return MgerImpl.addFile(name);
    }

    @Override
    public boolean addProblem(String tableName, List<ExamTheme> read) {
        if(read.size()>0) {
            for (ExamTheme examTheme : read) {
                MgerImpl.addProblem(tableName, examTheme);
            }
        }
        return true;
    }

    @Override
    public boolean createTable(String name) {
        Jedis jedis = JedisUtil.getJedis();
        boolean flag=MgerImpl.createTable(name);
        if(flag) {
            if (jedis.exists("problem"))
                jedis.del("problem");
        }
        return flag;
}

    @Override
    public boolean find(String manage, String password) {
        return MgerImpl.find(manage,password);
    }


}
