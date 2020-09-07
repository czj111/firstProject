import cn.itcast.dao.Dao;
import cn.itcast.dao.SAndDTest;
import cn.itcast.domain.Manager;
import cn.itcast.domain.ResultInfo;
import cn.itcast.domain.User;
import cn.itcast.service.ServiceManager;
import cn.itcast.service.ServiceUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:applicationContext-dao.xml","classpath:applicationContext-service.xml"})
public class DataTest {
    @Autowired
    SAndDTest test;
    @Autowired
    DataSource dataSource;
    @Autowired
    Dao dao;
    @Autowired
    ServiceUser service;
    @Autowired
    ServiceManager smanager;
    @Test
    //测试Spring与mybatis的整合
    public void test1(){
        System.out.println("dataSource="+dataSource);
        int count = test.findCount();
        System.out.println("int="+count);
    }
//    测试用户功能
    @Test
    public void test2()
    {
//        测试查询用户名是否存在功能
//        int result = dao.exitUserName("zhangsan");
//        System.out.println("result="+result);
//        测试添加用户功能
        User user=new User();
        user.setUsername("zhangsan");
        user.setPassword("12345678");
        user.setBirthday("2017-11-15 21:45:00");
        service.registerUser(user);
    }
//    测试管理员功能
    @Test
    public void test3() {
        Manager manager=new Manager();
        manager.setManager("czj");
        manager.setPassword("12345678");
        ResultInfo login = smanager.login(manager);
        System.out.println(login);
    }

}
