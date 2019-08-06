# 겪었던 error

---

- `302(Redirect Problem)`
  - 회원가입 화면에서 `id`, `password`, `email`의 값을 `UserRegisterController`로 Post Mapping 하였을 때 발생
  - 해결 방법1 : `WebSecurityConfigurerAdapter`를 상속받은 `SecurityConfig`class의 `configure`메소드에
  `http.csrf().disable()`을 추가(보안에 좋지 않다.[csrf란?](/study.md))
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

<br>

- `org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags`
  - 하나의 `Entity`에서 다중 `Eager`을 사용하였을 때 생기는 오류
    - example
      ~~~java
        @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
        private List<Projects> projectsList;

        @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
        private List<Introduction> introductionList;
      ~~~
  - 해결 방법 : Lazy로 변경([출처](https://eclipse4j.tistory.com/215))      

<br>

- `org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.mugon.domain.User.introductionList, could not initialize proxy - no Session`
  - `User`class에 mapping된 `Introduction`를 조회하지 못해 생기는 오류
  - 해결 방법1 : OneToMany에 fetch = FetchType.Eager 추가
  - 해결 방법2 : @Transactional 추가
    - [출처](https://ankonichijyou.tistory.com/entry/JPA-OneToMany-%EC%98%A4%EB%A5%98)
