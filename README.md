## 小轮子
目标：基于nio实现一个类似于`nodejs`的小轮子

## 实现的功能有：
1. servlet端mvc分离
2. 请求通过nio去简单处理，解析
3. 一个简易的数据库连接池
4. 一个简单的数据orm mini小轮子

## 测试
一个helloworld 的回复
webbench -c 20 -t 10 http://localhost:8099/hello
并发20个请求，每秒可以请求2k左右的请求，貌似有点低啊！！！！

## todo
熟悉netty的原理，比较其性能优越处
支持post请求
