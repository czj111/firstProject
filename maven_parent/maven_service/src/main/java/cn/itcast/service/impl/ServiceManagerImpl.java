package cn.itcast.service.impl;

import cn.itcast.dao.ManagerDao;
import cn.itcast.domain.Manager;
import cn.itcast.domain.ResultInfo;
import cn.itcast.service.ServiceManager;
import cn.itcast.utils.JedisUtil;
import com.sun.deploy.net.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class ServiceManagerImpl implements ServiceManager {

    @Autowired
    ManagerDao dao;
    public ResultInfo login(Manager manager) {
        ResultInfo resultInfo=new ResultInfo();
        if(dao.login(manager)>0)
        {
            resultInfo.setFlag(true);
        }
        else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("管理员名或密码错误");
        }
        return resultInfo;
    }

    public void addFileName(String name) {
        dao.addFileName(name);
    }

    public void createTable(String name) {
//        Jedis jedis = JedisUtil.getJedis();
        dao.createTable(name);
        dao.upQuestName(name);
//        if (jedis.exists("problem")){
//            jedis.del("problem");
//        }

    }

}
