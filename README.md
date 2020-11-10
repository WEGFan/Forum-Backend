# Neusoft Forum Backend

东软暑假实训 论坛系统后端

项目预览地址：

- 前台页面：<http://forum.wegfan.cn/>
- 后台页面：<http://forum-admin.wegfan.cn/>

管理员帐号密码：`admin` / `123456`

## 部署

1. 安装 [OpenJDK 8](https://openjdk.java.net/)、[MySQL 5.7](https://www.mysql.com/)、[Maven 3.6+](https://maven.apache.org/)
2. 克隆代码 `git clone https://github.com/WEGFan/Neusoft-Forum-Backend && cd Neusoft-Forum-Backend`
3. 建数据库 `mysql -u root -p <sql/forum.sql`
4. 打包 `mvn clean package`

## 运行

`java -jar ./target/forum-0.0.1-snapshot.jar`

初始帐号密码：`admin` / `admin123`
