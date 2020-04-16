package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.AllDaoImpl;
import cn.itcast.travel.domain.Paging;

public class AllServiceImpl implements AllService{
    AllDaoImpl allDaoImpl=new AllDaoImpl();
    @Override
    public Paging findAll(Paging page) {
//        获得数据库文件总的数量
        int count=allDaoImpl.countFiles();
        page.setCount(String.valueOf(count));
        if(count!=0) {
//        获得开始,结束条数
            int start = (Integer.parseInt(page.getNowPage()) - 1) * Integer.parseInt(page.getItems()) + 1;
            int end = start + Integer.parseInt(page.getItems()) - 1;
            if (end > Integer.parseInt(page.getCount())) {
                end = Integer.parseInt(page.getCount());
            }
            page.setPages(allDaoImpl.findAllFiles(start, end));
        }
        return page;
    }
}
