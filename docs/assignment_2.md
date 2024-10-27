# 迭代二

## 写在前面

同学们，我们用了两周时间完成了 ACP 的部分功能，包括注册、创建课程、选课等。在 ACP-2 中，我们将会实现数据导入导出等一系列相关命令，同时完善教学机制，加入课表相关操作。

在开始编码之前，建议大家先**仔细阅读需求说明**和文末的**HINTS**

编写好的代码需要经过 patpat 评测。通过所有的测试点后，**需将整个项目文件打包上传到云平台**，即可完成本次实验。希望大家能够认真完成，**不作弊不抄袭，不偷取数据点**。

>**不要以身试法，不要抱有侥幸心理，认为自己学术不端的行为不会被发现。** :punch:

## 实验说明

### 一、实验目标

1. 熟练掌握 Java 文件操作。
2. 了解文件输入输出流的区别与用法。
3. 了解重定向概念。
4. 了解 Java 序列化和反序列化技术。
5. 了解异常的使用。

### 二、实验的重难点

1. 文件操作。
2. 重定向。
3. Java 异常处理。
4. Java 序列化和反序列化技术。

### 三、关键技术

#### 1. `Java.io` 包

##### `File` 类

* `File` 类可以表示一个文件，还可以表示一个目录（Directory）。这主要来自于UNIX的“**一切皆文件**”的设计思想。
* `File` 类的对象**不能直接对文件进行读写操作**，只能修改文件的属性，比如文件的名称，修改日期的日期。

* 举例如下

```java
import java.io.*;
public class Test02 {
 public static void main(String[] args) {
 String path = "C:/windows/"; //如果路径出现问题，可以尝试使用反斜杠。此时，例子中的路径应该表示为: "C:\\windows\\"。如果仍然存在错误，也许可以尝试使用LINUX的路径格式，也即: "/c/windows/"
 File f = new File(path, "notepad.exe");
 System.out.println("============================================");
 System.out.println("文件长度：" + f.length() + "字节");
 System.out.println("文件或者目录：" + (f.isFile() ? "是文件" : "不是文件"));
 System.out.println("文件或者目录：" + (f.isDirectory() ? "是目录" : "不是目录"));
 System.out.println("最后修改日期：" + new Date(f.lastModified()));
 System.out.println("文件名称：" + f.getName());
 System.out.println("文件路径：" + f.getPath());
 System.out.println("绝对路径：" + f.getAbsolutePath());
 }
}
```

##### `Stream` 流

* 流是指一**连串流动的数据信号**，通过先进先出的方式接收和发送数据。
* 数据流包括**输入流和输出流**，输入输出流又分为字节流和字符流。

| 类型 | 说明 | 用法 |
| ------ | :--------------------------------------- | :----------------------------------------------------------- |
| 字节流 | 以字节为基本单位 ，无法读取中文字符 | 继承 `InputStream`（输入字节流）类和 `OutputStream`（输出字节流）类 |
| 字符流 | 两个字节为基本单位，专门处理字符串和文本 | 继承 `Reader`（读取流）类和 `Writer`（写入流）类 |

欲了解更多信息，可以查看**文末的提示**。

#### 3. Java 异常处理

异常处理是 Java 中**处理运行时错误**的一种十分有效的手段，可以实现逻辑与错误处理的分离，从而更优雅地处理程序中出现的错误。异常的出现表明你的程序中出现了错误，但并不是所有的错误都是致命的，因此你可以通过捕获异常来相应地处理这些情况。

>同学们评测时出现的 RE 往往就是因为有异常没有被捕获，从而导致程序异常结束。可以试试用 `try` 和 `catch` 将 `main` 方法包裹起来，输出捕获的异常，看看到底是什么导致了 RE。

>使用Java的异常抓抛模型，在很大程度上能够减少因为`if-else`分支导致的“箭头状“缩进，而且，也能够更清晰地指出我们处理错误情况的方式，让我们的代码更加清晰。 :wink:

#### 4. 目录、文件和路径

目录就是文件夹，目录中有文件，同时，目录也是一种特殊的文件。程序运行总是在一个目录下（当前目录、工作目录），一般是项目所在的目录，**当前目录可以用 `.` 指代（或者不写），当前目录的父目录可以用 `..` 指代**。

“C 盘下的 User 下的 Desktop 下的 Test 下的 src 下的 com 下的 Test.java 文件”，我们就可以用这样的路径来描述它的位置：`C:/User/Desktop/Test/src/com/Test.java` （这种写法不太符合 Windows 的规范但是足够我们理解路径了）。**如果这里的 Test 就是我们项目，那么程序就运行在该目录下，则 Test.java 也可以用 `./src/com/Test.java` 表示，其中的 `./` 也可以省略。**对于前者，路径从一个根位置出发，一路深入到指定位置，这种路径称为**绝对路径**；对于后者，路径从一个参考点出发，目标位置根据参考点而定，这种路径叫做**相对路径**。

以下示例有助于我们理解相对路径 —— 本次迭代主要涉及的文件操作问题。假定当前位置就是项目 Test，则其他文件/目录的相对路径以“注释”的形式标注。

```
Desktop # 父目录: ..
└── a.jpg # ../a.jpg
 ├── Test # 当前目录: .
 │ ├── src # ./src 或 src
 │ │ ├── com # ./src/com 或 src/com
 │ │ │ ├── Test.java # ./src/com/Test.java 或 src/com/Test.java
 │ │ │ ├── Hello.java # ./src/com/Hello.java 或 src/com/Hello.java
 │ │ │ └── Person.java # ./src/com/Person.java 或 src/com/Person.java
 │ │ └── ui # ./src/ui 或 src/ui
 │ │ ├── MainWindow.java #./src/ui/MainWindow.java 或 src/ui/MainWindow.java
 │ │ └── Decoration.java #./src/ui/Decoration.java 或 src/ui/Decoration.java
 │ ├── out # ./out 或 out
 │ │ ├── Test.class # ./out/Test.class 或 out/Test.class
 │ │ ├── Main.class # ./out/Main.class 或 out/Main.class
 │ │ ├── ...
 │ └── data # ./data 或 data
 │ └── a.txt # ./data/a.txt 或 data/a.txt
 └── OOP # ../OOP
 ├── OOP_01.pdf # ../OOP/OOP_01.pdf
 └── OOP_02.pptx # ../OOP/OOP_02.pptx
```

>如果同学们使用的是IDEA进行开发，那么**默认的`.`目录其实是项目的根目录**，在进行文件重定向操作的时候，请勿混淆！:thinking:

#### 5. **序列化与反序列化**

* Java**序列化**就是指把 Java 对象转换为字节序列的过程。
* Java**反序列化**就是指把字节序列恢复为 Java 对象的过程。

序列化最重要的作用：在传递和保存对象时，保证对象的**完整性和可传递性**。对象转换为有序字节流，以便在网络上传输或者保存在本地文件中。

反序列化的最重要的作用：根据字节流中保存的对象状态及描述信息，通过反序列化重建对象。

总结：核心作用就是对象状态的保存和重建。（整个过程核心点就是字节流中所保存的对象状态及描述信息）。

### 四、相关资料

1. 文件相关操作见：<http://c.biancheng.net/view/1133.html>
2. 流相关操作见：<https://www.runoob.com/java/java-files-io.html>
3. 异常处理：<https://www.runoob.com/java/java-exceptions.html>
4. 序列化和反序列化：

* 理论详解：<https://blog.csdn.net/weixin_45433031/article/details/115364830>

* 实践：<https://www.cnblogs.com/com-Jacob/p/16207339.html>

>例如，你实例化了一个对象，名字为小明，此时这个小明对象**只在你程序运行期间存在，运行结束后就消失了**，但是你可以通过序列化，将小明对象转换为字节序列（在运行期间这个对象本质也只是一串数据）**存储到本地文件中，**之后即可通过反序列化将这个文件中存储的字节序列**转换回一个名字叫小明的对象**。

### 五、DDL及其他要求

1. **本次实验持续 3 周**，截止时间以云平台为准。

---

## 实验内容

### 一、命令概览

本次迭代需要完成的命令如下：

| 需求 | 命令符 | 说明 |
| ---- | ------------------ | ------------ |
| 新增 | switch | 切换用户 |
| 修改 | selectCourse | 选择课程 |
| 新增 | inputCourseBatch | 批量导入课程 |
| 新增 | outputCourseBatch | 批量导出课程 |
| 新增 | listStudent | 查看选课学生 |
| 新增 | removeStudent | 移除选课学生 |
| 新增 | listCourseSchedule | 查看课表 |

>对于修改中新增的错误情况，其判断顺序位于**原有错误情况之后，但仍然在成功情况之前**。 :thinking:

### 二、功能描述

#### 1. 切换用户

> 1. 正如迭代一中所说，为了能够更好地模拟多人共同使用系统的情形，我们的系统支持**多个不同用户同时登录**。
> 2. 在一个用户登录之后，我们**默认该用户为当前用户**，也可以使用`swtich`进行用户的切换。
> 3. 在执行`quit`后，应该**强制登出所有的用户**, 请勿忘记。

##### 1.1 格式说明

| 命令符 | 参数 |
| ------ | ------ |
| switch | 学工号 |

##### 1.2 命令反馈说明

###### 1.2.1 成功切换用户

```
Switch to 学工号
```

###### 1.2.2 参数不合法

```
Illegal argument count
```

###### 1.2.3 学工号不合法

```shell
Illegal user id
```

###### 1.2.4 用户不存在

```
User does not exist
```

###### 1.2.5 指定用户未登录

```
学工号 is not online
```

>`switch`命令**不会更改用户的登录的顺序**。也就是说，在最后退出的时候，只需按照`login`的顺序依次退出便可。 :thinking:

#### 2. 选择课程

##### 2.1 格式说明

> 1. 新增课程人数要求，当一门课程选课学生人数**大于等于30时**，学生无法再选择该课程。
> 2. 学生选择课程后课程自动加入学生课表，每个学生都有且**仅有一个课表**，课表中课程需要**保证时间不冲突**。

##### 2.2 命令反馈说明

###### **2.2.1 课程容量已满**

已选课人数大于等于30人。

```
Course capacity is full
```

#### 3. 批量导出课程

> 1. 批量导出课程的命令符为`outputCourseBatch`。
> 2. **老师**可以将**当前**ACP系统中的**自己的课程**都导出到**由路径指定的文件中**。
> 3. 你必须使用**序列化的技术**进行导出。
> 4. **文件需放在项目根目录的 `data` 路径下**。例如：若路径为 `./help.txt`，则序列化文件保存路为`./data/help.txt`；若路径为 `data/help.txt`，则序列化文件保存路径为 `./data/data/help.txt`，其中`.`代表项目的根目录路径。
> 5. 如果文件**不存在，则创造**；如果文件**存在，则进行写覆盖操作**。
> 6. **不需要考虑路径的合法性问题**，我们保证，此命令牵涉的路径表示的，**均是合法的.txt文件.**

>1. 为和现实情况维持一致，我们只会验证课程**最基本的信息的正确性，而基本的验证的方法是通过`inputCourseBatch`进行的**。导出的课程只需要保留**课程名称、时间段、学分和学时**的信息。 :thinking:
>2. 如果无特殊说明，我们的文件的存放位置都在`项目目录/data/`或其子目录下。 :thinking:
>3. 关于文件的**合法性问题**，详见**文末提示**。 :thinking:

##### 3.1 格式说明

| 命令符 | 参数 |
| ----------------- | ---- |
| outputCourseBatch | 路径 |

##### 3.2 命令反馈说明

###### 3.2.1 导出成功

```shell
Output course batch success
```

###### **3.2.2 参数不合法**

```
Illegal argument count
```

###### **3.2.3 当前无用户在线**

```
No one is online
```

###### **3.2.4 登录用户身份不是 `Teacher`**

```
Permission denied
```

#### 4. 批量导入课程

> 1. 批量导入课程的命令符为 `inputCourseBatch`。
> 2. **老师**可将由路径指定的文件中的所有课程加入到**自己的课程中**。
> 3. 导入到一定阶段时，如果**现有课程数量已然为10**，则输出相应的错误信息。
> 4. 如果课程数量未达上线，但是**名字或时间**冲突，则**保留老师现有的课程**。
> 5. 新建的课程的**课程号**的分配与`createCourse`类似，依旧是**全局分配的**。
> 6. 新建课程成功后，**需要打印相关信息**，其格式和`createCourse`中的类似。
> 7. 你必须使用到**反序列化的技术进行导入**。
> 8. **文件需放在项目根目录的 `data` 路径下**。

>注意：该命令一定是**配合批量导出课程使用的，**这意味着我们只会考察最基本的课程信息，因此大家**不需要重新设计**自己的课程类。

##### 4.1 格式说明

| 命令符 | 参数 |
| :--------------- | :--- |
| inputCourseBatch | 路径 |

##### 4.2 命令反馈说明

###### **4.2.1 导入成功**

```
Input course batch success
```

###### **4.2.2 参数不合法**

```
Illegal argument count
```

###### **4.2.3 当前无用户在线**

```
No one is online
```

###### **4.2.4 权限不为 `Teacher`**

```
Permission denied
```

###### 4.2.5 文件路径对应的文件不存在

```
File does not exist
```

###### 4.2.6 文件路径对应的文件是目录

```shell
File is a directory
```

###### 4.2.7 课程数量已达上限

```shell
Course count reaches limit
```

###### 4.2.8 课程名字已存在

```shell
Course name already exists
```

###### 4.2.9 课程时间冲突

```shell
Course time conflicts
```

###### 4.2.10 导入新建课程成功

```shell
Create course success (courseId: C-X)
```

>1. 在UNIX中，我们认为“一切皆文件"。
>2. 输出4.2.7-4.2.10的信息后，**并不会导致该命令执行失败**。 :stuck_out_tongue_winking_eye:

#### 5. 查看选课学生

> 1. 仅**老师和管理员**可查看选课学生。由于课程号是唯一的，因此老师和管理员输入该命令的结果是一致的。
> 2. 按照学工号进行**升序排序**。
> 3. 我们认为**博士>学术硕士>专业硕士>本科生**

##### 5.1 格式说明

| 命令符 | 参数1 |
| :---------- | :----- |
| listStudent | 课程号 |

##### 5.2 命令反馈说明

###### **5.2.1 成功输出信息格式为**

```
userId1: userName1
userId2: userName2
...
List student success
```

>上述输出的数字1，2仅仅表示第一个学生和第二个学生，在真正进行输出的时候，并不需要某个数字来表明顺序。例如，现在有`22373009 haha`和`22373012 xixi`都选择了课程号为`C-1`的课，那么输入`listStudent C-1`的结果为：
>
>```shell
>22373009: haha
>22373012: xixi
>List student success
>```

###### **5.2.2 参数不合法**

```
Illegal argument count
```

###### **5.2.3 当前无用户在线**

```
No one is online
```

###### **5.2.4 权限不为 `Teacher` 和 `Administrator`**

```
Permission denied
```

###### **5.2.5 课程号不合法**

```
Illegal course id
```

###### **5.2.6 课程不存在**

对于管理员而言是**课程编号未注册或已注销**，对于老师而言是课程编号未注册或已注销或者该课程号**不属于老师**。

```
Course does not exist
```

###### **5.2.7 无选课学生**

```
Student does not select course
```

#### 6.移除学生

> 1. 老师和管理员可以通过此命令移除某个学生。此处的”移除“是指**从课程中移除**，而不是在平台中踢出学生。
> 2. **老师：**无可选参数时，若该学生选择了老师的课程，则老师**从自己所有的课程中移除该学生；**有可选参数时，则将该学生从**指定的且是自己的课程中移除**。
> 3. **管理员**：无可选参数时，若该学生存在已选课程，管理员可以**从所有已选课程中移除该学生；**有可选参数时，则将该学生从**指定的课程中移除**。

##### 6.1 格式说明

| 命令符 | 参数1 | 可选参数 |
| :------------ | ----- | :------- |
| removeStudent | 学号 | [课程号] |

##### 6.2 命令反馈说明

###### **6.2.1 移除学生成功**

```
Remove student success
```

###### **6.2.2 参数数量不合法**

```
Illegal argument count
```

###### **6.2.3 当前无用户在线**

```
No one is online
```

###### **6.2.4 登录用户身份不是 `Teacher` 或 `Administrator`**

```
Permission denied
```

###### **6.2.5 学号不合法**

```
Illegal user id
```

###### **6.2.6 学号对应的用户未注册**

```
User does not exist
```

###### **6.2.7 学号对应的用户身份不是 `Student`**

```
User id does not belong to a Student
```

**一个参数**

###### **6.2.8 学生没有已选课程**

对于老师而言是学生**没有选择自己的课程**，对于管理员而言是**学生尚未选择课程**。

```
Student does not select course
```

**两个参数**

###### 6.2.9 课程号不合法

```shell
Illegal course id
```

###### 6.2.10 课程不存在

对管理员而言，课程编号**未注册或已注销**；对老师而言，课程编号未注册或已注销或者**不属于自己**。

```shell
Course does not exist
```

###### **6.2.11 学生未选择该课程**

如果课程存在，但是相应的学生却**没有选择**，则依然输出错误信息。

```
Student does not select course
```

#### 7. 查看课表

> 1. **仅学生**可查看自己的课表（无参数），**仅管理员**可以查看学工号对应的**学生（如果是）的课表（有参数）**
> 2. 按照课程**时间的先后顺序**进行排序。星期日期小的先被打印；星期日期相同时，上课时间更早的先被打印。
> 3. 每个字段的输出形式，其实和`listCourse`的形式相同。

##### 7.1 格式说明

| 命令符 | 可选参数 |
| :----------------- | :------- |
| listCourseSchedule | [学工号] |

##### 7.2 命令反馈说明

###### 7.2.1 成功输出信息

```
课程时间 课程名称 课程学分 课程学时 任课老师名称
课程时间 课程名称 课程学分 课程学时 任课老师名称
课程时间 课程名称 课程学分 课程学时 任课老师名称
...
List course schedule success
```

例如：

```shell
1_1-2 OO 2.0 36 Tea_a
List course schedule success
```

###### **7.2.2 参数数量不合法**

```
Illegal argument count
```

###### **7.2.3 当前无用户在线**

```
No one is online
```

**无参数**

###### **7.2.4 权限不为 `Student`**

```
Permission denied
```

###### 7.2.5 学生尚未选课

```shell
Student does not select course
```

**有参数**

###### 7.2.6 权限不为`Administrator`

```
Permission denied
```

###### 7.2.8 学工号不合法

```shell
Illegal user id
```

###### 7.2.7 学工号对应的用户不存在

```
User does not exist
```

###### 7.2.8 学工号对应的用户不是学生

```
User id does not belong to a Student
```

###### 7.2.9 学生尚未选课

```shell
Student does not select course
```

### 三、HINTS

#### 3.1 迭代中的文件合法性问题

为了给同学们减少压力，在我们的迭代中，只有在进行文件的读取时，文件路径可能是不合法的，而不合法的理由只有两个：**文件不存在和文件是一个目录**。在进行文件的输出时，给出的文件路径是一定合法的。一方面，如果路径之指定的**文件不存在，则直接创造**；另外一方面，保证路径最终指向的文件**为.txt文件**。

另外，我们并**不会对文件路径本身的合法性进行判断**，也即，我们给出的文件路径是符合规范的。

#### 3.2 封装提示

鉴于**文件操作**本身其实就会涉及到很多其他的概念，例如**异常处理、重定向、字符串拼接**等等，如果同学们并没有进行适当的封装，将对文件的操作零散地分布在各个类中，一旦出现错误，你就必须定位错误的原因，繁琐的DEBUG流程会让人苦不堪言。

另外，当你修复好一处文件操作的BUG时，相同或者类似的文件操作多半也会存在类似的BUG，这也就代表着你需要进行更加无趣的一致性维护。

因此，我们建议把所有的文件操作用**类封装起来，**比如判断文件的合法性、创建文件、打开文件、输出文件、删除文件夹。只要验证了一个文件操作自身逻辑的正确性，在出现BUG时，就能够很轻易地进行排查。另外，如果你想进行相应的修改，则**只需要修改一处的代码便可**。

#### 3.3 测试提示

开始测试前务必**把前一次测试生成的文件删去**，建议在每次输入 `quit` 指令后，调用工具类的方法删除 `data` 文件夹。

#### 3.4 流在Java中的使用示例

实际上，Java中主要有两种流，分别是字节流和字符流。对于字节流，其最明显的特征是，输入和输出分别用`InputStream`和`OutputStream`进行表示，而对于字符流，其最明显的特征是，输入和输出分别用`Reader`和`Writer`表示。字节流和字符流的功能差距在于，**字节流的基本处理单元是单个字节，而字符流的基本处理单元是Unicode码元（大小2字节）**。这意味着，如果你的流中含有中文，使用字符流是更保险的选择。

因为字符流和字节流的使用十分相似，其各自的子类类型也比较一致，因此在此只给出字节流的使用示例，字符流的使用方式可以类推。

>1. 在每个类中的`private String in`和`private String out`分别代表的是**输入文件和输出文件**。你可以根据你自己的需要，做出相应的调整。
>2. 下面的代码中，使用到了异常。其中`try-with-resources`的写法，可以在出现异常后，将我们应该关闭的资源（在本例中，是各个流）**自动进行关闭**，而无需我们人为地去书写相应的`close()`语句。一来是麻烦，二来是可能忘记。
>3. 下面的代码中，出于简便的考虑，对于所有抛出的异常都是使用`Exception`进行捕获的，但这是一个**十分不好的习惯**。同学们在今后进行开发的时候，**切勿使用这样的方式！！！**
>4. 同学们如果想要测试以下代码的结果，可以直接进行复制到同一目录下。**注意：一定要给出相应的文件路径！！！**

```Java
// StreamTest.java
public abstract class StreamTest {
 public abstract void doStreamTest();
}
```

```Java
// FileStreamTest.java
import java.io.*;
import java.util.Arrays;

// If you want to read or write files IN BYTE, then you can
// use FileStream.
public class FileStreamTest extends StreamTest {
 private String in = "./src/in.txt";
 private String out = "./src/out.txt";
 public FileStreamTest(){;}
 public FileStreamTest(String inFile, String outFile){
 this.in =inFile;
 this.out = outFile;
 }
 private void testFileStream(String inFile, String outFile){
 byte[] bytes = new byte[100];
// try with resources.
 try(FileInputStream in = new FileInputStream(inFile);
 FileOutputStream out = new FileOutputStream(outFile);){
 int byteRead = 0;
 while((byteRead = in.read(bytes)) != -1){
 System.out.println("the bytes going to be write by FileOutputStream: " + Arrays.toString(bytes));
 out.write(bytes, 0, byteRead);
 }
 } catch (Exception e){
 e.printStackTrace();
 }
 }

 @Override
 public void doStreamTest() {
 System.out.println("do file stream test...");
 testFileStream(in, out);
 System.out.println("finish file stream test...");
 }
}
```

```Java
// DataStreamTest.java
import java.io.*;

// When you want to read or write elementary data such as Integer, Double, etc.
// Then it is recommended to use DataStream.
public class DataStreamTest extends StreamTest {
 private String out = "./src/out.txt";
 public DataStreamTest(String outFile){
 out = outFile;
 }
 public DataStreamTest(){;}
 private void testDataOutputStream(String outFile){
 try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(outFile)))){
 dos.writeBoolean(true);
 dos.writeByte((byte)0x12);
 dos.writeChar((char)0x34);
 dos.writeShort(0x1234);
 dos.writeInt(0x12345678);
 dos.writeLong(0x123456789ABCDEFL);
 dos.writeUTF("ABCDEFG");
 }catch (Exception e){
 e.printStackTrace();
 }
 }
 private void testDataInputStream(String inFile){
 try(DataInputStream in = new DataInputStream(new FileInputStream(new File(inFile)))){
 System.out.println("what will be demonstrated are the data read by DataInputStream.");
 System.out.printf("readBoolean():%s\
", in.readBoolean());
 System.out.printf("readByte():0x%s\
", in.readByte());
 System.out.printf("readChar():0x%s\
", in.readChar());
 System.out.printf("readShort():0x%s\
", in.readShort());
 System.out.printf("readInt():0x%s\
", Integer.toHexString(in.readInt()));
 System.out.printf("readLong():0x%s\
", Long.toHexString(in.readLong()));
 // If you didn't use DataOutputSteam to write a UTF,
 // then it will throw an Exception!!
 System.out.printf("readUTF():%s\
", in.readUTF());
 }catch (Exception e){
 e.printStackTrace();
 }
 }
 @Override
 public void doStreamTest() {
 // usually, we use DataOutputStream with DataInputStream.
 System.out.println("do data stream test...");
 testDataOutputStream(out);
 testDataInputStream(out);
 System.out.println("finish data stream test...");
 }
}

```

```Java
//BufferedStreamTest.java
import java.io.*;
import java.util.Arrays;

// If you want to use BUFFER to make I/O operation much more faster,
// then you can use BufferedStream.
public class BufferedStreamTest extends StreamTest{
 private String in = "./src/in.txt";
 private String out = "./src/out.txt";
 public BufferedStreamTest(String inFile, String outFile){
 in = inFile;
 out = outFile;
 }
 public BufferedStreamTest(){;}
 private void testBufferedStream(String inFile, String outFile){
 byte[] bytes = new byte[100];
 int byteRead = 0;
 try(
 BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(inFile)));
 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(outFile)));
 ){
 while((byteRead = bis.read(bytes, 0, bytes.length)) != -1){
 System.out.println("the bytes going to be write by BufferedOutputStream: " + Arrays.toString(bytes));
 bos.write(bytes, 0, byteRead);

 }
 }catch(Exception e){
 e.printStackTrace();
 }
 }
 @Override
 public void doStreamTest() {
 System.out.println("do buffered stream test...");
 testBufferedStream(in, out);
 System.out.println("finish buffered stream test...");
 }
}
```

```Java
// ObjectStreamTest.java
import java.io.*;

// If you want to read or write ANY OBJECT, then you can
// use ObjectStream.
public class ObjectStreamTest extends StreamTest{
 private String out = "./src/out.txt";
 public ObjectStreamTest(String outFile){
 out = outFile;
 }
 public ObjectStreamTest(){;}
 private void testObjectInputStream(String inFile){
 try(
 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(inFile)))
 ){
 Object o = (String)ois.readObject();
 System.out.println("Object read by ObjectInputStream: " + o.toString() );
 }catch(Exception e){
 e.printStackTrace();
 }
 }
 private void testObjectOutputStream(String outFile){
 try(
 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(outFile)))
 ){
 oos.writeObject("MuJue");
 }catch(Exception e){
 e.printStackTrace();
 }
 }
 @Override
 public void doStreamTest() {
 System.out.println("do object stream test...");
 testObjectOutputStream(out);
 testObjectInputStream(out);
 System.out.println("finish object stream test...");
 }
}
```

```Java
/**
 * &#064;Classname Test
 * &#064;Description ttt
 * &#064;Date 2024/9/2 9:29
 * &#064;Created MuJue
 */
public class Test {
 private static void streamTest(){
 StreamTest st = new FileStreamTest();
 st.doStreamTest();
 st = new DataStreamTest();
 st.doStreamTest();
 st = new BufferedStreamTest();
 st.doStreamTest();
 st = new ObjectStreamTest();
 st.doStreamTest();
 }
 public static void main(String[] args){
 streamTest();
 }
}
```

### 四、测试样例

#### 1-4命令

##### 输入

```shell
register 00001 Tea_a 222Bb$ 222Bb$ Teacher
register 00002 Tea_b 333Bb$ 333Bb$ Teacher
register 00003 Tea_c 444Bb$ 444Bb$ Teacher
register AD001 Admin 888ADad% 888ADad% Administrator
login 00001 222Bb$
login 00002 333Bb$
login 00003 444Bb$
login AD001 888ADad%
createCourse OO 1_3-4 2.5 45
createCourse CO 4_7-8 4.5 81
createCourse AL 3_1-2 3.0 54
listCourse
switch 00001
createCourse OO 1_3-4 2.5 45
listCourse
switch 00002
createCourse CO 4_7-8 4.5 81
listCourse
switch 00003
createCourse AL 3_1-2 3.0 54
listCourse
switch AD001
listCourse
switch 00001
cancelCourse C-1
switch 00002
cancelCourse C-2
switch 00003
cancelCourse C-3
switch 00001
createCourse AA 1_1-2 2 36
createCourse BB 3_3-4 2 36
createCourse CC 5_5-6 2 36
outputCourseBatch c1.txt
listCourse
switch 00002
inputCourseBatch c1.txt
listCourse
switch 00003
inputCourseBatch c1.txt
inputCourseBatch c1.txt
listCourse
switch AD001
listCourse
quit
```

##### 输出

```shell
Register success
Register success
Register success
Register success
Welcome to ACP, 00001
Welcome to ACP, 00002
Welcome to ACP, 00003
Welcome to ACP, AD001
Permission denied
Permission denied
Permission denied
Course does not exist
Switch to 00001
Create course success (courseId: C-1)
C-1 OO 1_3-4 2.5 45
List course success
Switch to 00002
Create course success (courseId: C-2)
C-2 CO 4_7-8 4.5 81
List course success
Switch to 00003
Create course success (courseId: C-3)
C-3 AL 3_1-2 3.0 54
List course success
Switch to AD001
Tea_a C-1 OO 1_3-4 2.5 45
Tea_b C-2 CO 4_7-8 4.5 81
Tea_c C-3 AL 3_1-2 3.0 54
List course success
Switch to 00001
Cancel course success (courseId: C-1)
Switch to 00002
Cancel course success (courseId: C-2)
Switch to 00003
Cancel course success (courseId: C-3)
Switch to 00001
Create course success (courseId: C-4)
Create course success (courseId: C-5)
Create course success (courseId: C-6)
Output course batch success
C-4 AA 1_1-2 2.0 36
C-5 BB 3_3-4 2.0 36
C-6 CC 5_5-6 2.0 36
List course success
Switch to 00002
Create course success (courseId: C-7)
Create course success (courseId: C-8)
Create course success (courseId: C-9)
Input course batch success
C-7 AA 1_1-2 2.0 36
C-8 BB 3_3-4 2.0 36
C-9 CC 5_5-6 2.0 36
List course success
Switch to 00003
Create course success (courseId: C-10)
Create course success (courseId: C-11)
Create course success (courseId: C-12)
Input course batch success
Course name already exists
Course name already exists
Course name already exists
Input course batch success
C-10 AA 1_1-2 2.0 36
C-11 BB 3_3-4 2.0 36
C-12 CC 5_5-6 2.0 36
List course success
Switch to AD001
Tea_a C-4 AA 1_1-2 2.0 36
Tea_a C-5 BB 3_3-4 2.0 36
Tea_a C-6 CC 5_5-6 2.0 36
Tea_b C-7 AA 1_1-2 2.0 36
Tea_b C-8 BB 3_3-4 2.0 36
Tea_b C-9 CC 5_5-6 2.0 36
Tea_c C-10 AA 1_1-2 2.0 36
Tea_c C-11 BB 3_3-4 2.0 36
Tea_c C-12 CC 5_5-6 2.0 36
List course success
00001 Bye~
00002 Bye~
00003 Bye~
AD001 Bye~
----- Good Bye! -----
```

#### 5-7命令

##### 输入

```shell
register 22373003 Hchc Aa333@ Aa333@ Student
register 22373002 Hbhb Aa222@ Aa222@ Student
register 22373001 Haha Aa111@ Aa111@ Student
register 00001 Tea_a Bb111$ Bb111$ Teacher
register 00002 Tea_b Bb222$ Bb222$ Teacher
login 22373003 Aa333@
login 22373002 Aa222@
login 22373001 Aa111@
login 00001 Bb111$
login 00002 Bb222$
switch 00001
createCourse OO 1_1-2 2.5 45
createCourse CO 3_3-4 4.5 81
switch 00002
createCourse OO 1_3-4 2.5 45
createCourse CO 3_5-6 4.5 81
switch 22373003
selectCourse C-1
selectCourse C-2
selectCourse C-3
selectCourse C-4
switch 22373002
selectCourse C-2
selectCourse C-4
switch 22373001
selectCourse C-1
selectCourse C-3
switch 00001
listStudent C-1
switch 00002
listStudent C-4
switch 00001
removeStudent 22373001 C-1
removeStudent 22373001 C-1
switch 22373001
listCourseSchedule
switch 22373002
listCourseSchedule
switch 22373003
listCourseSchedule
quit
```

##### 输出

```shell
Register success
Register success
Register success
Register success
Register success
Welcome to ACP, 22373003
Welcome to ACP, 22373002
Welcome to ACP, 22373001
Welcome to ACP, 00001
Welcome to ACP, 00002
Switch to 00001
Create course success (courseId: C-1)
Create course success (courseId: C-2)
Switch to 00002
Create course success (courseId: C-3)
Create course success (courseId: C-4)
Switch to 22373003
Select course success (courseId: C-1)
Select course success (courseId: C-2)
Select course success (courseId: C-3)
Select course success (courseId: C-4)
Switch to 22373002
Select course success (courseId: C-2)
Select course success (courseId: C-4)
Switch to 22373001
Select course success (courseId: C-1)
Select course success (courseId: C-3)
Switch to 00001
22373001: Haha
22373003: Hchc
List student success
Switch to 00002
22373002: Hbhb
22373003: Hchc
List student success
Switch to 00001
Remove student success
Student does not select course
Switch to 22373001
1_3-4 OO 2.5 45 Tea_b
List course schedule success
Switch to 22373002
3_3-4 CO 4.5 81 Tea_a
3_5-6 CO 4.5 81 Tea_b
List course schedule success
Switch to 22373003
1_1-2 OO 2.5 45 Tea_a
1_3-4 OO 2.5 45 Tea_b
3_3-4 CO 4.5 81 Tea_a
3_5-6 CO 4.5 81 Tea_b
List course schedule success
22373003 Bye~
22373002 Bye~
22373001 Bye~
00001 Bye~
00002 Bye~
----- Good Bye! -----
```
