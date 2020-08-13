package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.AllDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class AllServiceImpl implements AllService{
    AllDaoImpl allDaoImpl=new AllDaoImpl();

    @Override
    public Paging findAll(Paging page) {
//        获得数据库文件总的数量
        int countItems = allDaoImpl.countFiles();
        double dcountItems=Double.valueOf(countItems);
//        System.out.println("总的数据数量" + countItems);
//        System.out.println("一页的数量"+page.getItems());
        int items=Integer.parseInt(page.getItems());
        double ditems=Double.valueOf(items);
        int count = (int) Math.ceil(dcountItems/ditems);
//        System.out.println("总的页码数" + count);
        page.setCountItems(String.valueOf(countItems));
        page.setCount(String.valueOf(count));
        if (countItems != 0) {
//        获得开始,结束条数
            int start = (Integer.parseInt(page.getNowPage()) - 1) * Integer.parseInt(page.getItems()) + 1;
            int end = start + Integer.parseInt(page.getItems()) - 1;
            if (end > Integer.parseInt(page.getCountItems())) {
                end = Integer.parseInt(page.getCountItems());
            }
                page.setPages(allDaoImpl.findAllFiles(start, end));
        }
                return page;
    }

    @Override
    public Paging findFilesByName(Paging page) {
        //        获得数据库文件总的数量
        System.out.println(page.getMsg());
        String msg=page.getMsg();
        String name="%";
        for(int i=0;i<msg.length();i++)
        {
            name=name+msg.charAt(i)+"%";
        }
//        System.out.println(name);

        int countItems=allDaoImpl.countFilesByName(name);
        double dcountItems=Double.valueOf(countItems);
//        System.out.println("总的数据数量" + countItems);
//        System.out.println("一页的数量"+page.getItems());
        int items=Integer.parseInt(page.getItems());
        double ditems=Double.valueOf(items);
        int count = (int) Math.ceil(dcountItems/ditems);
//        System.out.println("总的页码数" + count);
        page.setCountItems(String.valueOf(countItems));
        page.setCount(String.valueOf(count));

        if(countItems!=0) {
//        获得开始,结束条数
            int start = (Integer.parseInt(page.getNowPage()) - 1) * Integer.parseInt(page.getItems()) + 1;
            int end = start + Integer.parseInt(page.getItems()) - 1;
            if (end > Integer.parseInt(page.getCountItems())) {
                end = Integer.parseInt(page.getCountItems());
            }
//            System.out.println("end:"+end);
            page.setPages(allDaoImpl.findFilesByName(start,end,name));
        }
        return page;
    }

    @Override
    public List<problemName> findProblemName() {
//        1.查看redis是否有缓存
        Jedis jedis = JedisUtil.getJedis();
        problemName prob=null;
        List<problemName> problem=null;
        if(!jedis.exists("problem")){
            problem=allDaoImpl.findProblemName();
            for(int i=0;i<problem.size();i++)
            {
                jedis.zadd("problem",(double)i,problem.get(i).getName());
            }
        }
        else {
            problem=new ArrayList<>();
            Set<String> probName = jedis.zrange("problem", 0, -1);
            for (String s : probName) {
                prob = new problemName();
                prob.setName(s);
                problem.add(prob);
            }
        }
        return problem;
    }

    @Override
    public ExamScore Score(String username, List<ExamTheme> examThemes, String userAnswer) {
        ExamScore userScore =new ExamScore();//返回给用户的分数和次数
        ExamResult examResult = new ExamResult();//存储用户的测试数据
//        1.获取考试时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        userScore.setTime(time);
//        2.获得用户考试次数
        int fre = allDaoImpl.findFre(username)+1;
        userScore.setFrequency(fre);
//        3.获取用户答案
        String[] split = userAnswer.split(":");
        for(int i=0;i<split.length;i++)
        {
            examThemes.get(i).setUserAnswer(split[i]);
        }
//        for(int i=0;i<split.length;i++) {
//            System.out.println("用户答案" + split[i]);
//        }
//        4.获取用户成绩
        int score=0;
        for(int i=0;i<20;i++)
        {
            if(split[i].equals(examThemes.get(i).getAnswer()))
            {
                score++;
            }
        }
        score=5*score;
        userScore.setScore(score);
//        System.out.println("成绩"+score);
//        5.获取用户题目
        examResult.setExamThemes(examThemes);
        examResult.setExamScore(userScore);
//        6.保存此次考试信息
        allDaoImpl.save(username,examResult);

        return userScore;
    }

    @Override
    public List<ExamScore> showExamScore(String name) {
        return allDaoImpl.showExamScore(name);
    }

    @Override
    public ExamResult showExam(String name, int fre) {
        ExamResult result=new ExamResult();
        result.setExamThemes(allDaoImpl.showExam(name,fre));
        result.setExamScore(allDaoImpl.oneExam(name,fre));
        return result;
    }

}
