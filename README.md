## Backend Project For PKU Web Course

This project is for PKU Web Course.

### 关于配置

电脑里需要安装Java 17、Gradle和MySQL。

**如果直接运行本项目时，由于MySQL的配置会又错，因此需要自己配置！**

在appplication.properties里配置MySQL。如果在本地运行MySQL时，appplication.properties的内容为如下：

```
spring.datasource.url=jdbc:mysql://localhost:{端口号，默认值为3306}/{table的名字}?serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username={用户名}
spring.datasource.password={密码}
```

如果想换在本地运行的后端程序的端口时，在appplication.properties里加：

```
server.port={想要的端口}
```

本项目将端口设置为80。

### 关于运行

1. 通过terminal进入本项目，输入`./gradlew build`，生成build文件夹。
2. 进入build里的libs文件夹，会找到`pku-web-0.0.1-SNAPSHOT.jar`文件。
3. 输入`java -jar pku-web-0.0.1-SNAPSHOT.jar`运行项目。
