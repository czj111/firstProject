package cn.itcast.travel.domain;

/**
 * 展示测试结果
 */
public class ExamScore {
    private int frequency;//考试次数
    private int score;//考试分数
    private String time;//考试成绩

    @Override
    public String toString() {
        return "ExamScore{" +
                "frequency=" + frequency +
                ", score=" + score +
                ", time='" + time + '\'' +
                '}';
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
