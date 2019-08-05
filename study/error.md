# 겪었던 error

---

- `302(Redirect Problem)`
  - 회원가입 화면에서 `id`, `password`, `email`의 값을 `UserRegisterController`로 Post Mapping 하였을 때 발생
  - 해결 방법1 : `WebSecurityConfigurerAdapter`를 상속받은 `SecurityConfig`class의 `configure`메소드에
  `http.csrf().disable()`을 추가(보안에 좋지 않다.)
  - 해결 방법2([출처](https://spring.io/blog/2013/08/21/spring-security-3-2-0-rc1-highlights-csrf-protection))
    1. html파일의 header에 다음을 추가

      ~~~html
      <meta name="_csrf" th:content="@{_csrf.token}"></meta>
      <meta name="_csrf_header" th:content="@{_csrf.headerName}"></meta>
      ~~~

    2. js파일에 다음을 추가
      ~~~ javascript
      var token = $("meta[name='_csrf']").attr("content");
      var header = $("meta[name='_csrf_header']").attr("content");

      $.ajax({
        beforeSend : function(xhr){
          xhr.setRequestHeader(header, token);
        }
      })
      ~~~
