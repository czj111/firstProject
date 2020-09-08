package cn.itcast.service;

import cn.itcast.domain.*;

import java.util.List;

public interface ServiceUser {
    /**
     * 验证用户名
     * @param username
     * @return
     */
    ResultInfo exitUserName(String username);

    /**
     * 注册用户
     * @param user
     * @return
     */
    void registerUser(User user);

    /**
     * 激活用户
     * @param code
     * @return
     */
    void activeCode(String code);

    /**
     * 登录功能
     * @param user
     * @return
     */
    ResultInfo login(User user);

    /**
     * 获取功能
     */
    List<Function> findAllFunc();

    /**
     * 获取测试题目
     */
    List<ExamTheme> acceptExam(String name);

    /**
     * 查询所有文件名
     * @param page
     * @return
     */
    Paging findAll(Paging page);
}
