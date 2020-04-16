package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.Paging;

public interface AllService {
    /**
     * 查询所有文件名
     * @param page
     * @return
     */
    Paging findAll(Paging page);
}
