package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.ExamTheme;
import cn.itcast.travel.domain.Files;

import java.util.List;

public interface Manager {
    /**
     * 添加上传的文件名
     * @param name
     * @return
     */
    boolean addFile(String name);

    /**
     * 在数据库内添加试题
     * @param tableName
     * @param examTheme
     */
    void addProblem(String tableName, ExamTheme examTheme);

    /**
     * 创建题库,并记录题库名
     */
    boolean createTable(String name);

    /**
     * 查询管理员和密码是否正确
     */
    boolean find(String manager,String password);

}
