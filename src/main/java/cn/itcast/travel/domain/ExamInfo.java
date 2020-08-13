package cn.itcast.travel.domain;

import java.util.List;

/**
 * 获取到的考试题目
 */
public class ExamInfo {
    private boolean flag;//是否获取考试题目
    private List<Exam> exam;//考试题目
    private String msg;//考试科目
    public ExamInfo() {
    }

    @Override
    public String toString() {
        return "ExamInfo{" +
                "flag=" + flag +
                ", exam=" + exam +
                ", msg='" + msg + '\'' +
                '}';
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Exam> getExam() {
        return exam;
    }

    public void setExam(List<Exam> exam) {
        this.exam = exam;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
