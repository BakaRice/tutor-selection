# 声明基础镜像
FROM openjdk:11
# 打包后在项目目录下，将编译后的jar包复制到容器指定位置
COPY ./target/*.jar /home/tutor-selection-tool.jar
# 声明入口，运行容器后，运行java程序，加参数使用生产环境的配置
ENTRYPOINT java -jar /home/tutor-selection-tool.jar --spring.profiles.active=prod
