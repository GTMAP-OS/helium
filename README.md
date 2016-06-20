# helium
A Java Backend Application!
java应用配置中心。实现java web应用的properties配置实时更新，无需重启应用。

# 技术路线
- 基于jdk1.7
- [spring](https://github.com/spring-projects/spring-framework)
- [jackson](https://github.com/codehaus/jackson)

# 实现思路
- 利用java7中的watchservice实时监控配置文件的变化，利用监听方式实时刷新系统的properties
- 增加对国图特有的配置路径的配置文件的支持。只需要在helium.json中配置需要监控的配置文件即可。
- 后期会集成多个应用的配置管理 以及提供console前台页面对配置进行界面化管理与实时的配置功能。


# 使用方法：

添加maven依赖：
