package cn.itcast.travel.service.impl;

import org.junit.Test;

public class DemoTest {
    @Test
    public void func(){
        String username="czj";
        serviceImpl service = new serviceImpl();
        String result=service.rgUserName(username);
        System.out.println(result);

    }
}
