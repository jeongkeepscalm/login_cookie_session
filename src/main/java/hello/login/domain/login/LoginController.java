package hello.login.domain.login;

import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @GetMapping("/login")
  public String loginForm(@ModelAttribute("loginForm") LoginRequest form) {
    return "login/loginForm";
  }

  @PostMapping("/login")
  public String login(@Validated @ModelAttribute(name = "loginForm") LoginRequest form
          , BindingResult bindingResult
          , HttpServletResponse response) {

    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId() , form.getPassword());
    log.info("login? {}", loginMember);

    if (loginMember == null) {
      // ObjectError
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // login success logic

    // add Cookie
    Cookie loginIdCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
    response.addCookie(loginIdCookie);

    return "redirect:/";

  }

}
