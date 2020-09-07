package cn.itcast.dao;

import cn.itcast.domain.ExamTheme;
import cn.itcast.domain.Function;
import cn.itcast.domain.User;

import java.util.List;

public interface Dao {
    /**
     * 查看用户名是否存在
     * @param username
     * @return
     */
    int exitUserName(String username);

    /**
     * 添加用户
     * @param user
     * @return
     */
    void addUser(User user);

    /**
     * 激活用户
     * @param code
     * @return
     */
    void activeCode(String code);

    /**
     * 查询用户名和密码是否正确
     * @param user
     * @return
     */
    User exitUser(User user);

//    /**
//     * 获取所有功能
//     */
//    List<Function> findAllFunc();
//
//    /**
//     * 确认题库中的试题准备充足
//     * @param name
//     * @return
//     */
//    boolean examCount(String name);
//
//    /**
//     * 通过课程名找到相关题库,提取固定数量的单选题
//     * @param name
//     * @return
//     */
//    List<ExamTheme> examSin(String name);
//
//    /**
//     * 通过课程名找到相关题库,提取固定数量的多选题
//     * @param name
//     * @return
//     */
//    List<ExamTheme> examMul(String name);
}
