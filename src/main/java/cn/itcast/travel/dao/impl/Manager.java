package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Files;

import java.util.List;

public interface Manager {
    /**
     * 添加上传的文件名
     * @param name
     * @return
     */
    boolean addFile(String name);
}
