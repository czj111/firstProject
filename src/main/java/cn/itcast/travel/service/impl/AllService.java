package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.*;

import java.util.List;

public interface AllService {
    /**
     * 查询所有文件名
     * @param page
     * @return
     */
    Paging findAll(Paging page);

    /**
     * 根据名字查询文件
     * @param page
     * @return
     */
    Paging findFilesByName(Paging page);

    /**
     * 查询所有已建立的题库名
     */
    List<problemName> findProblemName();

    /**
     * 存储用户测试数据
     */
    ExamScore Score(String username,List<ExamTheme> examThemes,String userAnswer);

    /**
     * 查看考试信息
     */
    List<ExamScore> showExamScore(String name);

    /**
     * 查看考试内容
     */
    ExamResult showExam(String name,int fre);

}
