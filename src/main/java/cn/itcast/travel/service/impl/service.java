package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.ExamTheme;
import cn.itcast.travel.domain.Function;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;

import java.util.List;

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

    /**
     * 获取功能
     */
    List<Function> findAllFunc();

    /**
     * 获取测试题目
     */
    List<ExamTheme> acceptExam(String name);
}
