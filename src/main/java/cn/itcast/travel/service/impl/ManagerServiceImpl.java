package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.ManagerImpl;
import cn.itcast.travel.domain.Paging;

public class ManagerServiceImpl implements ManagerService {
    ManagerImpl MgerImpl=new ManagerImpl();

    @Override
    public boolean addFileName(String name) {
        return MgerImpl.addFile(name);
    }


}
