配置文件命名规则：
````java
application-{profile}.yml
````
启动应用后按照如下格式对URL访问：
> http://localhost:8888/{applicationname}/{profile}/{label}
````java
http://localhost:8888/config-server/dev  
http://localhost:8888/config-server/prod 
http://localhost:8888/config-server/test
````

也可以直接访问配置文件：
````java
http://localhost:8888/application-dev.yml
````