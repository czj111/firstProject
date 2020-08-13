package cn.itcast.travel.domain;

public class problemName {
    String name;

    @Override
    public String toString() {
        return "problemName{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
