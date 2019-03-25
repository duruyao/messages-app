<h1 id="0">Java 编程和文档约定</h1>

---

**List**
* [Java 编程和文档约定](#0)
    * [命名和大小写约定](#1)
        * [包](#1.1)
        * [引用类型](#1.2)
        * [方法](#1.3)
        * [字段和常量](#1.4)
        * [参数](#1.5)
        * [局部变量](#1.6)
    * [Java文档注释](#2)

---

<h2 id="1">命名和大小写约定</h2>

以下约定几乎全球通用，而且会影响你定义的类的公开API，因此请认真遵守。

- **<p id="1.1">包</p>**

公开可见的包通常使用唯一的包名。十分常见的做法是，将网站域名倒过来，放在包名前.所有包名都应该用小写字母。
e.g.
```java
package com.google.map;
```
如果包只在应用内部使用，而且打包成JAR文件分发，那么这种包公开不可见，无需遵守上述约定。此时，经常使用应用名称做包名或包名的前缀。


- **<p id="1.2">引用类型</p>**

类型名称应以大写字母开头，且大小写混用。若类名包含多个单词，每个单词首字母都要大写。
e.g.
```java
public class StringBuffer {

}
```
若类型名称中有一部分是简称，那么简称可以全用大写字母。
e.g.
```java
public class HTMLParser {

}
```
类和枚举类型是为了表示对象，因此类名要使用名词。如果接口是用来为实现这个类提供额外信息的，一般使用形容词命名。如果接口的作用更像是抽象类，那么应该使用名词命名。

- **<p id="1.3">方法</p>**

方法名始终以小写字母开头，若包含多个单词，除第一个单词外，其他单词首字母都要大写。即“驼峰式”命名法。
e.g.
```java
public void insert() {

}

public void insertObject() {

}
```
尽量使方法名第一个单词为动词，选择简短的名称，同时避开太过通用的名称。

- **<p id="1.4">字段和常量</p>**

非常量字段的命名规定与方法一样。声明为 `static final` 的常量，其名称应该使用全大写形式。若常量名包含多个单词，应使用下划线分割。
 e.g.
 ```java
public class Example {
    public static final int DAYS_PER_WEEK = 7;
    private String dateName;
}
 ```

- **<p id="1.5">参数</p>**

方法的参数的大小写约定和非常量字段一样。
e.g.
```java
double CalculateRectanglePerimeter(double rectangleHeight, double rectangleWidth) {
    return (rectangleHeight + rectangleWidth) * 2;
}
```

- **<p id="1.6">局部变量</p>**

局部变量的命名方法往往与方法和字段的命名约定一样。

<h2 id="2">Java文档注释</h2>

---

**<center>Unfinished</center>**

---