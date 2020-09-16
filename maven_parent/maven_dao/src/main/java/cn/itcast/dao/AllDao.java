package cn.itcast.dao;


import cn.itcast.domain.BankName;
import cn.itcast.domain.Files;

import java.util.List;

public interface AllDao {
    /**
     * 查询所有的文件名
     * @return
     */
    List<Files> findAllFiles(int start, int end);

    /**
     * 查询上传文件有多少
     * @return
     */
    int countFiles();

    /**
     * 通过名字查询数据库此文件数量
     * @param name
     * @return
     */
    int countFilesByName(String name);

    /**
     * 通过名字查询符合条件的文件名
     * @param start
     * @param end
     * @param name
     * @return
     */
    List<Files> findFilesByName(int start, int end, String name);

    /**
     * 查询所有已建立的题库名
     */
    List<BankName> findProblemName();

    /**
     * 查询用户已测试的次数
     */
    int findFre(String username);

//    /**
//     * 保存考试信息
//     */
//    void save(String username, ExamResult examResult);
//
//    /**
//     * 查看所有考试信息
//     */
//    List<ExamScore> showExamScore(String name);
//
//    /**
//     * 查看考试内容
//     */
//    List<ExamTheme> showExam(String name, int fre);
//
//    /**
//     *查看某一次考试信息
//     */
//    ExamScore oneExam(String name, int fre);

}
