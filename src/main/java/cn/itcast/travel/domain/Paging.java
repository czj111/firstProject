package cn.itcast.travel.domain;

import java.util.List;

public class Paging {
    private String count;//总的页数
    private String items;//每页信息数
    private String nowPage;//现在所在页数
    private List<Files> pages;//数据

    @Override
    public String toString() {
        return "Paging{" +
                "count='" + count + '\'' +
                ", items='" + items + '\'' +
                ", nowPage='" + nowPage + '\'' +
                ", pages=" + pages +
                '}';
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


}
