# 导师选择系统

 东北林业大学 Web系统框架课程  导师选择系统 demo 后端

![image-20200519002133178](./docs/image-20200519002133178.png)



> ps. 自己真的做的很差劲，很多地方缺漏很多，算是学习的过程。写的也支离破碎，希望能够通过这个小项目学习一些知识。
>
> [:runner:前端](https://github.com/BakaRice/tutor-selection-vue)

## 关于工程
- JDK 1.11、Maven 3.2、Mysql8.0、swagger 2
- 使用Spring Boot作为核心框架
- 包含REST API

系统支持多个导师及学生的双向选择，导师可以设置每一门课程的权重以及最低分限制以达到对学生进行选择的目的。
学生可以对心仪导师进行选择。导师可以提前指定内定学生。

### 实体类

1. User

2. Teacher

3. Student

4. Course

5. Elective

   详见entity包内实体类

### 接口

## [Login Controller] 
1. **POST**

   /api/login  **用户登录**

    Parameters Example Value:

   ```JSON
   {
     "number": 1001,
     "password": 1001
   }
   ```

    Response body：

   ```json
   {
     "role": "fbf89a7c09ff46b4"
   }
   ```

   Respones Headers:

   ```
   authorization: /*加密后的role和uid字符串*/
   ```



## [Student Controller]
1. **GET**  查询自己的老师
   /api/students/teacher


2. **GET** 查询所有老师
   /api/students/teachers
   
3. **GET** 选取导师
   /api/students/teahcers/{tnumber}
   
   

## [Teacher Controller]
1. **POST** 为当前登录教师老师添加课程
   `/api/teachers/course`

   Parameters Example Value:

  ```json
  {
    "credit": 2.5,
  "lowsetSorce": 60,
     "name": "NewCourse",
  "weight": 0.7
   }
  ```

   

2. **GET** 查询当前登录老师的所有课程
   `/api/teachers/courses`

   

3. **GET** 查询指定course_id的课程
   `/api/teachers/courses/{course_id}`

   

4. **DELETE** 删除课程
   `/api/teachers/courses/{course_id}`

   

5. **POST** 为指定id课程添加学生，会覆盖原有该学生选修此课程的信息
   `/api/teachers/courses/{course_id}/students`
Parameters Example Value:（此处为StudentVO类）
   
   ```json
   [
     {
    "grade": 65,
       "name": "Student_1",
    "number": 2001
     },
     {
       "grade": 35,
       "name": "Student_2",
       "number": 2002
     }
   ]
   ```
   
   
   
6. **PATCH** 修改指定id课程名称和学分
   `/api/teachers/courses/info`

   Parameters Example Value:
   
   ```json
{ "id": 1, "name": exampleName, "credit": 3.5 }
   ```

   
   
7. **PATCH** 修改指定id课程最低分和权重
   `/api/teachers/courses/setting`

   Parameters Example Value:

   ```json
   { "id": 1, "lowsetSorce": 65, "weight": 0.2 }
   ```

   

   

8. **GET**  列出当前登录老师的所有学生
   `/api/teachers/students`

   


9. **PATCH** 为当前登录教师老师添加内定学生
   `/api/teachers/students`

   

10. **GET** 列出当前登录老师的个人信息
    `/api/teachers/teacher`

    

11. **POST** 修改老师信息
    `/api/teachers/teahcer`
    
    

---



### 疑惑与发现
1. `hibernate DDL Cascade`  

    在写删除课程时，发现存在外键无法直接删除，要记得在ddl时，加上cascade操作。
    详见`eumm CascadeType`,
    以及hibernate注解ddl操作好文：https://www.cnblogs.com/javaxiaoxin/p/8279641.html
2.  `EntityManager fresh`

    关于undo区 

### 时间轴
`~2020/03/11`:  
- 创建项目 和 实体类，并进行测试。

`~2020/03/12`:  
- 新增教师实体类、  
- 新增`Authority类`进行独立账户验证   
- 创建教师与学生间多对一关系  
- 为课程类添加权重属性

`~2020/03/17`  
- 调整实体类，修改属性细节。~~新增`schedule类`作为教师和课程中间类，用于设置权重及最低分。
(用于支持多个老师对于课程的权重，进行不同的设置)~~  
- 新增抽象repositories、services。

`~2020/03/24`
- 添加 swagger

`~2020/03/25`
- 添加自定义异常 用于处理插入时的外键查找不存在，将service层异常抛至controller层处理。
- CourseController <KBD>ADD</KBD> <KBD>DELETE</KBD> <KBD>PATCH</KBD> <KBD>GET</KBD> <KBD>POST</KBD> 
- 添加老师的内定学生

`~2020/03/31`
- 优化Sevice结构，重构为student与teacher服务。


`~2020/04/04`
- 添加   
登陆拦截器(`LoginInterceptor`),    
初始化用户组件(`InitComponent`),    
加密解密组件(`EncryptComponent`),  
获取HeaderAttribute组件(`RequestComponent`)
- swagger `authorization` 配置 ,便于测试需要登陆的接口

感谢阅读这份文档。
