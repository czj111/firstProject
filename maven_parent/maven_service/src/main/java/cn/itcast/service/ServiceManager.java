package cn.itcast.service;

import cn.itcast.domain.Manager;
import cn.itcast.domain.ResultInfo;

public interface ServiceManager {
    public ResultInfo login(Manager manager);

    public void addFileName(String name);
}
