package cn.itcast.dao;

import cn.itcast.domain.ExamTheme;
import cn.itcast.domain.Manager;
import org.apache.ibatis.annotations.Param;

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
     * 创建题库
     */
    void createTable(@Param("name") String name);

    /**
     * 更新题库名
     */
    void upQuestName(String name);
}
