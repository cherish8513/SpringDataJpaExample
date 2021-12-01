package study.datajpa.domain;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.dto.UsernameOnlyDto;
import study.datajpa.repository.MemberRepository;
import study.datajpa.repository.TeamRepository;
import study.datajpa.repository.UsernameOnly;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testEntity() throws Exception{
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        member1.changeTeam(teamB);

        em.flush();
        em.clear();

        //when
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();


        //then
        members.stream().forEach(m -> System.out.println(m + " " + "-> member.team : " + m.getTeam()));
        teamA.getMembers().stream().forEach(m -> System.out.println(m.getTeam() + " " + m));
    }


    @Test
    public void testRepository() throws Exception{
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        //when
        List<Team> teams = teamRepository.findAll();
        List<Member> members = memberRepository.findAll();

        //then
        teams.stream().forEach(t-> t.getMembers().stream().forEach(m -> System.out.println(m + " " + m.getTeam())));
    }

    @Test
    public void memberDtoTest() throws Exception{
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        //when
        List<MemberDto> memberDtos = memberRepository.findMemberDto();


        //then
        memberDtos.stream().forEach(m -> System.out.println(m));
    }

    @Test
    public void memberPageTest() throws Exception{
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamA);
        Member member3 = new Member("member3", 10, teamB);
        Member member4 = new Member("member4", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<MemberDto> page = memberRepository.findByAge(age, pageRequest);


        //then
        List<MemberDto> content = page.getContent();

        content.stream().forEach(m -> System.out.println(m));

        assertEquals(content.size(), 3);
        assertEquals(page.getTotalElements(), 4);
        assertEquals(page.getNumber(), 0);
        assertEquals(page.getTotalPages(), 2);
        assertEquals(page.isFirst(), true);
        assertEquals(page.hasNext(), true);
    }

    @Test
    public void memberProjectionTest() throws Exception{
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamA);
        Member member3 = new Member("member3", 10, teamB);
        Member member4 = new Member("member4", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        //when
        List<UsernameOnlyDto> result = memberRepository.findProjectionsByUsername("member1", UsernameOnlyDto.class);


        //then
        result.stream().forEach(r -> System.out.println(r.getUsername()));
    }
}