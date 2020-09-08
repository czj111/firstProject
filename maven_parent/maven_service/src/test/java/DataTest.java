import cn.itcast.dao.Dao;
import cn.itcast.dao.ManagerDao;
import cn.itcast.dao.SAndDTest;
import cn.itcast.domain.*;
import cn.itcast.service.ServiceManager;
import cn.itcast.service.ServiceUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

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
    ManagerDao managerdao;
    @Autowired
    ServiceUser suser;
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
        suser.registerUser(user);
    }
//    测试管理员功能
    @Test
    public void test3() {
        managerdao.createTable("test");
    }

//    测试分页功能
    @Test
    public void test4() {
        Paging page =new Paging();
        page.setMsg("");
        page.setNowPage(1);
        page.setItems(2);
        Paging all = suser.findAll(page);
        List<Files> pages = all.getPages();
        for(Files f:pages)
        {
            System.out.println(f);
        }
        System.out.println(all);

    }

}
