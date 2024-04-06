# 使用官方的 OpenJDK 8 作为基础镜像
FROM openjdk:8

# 设置工作目录为 /app
WORKDIR /app

# 将本地的 JAR 文件复制到镜像中的 /app 目录
COPY target/springSecurity_project-0.0.1-SNAPSHOT.jar app.jar

# 暴露容器的端口
EXPOSE 8888

# 容器启动时运行的命令
CMD ["java", "-jar", "app.jar"]
