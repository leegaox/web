> 模仿问卷星"常用办公软件需求统计表"

 需求地址：
 https://www.wjx.cn/jq/37748798.aspx
 
 ### 需求点： 
   - 基本信息为姓名、工号和部门；
   - 软件列表在数据库中可配置；
   - 统计界面未给出。
 
 
 ### 接口
  1. 获取软件列表前端展示： http://localhost:8080/api/software/getAll
  2. 前端提交问卷填写信息：http://localhost:8080/api/record/commit
  3. 获取软件使用情况数据：http://localhost:8080/api/statistics/pullSoft

