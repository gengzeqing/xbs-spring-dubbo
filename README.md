# xbs-spring-dubbo
spring整合dubbo的多模块maven项目

# 使用说明
1.xbs-api是dubbo接口;
2.xbs-provide是dubbo服务提供者;
(1)需要配置zookeeper连接信息；
(2)需要配置数据库连接，数据库分主数据库和从数据库，读写分离；
数据库主键没有使用数据库自身的自增策略，而是由程序利用Spring的AOP来生成；
(3)需要配置redis集群信息；

3.xbs-web是dubbo服务消费者;
配置和xbs-provide一样.

4.先启动xbs-provide工程,再启动xbs-web工程;
