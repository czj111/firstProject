package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.User;

public interface Dao {
    /**
     * 查看用户名是否存在
     * @param username
     * @return
     */
    boolean findUserName(String username);

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
     * 判断用户名和密码是否正确
     * @param user
     * @return
     */
    User exitUser(User user);
}
