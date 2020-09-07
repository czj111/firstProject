package cn.itcast.dao;

import cn.itcast.domain.ExamTheme;
import cn.itcast.domain.Manager;

public interface ManagerDao {
    /**
     * 查询管理员和密码是否正确
     */
    int login(Manager manager);

    /**
     * 添加上传的文件名
     * @param name
     * @return
     */
    void addFileName(String name);

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
}
