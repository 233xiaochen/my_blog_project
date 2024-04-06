# 基础镜像
FROM openjdk:8-jdk

# 设置工作目录
WORKDIR /app

# 将应用程序 JAR 文件复制到镜像中
COPY target/springSecurity_project-0.0.1-SNAPSHOT.jar app.jar

# 设置容器启动命令
CMD ["java", "-jar", "app.jar"]
