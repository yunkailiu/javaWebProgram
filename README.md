# javaWebProgram

java web部分demo项目，便于自己练手和学习知识点，记录不懂的地方，并且将一些方便使用的框架记录下来，便于以后使用。

----------

记录知识点博客：[这里](https://www.cnblogs.com/yunkaiL/p/10374701.html)

----------
demo1：企业门户网站

这是基于jsp+JavaScript+MySQL实现的，每一个页面就是一个jsp文件，该demo值得学习的地方：轮播图的实现，与数据库交互的CRUD操作

- 当页面上存在公共区域时，可以另起一个新的jsp文件，直接用标签开始构造即可，无需完整结构，最后使用`<%@ include file="xxx.jsp" %>`引入即可。
- css样式文件完全可以写在新文件中，使用`<link href="css/xxx.css" rel="stylesheet" type="text/css" media="all">`引入即可。
- js文件也可以写在新文件中，使用`<script src="js/xxx.js"></script>`引入即可。
- jsp文件中使用java语言在<%%>插入即可，可以在java的类文件中直接设计好html格式，最终直接插入到标签里即可。
- News.java中有完整的连接数据库的步骤，包括：建立与数据库连接的通道、定义sql语句、执行sql语句、关闭通道。
- Function.java中的PageFront函数为分页操作，可以参考。
- login.jsp中有简单的表单用户登录方法，当然可以用其他的。
- 后台的模板样式可以参考news.jsp文件

