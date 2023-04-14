# Spring-Boot_Kotlin_InfluxDB_RabbitMQ
### [프로젝트 설명]

Device에 제어 요청을 보내거나 받을때마다 로그를 저장하기 위해서 해당 로그를 InfluxDB에 저장하는 프로젝트

해당 프로젝트는 **로그를 InfluxDB에 저장하는 것을 목표**로 하고 있기에 Device 즉 IoT를 따로 제어하는 기능은 따로 구현하지 않는다.

### [발견한 문제]

InfluxDB를 저번에 사용했을 때 version의 차이로 인해 ver2 → ver1을 사용했다 그때는 변경한 이유는 두가지정도로 기억한다.

1. ver2는 실행결과가 내가 원하는 형식으로 받을 수 없었다.
    1. 그 당시 생각으로는 설계된 데이터베이스 구조로는 데이터를 가지고 오고 서버에서 다시 join과 같은 가공을 해야하는 형태였다. 그러면 NoSQL 기반의 데이터베이스를 사용하는 장점이 없다고 생각했다. 그래서 version 1으로 낮춰서 원하는 형태로 받아와 바로 사용할 수 있도록 사용했다. 근데 지금 생각해보니 정말 방법이 없었나?라는 의문이 들어서 그때와 비슷한 구조로 테스트를 다시 해보려고 한다. 애초에 TDBM을 사용하면서 관계형 데이터베이스에서 사용한 설계를 그대로 가져온것도 이상하다고 생각이 든다. 그 당시에는 내가 할 수 있는 최선의 개발을 했지만 지금 생각하니 의문점이 많아서 다시 설계해보려고 한다.
2. ver2를 spring boot + kotlin에서 사용하는 라이브러리에서 jpa와 같이 orm을 제공하고 있지 않았다.
    1. 직접 쿼리문을 작성해서 쿼리문을 데이터베이스에 보내는 형태였다. 직접 쿼리문을 작성하는 것은 유연성에 떨어진다고 생각했다.
    2. 라이브러리 분석도 꽤 했는데, 딱히 정리를 해두지 않아서 잘 기억이 안난다,,, 궁금하니 다시 정리해보려고 한다.

### 📃[문제 구체화] Client → Device 제어 방식

### Question

1. 사용자가 device 키라는 요청을 보내면 **Device에 어떻게 접근**할 것인가?
   1. API 서버에서 직접 Device에 접근해서 제어한다.
   2. API 서버에서 중간 서버를 거쳐 Device에 접근한다.

   위 두개정도를 고민했다.

   API 서버에 Device 제어 하는 기능만 있다면

   a로 해결하는 것이 간편하다.

   하지만 만약 API 서버에서 Device 제어하는 기능만 있는 것이 아니라 다른 기능도 있다면?

   다른 기능에 문제가 생겨 API 서버에 문제가 생긴다면 Device 제어기능도 같이 불가능해진다.그러면 **중간서버가 필요하지 않을까?**

2. **로그를 저장하는 시점**은 언제인가?
   1. API 서버에서 사용자가 Device 제어 요청을 받은 후
   2. 중간서버에서 Device 제어 요청을 받은 후

   1번 문제에서 a와 같이 API 서버에서 직접 Device에 접근해서 제어하면

   a의 경우로만 해결해야한다.

   하지만 b와 같이 중간서버를 거친다면

   2번 문제에서 a의 방식은 API 서버에서 요청을 받은 것에 대한 로그를 저장하는 것이고

   b의 방식은 Device가 제어 요청을 받은 것에 대한 로그를 저장하는 것이 된다.

   즉 b의 경우가 **좀 더 Device 제어 단계에 가깝다**(?)


### Solution

<aside>
💡 1. (b) API 서버에서 중간 서버를 거쳐 Device에 접근한다. 
2. (b) 중간서버에서 Device 제어 요청을 받은 후

</aside>

**로직**

/* TODO : 아래 구조 사진 추가 */

사용자 → msg “device on” → msg publish → exchange → queue → msg “device on” → subscriber → device on, msg save on DB

중간서버를 두고 제어 요청을 메세지로 만들고 메세지를 중간 서버의 RabbitMQ exchange에 Publish한다. 그리고 해당 exchange를 구독하고 있는 subscriber가 해당 메세지를 받으면 Device를 제어하고 해당 메세지를 포함하고 있는 로그를 InfluxDB에 저장한다.