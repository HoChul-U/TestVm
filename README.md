# TestVm

#httpbin.org 만들기
---
-nhn팀프로젝트

> httpbin을 참고로 유사하게 만들려고해봄
 

+ 1번)
``` $ curl -v http://test-vm.com/ip ```

+ 2번)
  - 1번:
``` $ curl -v http://test-vm.com/get ```
  - 2번: ```$ curl -X GET http://test-vm.com/get?msg1=hello ```
  - 3번: ```$ curl -X GET "http://test-vm.com/get?msg1=hello&msg2=world"```

+ 3번)
  - 1번: ```# application/json 형태의 데이타를 post 로 전송```<br>
```$ curl -v "http://test-vm.com/post" -H 'Content-Type: application/json' -d '{ "msg1": "hello", "msg2": "world" }'```

  - 2번: ```curl -v -F "upload=@msg.json" http://test-vm.com/post```

<br>
위와 같은 구성으로 진행되었고 3-2까지 구현완료

> 하지만 content-length 부분은 다르게 나온다.
