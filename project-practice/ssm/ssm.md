> 以项目驱动学习，以实践检验真知

# 前言

现在使用Java后端开发使用的技术栈基本上比较统一：Spring + SpringMVC + Mybatis，即大家常说的SSM。虽然现在流行的做法是使用SpringBoot来快速搭建、配置好SSM项目，但还是有必要知道如何不用SpringBoot来组合好这三者，因为SpringBoot也只是帮助我们做好了许多配置，并不是说舍弃掉了那些配置，所以知道原生的SSM如何整合可以更好帮助我们理解SSM也能更好的理解SpringBoot带来的好处！而且有的老项目就是没有用SpringBoot，如果你对原生SSM整合与配置一无所知那维护老项目起来会极其难受。

SSM整合相比起SpringBoot的快速搭建自然是繁琐无比，但是不用担心，本文会一步一步演示如何整合这三者，并且会讲解每个配置的含义。老套路，文章最后还放上了思维导图和项目Github地址，**clone下来即可直接运行，如果想自己做一个完整的SSM项目，直接用这套架子进行开发也是完全没问题的！**

# 整合

## 项目搭建

### 创建项目

这里使用idea进行项目创建，maven来管理依赖包。首先我们在idea上新建一个project，选择Maven，然后选择web应用：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200319221215.png)

点击下一步后输入GroupId和ArtifactId后点击下一步直到完成。项目创建完毕后整个项目结构如下：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200319222112.png)



### 配置web项目

现在还先别着急配置SSM，我们先得配置一下这个idea下的web项目才行。大家也可以看到，项目建立起来后这个web.xml文件里写的是2.3版本，这个版本太老了，不行。

我们按住`catl + shift + alt + S`打开idea的Project Structure，然后点击左侧的Modules，再点击Web，然后点击右边的删除按钮，确定，最后点击APPLY先将这个默认的删除：



![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200319223405.png)



此时我们会发现默认的web.xml文件已经被删除了。然后我们再点击右侧的添加按钮，点击web.xml进行添加：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200319223507.png)

这里我们选择3.1版本，选择好后点击弹出框的OK，再点击下方的OK即可创建完毕：



![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200319223623.png)

创建完毕后就会发现我们的web.xml内容已经变成了3.1了。

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200319223749.png)

### 建立项目结构

此时还先不要着急去配置SSM，我们现在项目连个基本的结构都没有呢，你核心代码写在哪，你测试在哪些，你资源放哪这些都是我们要去建立的。

首先，我们在src路径下新建test文件夹，然后在src/main路径下新建java和resources文件夹。建立好文件夹后，右键点击文件夹，然后拖到下方，选择Mark Directory as，然后选择对应的目录结构。

java文件夹对应SourcesRoot，代表标记为项目源代码路径，代码就写在这里。

resources文件夹对应ResourcesRoot，代表标记为资源路径，所有资源比如配置文件就放在这。

test文件夹对应TestSourcesRoot，代表标记为测试路径，测试代码都会放在这里。

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200319234925.png)

文件夹指定好后，我们就要在java文件夹下创建我们的代码包结构。包的话就分为最基本的controller、service、mapper、entity。包建好后目录结构如下：



![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200321155756.png)

## 导入必备依赖包

基本的项目结构整理好后，接下来我们就要开始对SSM进行整合了。首先肯定要做的就是在pom.xml文件中导入必备的依赖包，直接复制粘贴就好了，各个地方都做了注释说明：

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>

    <!--统一配置jar包的版本-->
    <mysql.version>5.1.48</mysql.version>
    <spring.version>5.2.0.RELEASE</spring.version>
    <jackson.version>2.10.0</jackson.version>
</properties>

<dependencies>
    <!--单元测试，注意哦要4.12版本以上。scope为test代表只用来做测试，并不随版本打包发布此依赖包-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>

    <!--日志我们使用logback-->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.2.3</version>
    </dependency>

    <!-- 简化Getter、Setter的工具lombok。非必需
    注意：使用lombok还要在idea上下载对应的插件-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.10</version>
        <scope>provided</scope>
    </dependency>


    <!-- ***************数据库相关配置****************** -->
    <!-- mysql驱动依赖包，连接mysql必备-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
    </dependency>

    <!-- 数据源依赖包，能大幅提升性和便利性。这里我们用阿里的德鲁伊数据源-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.1.12</version>
    </dependency>

    <!-- Mybatis必备依赖包 -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.4.6</version>
    </dependency>

    <!-- ***************web相关配置****************** -->
    <!--配置JavaEE依赖包，包含了Servlet、Validation等功能-->
    <dependency>
        <groupId>javax</groupId>
        <artifactId>javaee-api</artifactId>
        <version>8.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- JSTL依赖包，如果用将jstl标签用在jsp中就要此依赖包。非必需的 -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>

    <!-- jackson依赖包,用来将java对象转换JSON格式，SpringMVC要用的 -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>${jackson.version}</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>${jackson.version}</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
        <version>${jackson.version}</version>
    </dependency>

    <!-- ***************Spring相关配置****************** -->
    <!--配置Spring JDBC容器所需的jar包-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>${spring.version}</version>
    </dependency>

    <!--配置Spring IOC容器所需的jar包-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>${spring.version}</version>
    </dependency>

    <!--Spring mvc-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>${spring.version}</version>
    </dependency>

    <!-- AspectJ所需的jar包-->
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.4</version>
    </dependency>

    <!--Spring测试依赖-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>${spring.version}</version>
        <scope>test</scope>
    </dependency>

    <!--配置Spring整合mybatis的jar包-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>1.3.2</version>
    </dependency>
</dependencies>
```

## Spring与Mybatis整合

### 数据库配置

在进行整合之前我们来准备一下数据库，好接下来进行一个完整的演示。我这里用的是MySQL 5.7.25，咱们建立一个名为ssm_demo的数据库，执行语句新建一张user表并插入两条测试数据：

```sql
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '账户名',
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '账户密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

INSERT INTO `user` VALUES (1, 'admin', '123456');
INSERT INTO `user` VALUES (2, 'rudecrab', '654321');
```

然后我们在entity包下建立和数据库表对应的实体类User：

```java
@Data // lombok注解，自动生成Getter、Setter、toString方法
public class User implements Serializable {
    private Long id;

    private String name;

    private String password;
}
```

然后我们在resources文件夹下建立`database.properties`文件来配置数据库连接的相关信息（这里根据自己的数据库信息配置哦），等下整合Mybatis要用到的：

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/ssm_demo?characterEncoding=utf-8&useSSL=false&autoReconnect=true&rewriteBatchedStatements=true&serverTimezone=UTC
jdbc.username=root
jdbc.password=root
```

### logback日志配置

真实项目中一般是要观察日志输出的，我们再配置一下日志。在resources目录下新建`logback.xml`文件。**注意啊，在尾部需要指定某个包，这个根据自己项目结构包名来设置**：

```xml
<?xml version="1.0" encoding="utf-8" ?>
<configuration>
    <!--定义日志文件输出地址-->
    <property name="LOG_ERROR_HOME" value="error"/>
    <property name="LOG_INFO_HOME" value="info"/>

    <!--通过appender标签指定日志的收集策略，我们会定义三个收集策略：控制台输出、普通信息文件输出、错误信息文件输出-->
    <!--name属性指定appender命名-->
    <!--class属性指定输出策略，通常有两种，控制台输出和文件输出，文件输出就是将日志进行一个持久化-->

    <!--控制台输出-->
    <appender name="CONSOLE_LOG" class="ch.qos.logback.core.ConsoleAppender">
        <!--使用该标签下的标签指定日志输出格式-->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--
            %p:输出优先级，即DEBUG,INFO,WARN,ERROR,FATAL
            %r:输出自应用启动到输出该日志讯息所耗费的毫秒数
            %t:输出产生该日志事件的线程名
            %f:输出日志讯息所属的类别的类别名
            %c:输出日志讯息所属的类的全名
            %d:输出日志时间点的日期或时间，指定格式的方式： %d{yyyy-MM-dd HH:mm:ss}
            %l:输出日志事件的发生位置，即输出日志讯息的语句在他所在类别的第几行。
            %m:输出代码中指定的讯息，如log(message)中的message
            %n:输出一个换行符号
            -->
            <pattern>%red(%d{yyyy-MM-dd HH:mm:ss.SSS}) %yellow([%-5p]) %highlight([%t]) %boldMagenta([%C]) %green([%L]) %m%n</pattern>
        </encoder>
    </appender>

    <!--普通信息文件输出-->
    <appender name="INFO_LOG" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--通过使用该标签指定过滤策略-->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!--标签指定过滤的类型-->
            <level>ERROR</level>
            <onMatch>DENY</onMatch>
            <onMismatch>ACCEPT</onMismatch>
        </filter>

        <encoder>
            <!--标签指定日志输出格式-->
            <pattern>[%d{yyyy-MM-dd' 'HH:mm:ss.SSS}] [%C] [%t] [%L] [%-5p] %m%n</pattern>
        </encoder>

        <!--标签指定收集策略，比如基于时间进行收集-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--标签指定生成日志保存地址，通过这样配置已经实现了分类分天收集日志的目标了-->
            <fileNamePattern>${LOG_INFO_HOME}//%d.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
    </appender>

    <!--错误信息文件输出-->
    <appender name="ERROR_LOG" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
        <encoder>
            <pattern>[%d{yyyy-MM-dd' 'HH:mm:ss.SSS}] [%C] [%t] [%L] [%-5p] %m%n</pattern>
        </encoder>

        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_ERROR_HOME}//%d.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
    </appender>

    <!--用来设置某一个包或具体的某一个类的日志打印级别-->
    <logger name="com.rudecrab.ssm.mapper" level="DEBUG"/>

    <!--必填标签，用来指定最基础的日志输出级别-->
    <root level="info">
        <!--添加append-->
        <appender-ref ref="CONSOLE_LOG"/>
        <appender-ref ref="INFO_LOG"/>
        <appender-ref ref="ERROR_LOG"/>
    </root>
</configuration>
```

### Mybatis全局设置

现在我们开始终于可以进行Spring和Mybatis的整合了。我们先在resources文件夹下新建`mybatis-config.xml`文件来对Mybatis进行全局配置，这里我习惯配置这些，根据自己的需求来就好：

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--配置全局设置-->
    <settings>
        <!--启用日志，并指定日志实现方式-->
        <setting name="logImpl" value="SLF4J"/>

        <!--启用主键生成策略-->
        <setting name="useGeneratedKeys" value="true"/>

        <!--配置启用下划线转驼峰的映射-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>

        <!--启用二级缓存-->
        <setting name="cacheEnabled" value="true"/>
    </settings>
</configuration>
```

### Spring-Myabtis整合配置

再来新建`spring-mybatis.xml`文件，这个文件就是用来做整合的！**注意啊，其中很多设置需要指定某个包，这个根据自己项目结构包名来设置**，注释写的很清楚了：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--属性文件的读取，这里读取刚才我们的数据库连接相关配置-->
    <context:property-placeholder location="classpath:database.properties" file-encoding="UTF-8"/>

    <!--配置自动扫描，如果不配置这个那么就无法使用@Autowired加载bean-->
    <context:component-scan base-package="com.rudecrab.ssm" use-default-filters="true">
        <!--这里要排除掉Controller的注解，Controller专门交给MVC去扫描，这样会就不会冲突-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--配置数据源-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <!--配置JDBC基础属性，即数据库连接相关配置-->
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!--配置连接池的设置,这个是要根据真实项目情况来配置的，随着项目的发展会不断修改-->
        <property name="initialSize" value="10"/>
        <property name="maxActive" value="100"/>
    </bean>

    <!--
    重点来了，这里配置是MyBatis的SqlSessionFactory，就是这一块配置将Spring和Mybatis整合到了一起
    如果不配置这里，你的mapper接口只能通过SqlSession来获取，十分麻烦。这里配置好后就可以通过Spring IoC来获取mapper接口了
    -->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactory">
        <!--指定数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--加载mybatis全局设置，classpath即我们的资源路径resources-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <!--配置Mybatis的映射xml文件路径-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>

    <!--指定Mybatis的mapper接口扫描包-->
    <!--注意！！！如果用的是tk.mybatis自动生成的mapper接口，一定要将org.mybatis.改成tk.mybatis-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--指定刚才我们配置好的sqlSessionFactory-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!--指定mapper接口扫描包-->
        <property name="basePackage" value="com.rudecrab.ssm.mapper"/>
    </bean>

    <!--配置事务管理器，如果不配置这个，不启动事务扫描的话，那么发生了异常也不会触发回滚-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--还得指定数据源-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--启动事务的扫描-->
    <tx:annotation-driven/>
</beans>
```

### JUnit测试

至此Spring和Myabtis已经整合配置好了，口说无凭，咱们还是得测试一下看下效果。在测试之前我们得先建立好mapper接口文件、myabtis映射xml文件、service接口和实现类：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200321224256.png)

UserMapper接口专门用来声明各种数据库操作方法，`@Repository`注解将其定义为Spring所管理的Bean：

```java
@Repository
public interface UserMapper {
    /**
     * 从数据库中查询出所有的User对象
     * @return User对象集合
     */
    List<User> selectAll();
}
```

UserMapper.xml映射文件用来写方法对应要执行的SQL语句：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.rudecrab.ssm.mapper.UserMapper">
    <!--开启缓存-->
    <cache/>

    <!--从数据库中查询出所有的User对象-->
    <select id="selectAll" resultType="com.rudecrab.ssm.entity.User">
        select * from user
    </select>

</mapper>
```

UserService接口用来声明关于User的业务方法：

```java
public interface UserService {
    /**
     * 从数据库中查询出所有的User对象
     * @return User对象集合
     */
    List<User> getAll();
}
```

UserServiceImpl实体类用来实现关于User的业务逻辑，`@Service`注解和`@Repository`注解用处一样，将其定义为Bean。`@Transactional`注解为声明式事务，如果该业务层的方法有异常抛出则会触发事务回滚。然后使用`@Autowired`注解在私有属性上，自动加载Bean，无需我们手动创建UserMapper了：

```java
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }
}
```

相关类和文件都建好了，现在我们在test文件夹下建立一个测试类UserServiceTest，**一定要在测试类上加上那两个注解**，否则无法正常使用Spring相关功能：

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void getAll() {
        System.out.println(userService.getAll());
        System.out.println(userService.getAll());
    }
}
```

运行后我们就可以看到运行结果了：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200321230150.png)

可以看到结果正常显示，并且日志也打印在了控制台上。**这代表我们已经完成了Spring和Mybatis的整合！**

### 思路

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200322155038.png)



## SpringMVC

### spring-mvc.xml

我们接下来配置SpringMVC，在resources目录下新建`spring-mvc.xml`文件进行配置。**注意啊，其中设置需要指定某个包，这个根据自己项目结构包名来设置**：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--配置视图解析器，这样控制器里就直接返回文件名就好了-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/views/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--配置静态资源过滤，不然静态资源比如css是访问不到的-->
    <mvc:default-servlet-handler/>

    <!--配置扫描的包-->
    <context:component-scan base-package="com.rudecrab.ssm.controller" use-default-filters="false">
        <!--只扫描controller,实际开发中最好用这种方式来写，这边MVC就只扫描controller，就不会IOC那边冲突，否则事务会被覆盖，IOC那边就要排除这个controller-->
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--启用MVC的注解-->
    <mvc:annotation-driven/>
</beans>
```

### web.xml

最后一个配置自然就是在web.xml里进行整合了，主要配置三点：

1. 配置Spring IOC容器，为了mybatis做准备
2. 配置SpringMVC的前端控制器
3. 配置字符编码过滤器

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

    <!--1.配置Spring IOC容器的创建，如果不配置这个，Mybatis就在web应用里无法使用-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <!--spring和mybatis整合配置文件路径-->
        <param-value>classpath:spring-mybatis.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!--2.配置SpringMVC的前端控制器-->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!--SpringMVC整合配置文件路径-->
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--3.配置字符编码过滤器-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

### 最终测试

以上，所有的配置都弄好了，那么接下来咱们就跑一个最简单的web项目来看看是否整合成功！还记得我们在在`spring-mvc.xml`文件中配置的视图解析前缀嘛，我们在**/WEB-INF/views/**文件夹下新建一个index.jsp文件：

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页-RudeCrab</title>
</head>
<body>
<%--循环提取userList中的元素--%>
<c:forEach var="user" items="${userList}">
    <ul>
        <li>${user}</li>
    </ul>
</c:forEach>
</body>
</html>
```

接下来再在controller包下新建一个控制器类来定义访问接口：

```java
@Controller
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getList")
    public String getList(Model model) {
        // 将数据存到model对象里，这样jsp就能访问数据
        model.addAttribute("userList", userService.getAll());
        // 返回jsp文件名
        return "index";
    }

    @GetMapping("/getJson")
    @ResponseBody
    public List<User> getList() {
        // 如果想做前后端分离的话可以加上@ResponseBody注解，直接返回数据对象，这样前端就可以通过获取json来渲染数据了
        return userService.getAll();
    }
}
```

然后我们启动Tomcat，在浏览器中访问接口：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200322140956.png)

可以看到我们成功的访问到了数据，至此SSM完全整合完毕！

# 总结

## 思维导图

整体的整合配置思路已经画好思维导图了，其中每个节点上都写好了比较详细的备注，可以下载文件后观看。文件放在末位的github地址中：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200322170120.png)

## github地址

https://github.com/RudeCrab/rude-java/tree/master/project-practice/ssm

上面包含了整个项目，clone下来用idea打开即可运行！同时也放上了思维导图文件。如果对你有帮助麻烦点一个star，项目中还有其他的【项目实践】，将来我也会不断更新更多的项目实践！