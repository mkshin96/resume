# 겪었던 error

- `302(Redirect Problem)`
  - 회원가입 화면에서 `id`, `password`, `email`의 값을 `UserRegisterController`로 Post Mapping 하였을 때 발생
  - 해결 방법 : `WebSecurityConfigurerAdapter`를 상속받은 `SecurityConfig`class의 `configure`메소드에
  `http.csrf().disable()`을 추가
