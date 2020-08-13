package cn.itcast.travel.domain;

import java.util.List;

/**
 *
 */
public class ExamResult {
    private String num;
    private ExamScore examScore;//考试成绩，时间，次数信息
    private List<ExamTheme> examThemes;//考试题目及答案

    @Override
    public String toString() {
        return "ExamResult{" +
                "num='" + num + '\'' +
                ", examScore=" + examScore +
                ", examThemes=" + examThemes +
                '}';
    }

    public ExamScore getExamScore() {
        return examScore;
    }

    public void setExamScore(ExamScore examScore) {
        this.examScore = examScore;
    }

    public List<ExamTheme> getExamThemes() {
        return examThemes;
    }

    public void setExamThemes(List<ExamTheme> examThemes) {
        this.examThemes = examThemes;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
