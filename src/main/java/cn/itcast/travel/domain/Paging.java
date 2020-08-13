package cn.itcast.travel.domain;

import java.util.List;

public class Paging {
    private String countItems;//总共查询到的数据条数
    private String count;//总的页数
    private String items;//每页信息数
    private String nowPage;//现在所在页数
    private List<Files> pages;//数据
    private String msg;//文件查询信息

    @Override
    public String toString() {
        return "Paging{" +
                "countItems='" + countItems + '\'' +
                ", count='" + count + '\'' +
                ", items='" + items + '\'' +
                ", nowPage='" + nowPage + '\'' +
                ", pages=" + pages +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Files> getPages() {
        return pages;
    }

    public void setPages(List<Files> pages) {
        this.pages = pages;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getNowPage() {
        return nowPage;
    }

    public void setNowPage(String nowPage) {
        this.nowPage = nowPage;
    }

    public String getCountItems() {
        return countItems;
    }

    public void setCountItems(String countItems) {
        this.countItems = countItems;
    }
}
