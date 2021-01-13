# OnlineExamServer
在线考试系统——后端

## 简介
本项目使用vue和springcloud开发，并使用docker部署到阿里云

项目还包括了
1. [在线考试系统——后台管理界面](https://github.com/Chen-Jiake/OnlineExamAdmin)
2. [在线考试系统——学生考试界面](https://github.com/Chen-Jiake/OnlineExamStudent)

本项目在以下两个项目的基础上完成
1. https://github.com/hongfurui2014/exam_online_hongfurui
2. https://gitee.com/davz/yf-exam-lite

## 项目主要功能
### 学生考试
1. 考试中心：参加考试
2. 历史考试：查看考试成绩以及答题情况
### 后台管理
1. 系统管理：对后台用户、考试用户、角色权限进行管理
2. 考试管理：对试题和试卷进行管理，对考生成绩进行统计
3. 学校操作：对班级和科目进行管理
4. 统计日志：查看系统重要操作日志

## 在本机配置后端——以ubuntu18.04为例
1. 配置环境：java 11，mysql 5.7, maven
2. 执行 sql 文件生成数据库
3. 修改 application-dev.yml 中的配置

## 利用docker将后端部署在阿里云上
1. 在阿里云上安装docker
2. 使用 docker 安装 mysql 5.7，并生成数据库
3. 新建 jwt 文件夹
4. 修改 application-proc.yml 中的配置
5. 将生成的 jar 包和相应的 Dockerfile上传至阿里云生成镜像
6. 启动容器的时候注意 jwt 文件夹的映射

## 利用docker将后端部署在虚拟机上
大致流程和以上相同，但是在自己配的时候还是遇到了一些坑，需要注意的是 application.yml 文件中的一些配置：
1. mysql 的 ip 地址为虚拟机的 ip 地址
2. Eureka 客户端的 ip 地址是 docker 的 ip 地址
