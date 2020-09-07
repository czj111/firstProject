package cn.itcast.domain;

/**
 * 存储测试题目
 */
public class ExamTheme {
    private String theme;//考试题目
    private String answerA;//选项
    private String answerB;
    private String answerC;
    private String answerD;
    private String answer;//题目答案
    private String userAnswer;//用户答案
    private String type;//题目类型

    public ExamTheme() {
    }

    public ExamTheme(String theme, String answerA, String answerB, String answerC, String answerD, String answer, String type) {
        this.theme = theme;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answer = answer;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ExamTheme{" +
                "theme='" + theme + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                ", answer='" + answer + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
