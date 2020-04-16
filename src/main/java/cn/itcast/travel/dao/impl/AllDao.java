package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Files;

import java.util.List;

public interface AllDao {
    /**
     * 查询所有的文件名
     * @return
     */
    List<Files> findAllFiles(int start, int end);

    /**
     * 查询上传文件有多少
     * @return
     */
    int countFiles();
}
