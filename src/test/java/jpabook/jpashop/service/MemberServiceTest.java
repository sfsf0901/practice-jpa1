package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Test
//    @Rollback(false) // 롤백하지 않고 커밋 -> insert 쿼리 날림
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("member");

        //when
        memberService.join(member);

        //then
//        em.flush(); // insert 쿼리 날림
        assertEquals(member, memberRepository.findById(member.getId()));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("member");
        Member member2 = new Member();
        member2.setName("member");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        //then
        fail("예회가 발생해야 한다."); // 특정 예외가 발생해야 하는데, 발생하지 않을 경우 테스트가 실패하도록 하는 안전장치
    }
}