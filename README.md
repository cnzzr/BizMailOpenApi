# BizMailOpenApi 腾讯企业邮箱Api接口 

## 编译注意事项  
项目单元测试方法涉及到多次腾讯企业邮接口调用，因此在编译时应跳过单元测试的验证
mvn package -DskipTests

## 命名空间说明
com.qq.exmail.openapi
com.qq.exmail.openapi.model
com.qq.exmail.openapi.service
com.qq.exmail.openapi.utils
com.qq.exmail.openapi.oauth
oauth2

单元测试
cn.msdi.BizMailOpenApi
cn.msdi.BizMailOpenApi.model
cn.msdi.BizMailOpenApi.service
cn.msdi.BizMailOpenApi.utils
cn.msdi.BizMailOpenApi.oauth

## 分支说明 
采用Git-Flow Light开发模式
master 编译发布版本
> 包含有正式服务器环境的特殊设置和配置信息

develop 开发版
features 功能分支

## 编译此项目所需的要求  
1.JDK6  
2.Maven
3.logs 目录存放日志文件

## 变更记录  

*通过Git进行代码管理*  
*创建日期：20160920*