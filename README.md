##简介
该库提供Android应用开发中常用的各种基础辅助类，目前包括如下几个功能模块：

* Activity基础管理
* 网络
* 图片处理
* json数据格式解析
* 常用的加解密算法
* Toast封装
* Log封装
* 时间日期
* 文件处理
* 字符串处理


##模块介绍

###1、加解密算法
* MD5Encrypt：MD5数据摘要算法辅助类，对源数据进行处理后形成128bit的摘要信息
    * 提供返回32位及16位十六进制字符串的加密接口
    * 支持获取文件MD5值
* DESCipher：DES对称加密算法封装类
    * 提供数据加密、解密接口；
    * 提供默认IV向量、工作模式及填充方式；
    * 支持IV向量设置；
    * 支持工作模式设置；
    * 支持填充方式设置；
* RSACipher：RSA非对称加密算法辅助类
    * 提供公钥、密钥生成接口；
    * 提供公钥数据加密接口；
    * 提供私钥数据解密接口；
    * 支持通过十六进制公、私密钥字符串还原公钥、密钥；
    * 支持通过byte数组还原公钥、密钥；
* Base64Cipher:Base64编码、解码辅助类
    * 提供Base64编码、解码接口；
    * 支持不同Base64编解码Flag设置

####历史修改记录
* 2016-02-17 15:37  罗德君
    * 提交MD5加密辅助类
    * 提交MD5加密单元测试
* 2016-02-17 17:54  罗德君
    * 提交DES加密、解密辅助类
    * 提交DES加密、解密单元测试
* 2016-02-18 11:29  罗德君
    * 提交RSA加密、解密辅助类
    * 提交RSA加密、解密单元测试
* 2016-02-18 13:30  罗德君
    * 提交BASE64辅助类
* 2016-02-19 12:45  罗德君
     * 提交BASE64Util单元测试


###2、log输出
* 通过boolean 值控制log是否输出
* 对android 系统log 进一步封装，输出更为详细的日志信息，如：线程id 、所在类名、方法名
* 支持format

#### 使用示例
* 方式一：LogCat.e("hello world");
* 方式二：LogCat.e("hell%s",o);

#### 注意事项
* 当format 格式不正确时，无法正常输出log（内部有做异常处理）
如：logCat.e("hell%",0)


#### 历史修改纪录
* 2016-02-17 10.45 陈坤
* version 1.0 提交


###3、Toast工具类
* text支持String和resourceId
* 支持选择设置gravity
* 支持选择设置显示时长
* 支持取消显示

#### 使用示例
* Toastor toastor = Toastor.build(getContext()).text("toastor").gravity(Gravity.BOTTOM).duration(Toast.LENGTH_LONG).show();
* toastor.cancel(getContext());

#### 历史修改纪录
* 2016-02-17 11.40 谈一秀
* version 1.0 提交

###4、Wifi监听器
* 监听wifi的断开与连接

#### 使用示例
* 先启动
* WifiMonitor.getInstance().startMonitor(getContext());
* 
* 再注册
* WifiMonitor.getInstance().registerObserver(实现WifiMonitor.WifiStateCallback 接口);
* 
* 使用完成之后不要忘了取消注册和停止监听
* WifiMonitor.getInstance().stopMonitor(getContext());
* WifiMonitor.getInstance().unregisterObserver(实现WifiMonitor.WifiStateCallback 接口);
* 
* 注意需要添加权限
* <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

#### 历史修改纪录
* 2016-02-18 14.40 谈一秀
* version 1.0 提交

###5 时间日期辅助类
* DateUtil :常用时间、日期处理工具类
  * 提供 获取当前时间并按指定格式输出
  * 将String,Long 转换为Date
  * 获取当前月份天数
  * 获取时间间隔并转换为：刚刚、几分钟前、几小时前、几天前、几月前、几年前
  * 比较2个日期得先后
 
### 历史修改记录
* 2016-02-18 16.10 陈坤
     * version 1.0 提交
     
###6 文件处理辅助类
* FileUtil :常用文件处理工具类
  * 提供获取指定目录下所有文件名列表
  * 向文件写入
  * 复制文件，文件夹
  * 删除文件、文件夹
  * 获取文件大小
  * 文件大小format
  * 读取文件内容
  * 获取文件扩展名
  * 文件移动
  * 判断文件是否存在
  * 重命名

### 历史修改记录
* 2016-02-19 12.40 陈坤
     * version 1.0 提交

 
###7 字符串处理辅助工具类
* StringUtil :常用字符串处理工具类
  * 判断是否为空
  * 去除字符串中所有空格(包括中间空格)
  * 随即生成指定长度唯一字符串
  * 将字符串转化为int、long、boolean
  
### 历史修改记录
* 2016-02-22 13.45 陈坤
     * version 1.0 提交  
 
### 8 Json数据格式解析
* JsonUtil:Json处理工具类
	* JsonString 反序列化 JavaBean

	* JsonString 反序列化 List< JavaBean>

	* JavaBean 序列化 JsonString

	* List<JavaBean> 序列化 JsonString

	* 序列化过滤字段

	* 序列化格式配置
	 
* 使用示例
```java
//JsonString 转 Object(Bean)
	Bean bean = JsonUtil.parseBean(jsonStr,Bean.class);
	
// Object(Bean)转String 具体参数配置请参考com.pisen.baselib.utils.JsonUtil#serialize()@Desc
String jsonStr = JsonUtil.serialize(bean);
```
### 注意事项
* 代码混淆时，泛型Bean需 **-keep**

### 历史修改记录
* 2016-02-22 14.40 石闯
     * version 1.0 提交



### 10 IO操作
* IOUtils : IO操作工具类
    * 关闭URLConnection
    * 关闭输入/输出流，并捕获异常
    * InputStream转换成BufferedInputStream
    * Reader转换成BufferedReader
    * 输入流、Uri 、URL 、URLConnection 转换成byte数组
    * 输入流转换成char数组
    * 输入流、URI 、URL 、byte数组转换成String
    * 读取输入流的每一行，封装成List<String>\
    * CharSequence、String 转换成InputStream
    * 将byte数组、char数组 、CharSequence 、String 写入输出流
    * 将数据行写入OutputStream
    * 把输入流中的数据写到输出流中(支持进度跟踪)
    * 对比输入流中的数据与输出流是否一致
    * 从输入流中读取数据
* 使用示例
```java
   IOUtils.close(connection);

   IOUtils.closeQuietly(reader);

   Reader newRader = IOUtils.toBufferedReader(reader);

   byte[] result = IOUtils.toByteArray(fileInputStream);

   char[] result = IOUtils.toCharArray(fileInputStream);

   IOUtils.toString(fileInputStream);

   final List<String> readlist = IOUtils.readLines(fileInputStream);

   InputStream inputStream = IOUtils.toInputStream(string);

   IOUtils.write(string.getBytes(), fileOutputStream);

   IOUtils.writeLines(list, null, fileOutputStream);

   IOUtils.copy(fileInputStream, fileOutputStreamOther);

   IOUtils.contentEquals(fileInputStreamA, fileInputStreamB)

   IOUtils.read(fileInputStream, byteData);
```

* 历史修改记录
    * 2016-02-23 10.25 谈一秀
      * version 1.0 提交
###11、网络
* 基于Volley封装，适合网络频繁访问，**不适合文件上传下载**
* 支持链式编程
* 支持常用Method，例如Get、Post等
* 支持设置Timeout
* 支持设置重试次数
* 支持设置是否缓存
* 支持添加请求headers
* 支持Json/POJO传参
* 支持取消Request
* 支持回调，支持根据泛型设置指定的返回类型(POJO对象)
* 异常处理，onError回调，errorMsg为可提示字符串，1、POJO设定的提示；2、网络异常；3、服务器错误;4、未知错误

#### 使用示例
* 1、初始化：在Application onCreate中调用RequestManager.getInstance().init(this);
* 2、使用参考demo:com.pisen.demo.volley.TestVolleyActivity.java

#### 注意事项
* 设置HttpCallBack<T>, T在混淆时需要excluded

#### 历史修改纪录
* 2016-02-24 11.02 何刚
* version 1.0 提交库及demo


### 12 配置数据存储 - SharedPreferences工具类
* SharedPreferecesUtil:SP处理工具类
	* 基本数据类型的SP序列化以及反序列化

	* String数据类型的SP序列化以及反序列化

	* Object对象的SP序列化以及反序列化(Base64)

	* Set<String>数据类型的SP序列化以及反序列化(可针对Map进行操作)

	* 对SP配置文件对应节点(属性)的删除 / 整个配置文件的删除

	* 对特定SP配置文件的操作(如上功能)

#### 使用示例
1. 应用启动时初始化(建议随Application进行初始化)
```java
	SharedPreferecesUtil.init(context);
```
2. 静态调用工具类方法
```java
	SharedPreferencesUtil.SharedPreferencesUtil.putBoolean("abc", true);
```


### 历史修改记录
* 2016-02-25 09.20 石闯
     * version 1.0 提交
     
##系统环境参数
* 最低JDK版本：1.7
* 最低SDK版本：15


### 13、 NanoHttpServer - java 轻量级服务器
* 核心类:NanoHTTPD 
	* 支持 POST ，GET ，HEAD and DELETE 请求.
	* 支持 Cookies 缓存.
	* 作为服务器 站点，供客户端访问.
	* 易于拓展成 FileServer 使用.
* 辅助启动类:ServerRunner

* Sample 常用示例

* HttpdIntegration 集成测试

#### 使用示例
1. 启动服务
```java
    server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
```
2. 停止服务
```java
	server.stop();
```

### 历史修改记录
* 2016-02-29 09.25 马欢
     * version 1.0 提交
     
### 注意事项
* 需要增加权限
<uses-permission android:name="android.permission.INTERNET" />

