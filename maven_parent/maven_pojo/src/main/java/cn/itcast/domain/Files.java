package cn.itcast.domain;

public class Files {
    private String name;

    @Override
    public String toString() {
        return "Files{" +
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
