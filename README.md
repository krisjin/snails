snails 概述
======

snails 主要完成数据的采集，数据采集是使用的[webmaigc](https://github.com/code4craft/webmagic) ,数据分析的图形展示使用echart,
在数据挖掘分析中主要借助现有的大数据处理算法，与成熟的语义分析实现。


模块划分
======
> * snails-dao (数据访问层)
> * snails-entity（实体模型，数据的抓取存储，数据的展现都是依赖它）
> * snails-nosql （使用多种nosql实现应对海量数据存储）
> * snails-scheduler（榨取数据任务调度任务）
> * snails-service (服务层)
> * snails-web 后台管理
