package hello.login;

import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;

  /**
   * 테스트용 데이터 추가
   */
  @PostConstruct
  public void init() {

    // 상품 데이터
    itemRepository.save(new Item("itemA", 10000, 10));
    itemRepository.save(new Item("itemB", 20000, 20));

    // 회원 데이터
    Member member = Member.builder()
            .loginId("test")
            .password("test123")
            .name("tester")
            .build();
    memberRepository.save(member);

  }

}