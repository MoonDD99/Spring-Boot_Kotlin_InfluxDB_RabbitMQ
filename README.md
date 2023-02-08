# Spring-Boot_Kotlin_InfluxDB_RabbitMQ
### 문제상황
- Influx version 2.x.xx → Influx version 1.8.10    
    InfluxQL은 query language로 RDBMS에서 사용되는 퀴리문이랑 비슷하게 사용할 수 있고 결과값도 비슷하게 출력된다.
    버전 2이상부터는 QL(query language)가 지원되지 않았고 Flux로는 원하는 형태로 결과값을 받을 수 없었다. (필드 key가 컬럼으로 나와야한다.)
    결과값 형태가 다른 것을 Spring boot 에서 해결할 수 있는 방법을 찾아보자,,
    
