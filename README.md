## shipper by spring

shipper的执行过程如下

1. 执行groovy
2. 代理基本域的过程
3. 查找基本域中定义的handler
4. 使用handler构造shipperContext
5. 使用shipperContext构造shipperTask
6. 按照input|filter|output的过程执行
7. 执行handler之前进行dsl中定义的初始化属性
8. 执行handler

借用spring的力量可以干涉其中的两个部分

1. 查找handler
2. 初始化属性

### 查找handler

对于基本的shipper来说,handler的查找范围在`classpath:META-INFO/shipper.properties`定义的包中中,并且其名字只能为其类名.

而借用了spring的力量之后,handler可以从spring的容器中进行查找.

查找过程如下:

1. 在spring容器中查找,若找到直接返回
2. 在handlerBuilder中查找,若无法找到则抛出异常

### 初始化属性

借用`spel`的力量,使得shipper能够访问env

shipper在最开始的时候执行`spel`,也就是说`execute`时刻的环境将决定了spel的执行结果.

由于在shipper中也可以使用`${...}`的形式作为字符串模板,因此解析的过程如下

1. 解析spel,若无法获取到值,则不替换spel
2. 运行时若执行到`${...}`则进行字符串模板解析,若无法解析到则抛出异常

