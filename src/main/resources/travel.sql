/*==============================================================*/
/* DBMS name:      oracle                                   */
/* Created on:     2020/3/7 23:10:29                           */
/*==============================================================*/
set names utf8;

drop table if exists tab_function;
drop table if exists 题库名;
drop table if exists 用户测试;
drop table if exists 用户测试题目;
drop table if exists files;
drop table if exists tab_user;

/*==============================================================*/
/* Table: 用户表 (存储用户信息)
/*==============================================================*/
create table tab_user(
USERNAME VARCHAR2(100) not null,
PASSWORD VARCHAR2(32) not null,
BIRTHDAY DATE,
SEX CHAR(2),
EMAIL VARCHAR2(100),
STATUS CHAR(1),
CODE VARCHAR2(50)
)

/*==============================================================*/
/* Table: files (存储已有文件名)
/*==============================================================*/
create table files(
NAME VARCHAR2(100);
)

/*==============================================================*/
/* Table: 用户功能表 (描述用户拥有的功能)
/*==============================================================*/
create table tab_function(
cid nvarchar2(2),
funcName nvarchar2(20)
)


/*==============================================================*/
/* Table: 创建题库表 题库名（有相应题库）
/*==============================================================*/
create table 题库名(
bankName nvarchar2(20)
)

/*==============================================================*/
/* Table: 用户测试数据（保存用户测试数据）
/*==============================================================*/
create table 用户测试(
username VARCHAR2(32),
frequency int,
time VARCHAR2(32),
score int
)
create table 用户测试题目(
username VARCHAR2(32),
frequency int,
theme nvarchar2(100),
answerA nvarchar2(100),
answerB nvarchar2(100),
answerC nvarchar2(100),
answerD nvarchar2(100),
answer nvarchar2(10),
userAnswer nvarchar2(10)
)

/*==============================================================*/
/* Table: 管理员表 (管理员用户名和密码)
/*==============================================================*/
create table 管理员(
manager VARCHAR2(32) primary key,
passsword VARCHAR2(20)
)



