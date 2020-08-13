package cn.itcast.travel.service.impl;


import cn.itcast.travel.domain.ExamTheme;
import cn.itcast.travel.domain.Paging;

import java.util.List;

public interface ManagerService {
    /**
     * 数据库添加上传文件名
     * @param name
     * @return
     */
    boolean addFileName(String name);

    /**
     * 为数据库添加题目
     * @param tableName
     * @param read
     * @return
     */
    boolean addProblem(String tableName, List<ExamTheme> read);

    /**
     * 创建题库,并记录题库名
     */
    boolean createTable(String name);

    /**
     * 管理员登录
     */
    boolean find(String manage,String password);
}
