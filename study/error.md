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
  - 해결 방법1 : Lazy로 변경([출처](https://eclipse4j.tistory.com/215))
  - 해결 방법2 : List<> -> Set<>로 변경([출처](https://thoughts-on-java.org/hibernate-tips-how-to-avoid-hibernates-multiplebagfetchexception/))
  - [Hibernate MultipleBagFetchException 정복하기](https://perfectacle.github.io/2019/05/01/hibernate-multiple-bag-fetch-exception/)

<br>

- `org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.mugon.domain.User.introductionList, could not initialize proxy - no Session`
  - `User`class에 mapping된 `Introduction`를 조회하지 못해 생기는 오류
  - 해결 방법 : OneToMany에 fetch = FetchType.EAGER 추가

<br>

- `org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.mugon.domain.User.introductionList, could not initialize proxy - no Session`**#2**
  - 다른 프로젝트에서 테스트코드를 짜다가 `User`관련 `domain`, `controller`, `service`, `repository`만 작성 후 save test를 진행했는데
    ~~~java
      (생략)
      user = userRepository.getOne(1L);
      assertThat(user).inNotNull();
      assertThat(user.getUserName).isEqualsTo("testUserName");
    ~~~
  - 제일 마지막 줄에서 `LazyInitializationException`이 발생, `User`객체는 매핑된(참조하거나 참조된) 객체가 없어 `Exception`의 이유를 몰라 구글링을 하게 되었는데, getOne과 findOne의 차이에 대해 알게 됨.
      - getOne은 Lazy Evaluation이 적용된 프록시를 리턴하지만 findOne은 데이터를 바로 가져옴
      - 따라서 위의 코드일 경우 `findBy...`과 같이 코드를 작성하면 `LazyInitializationException`이 발생하지 않음!
