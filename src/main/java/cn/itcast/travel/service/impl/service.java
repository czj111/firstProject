package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;

public interface service {

    /**
     * 验证用户名
     * @param username
     * @return
     */
    String rgUserName(String username);

    /**
     * 注册用户
     * @param user
     * @return
     */
    Boolean registerUser(User user);

    /**
     * 激活用户
     * @param code
     * @return
     */
    boolean activeCode(String code);

    /**
     * 登录功能
     * @param user
     * @return
     */
    ResultInfo login(User user);
}
