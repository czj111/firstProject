package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.AllDaoImpl;
import cn.itcast.travel.domain.Files;
import org.junit.Test;

import java.util.List;

public class DemoTest {
    @Test
    public void func(){
        AllDaoImpl allDao = new AllDaoImpl();
        List<Files> allFiles = allDao.findAllFiles(1, 3);

        for(Files files:allFiles)
        {
            System.out.println(files);
        }

    }
}
