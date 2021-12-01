package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.domain.Member;
import study.datajpa.dto.MemberDto;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    //도메인 클래스 컨버터
    @GetMapping("/members2/{id}") // pk를 주소창에 보이는 것 부터 권장하지 않는다.
    public String findMember(@PathVariable("id") Member member){
        return member.getUsername();
    }

    //http://localhost:8080/members?page=11&size=3&sort=id,desc
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable pageable){
        return memberRepository.findAll(pageable).map(m -> new MemberDto(m.getId(), m.getUsername(), null));
    }

//    @PostConstruct
//    public void init(){
//        for (int i = 0; i < 100; i++) {
//            memberRepository.save(new Member(("user" + i), i));
//        }
//    }
}
