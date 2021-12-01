package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.domain.Member;
import study.datajpa.dto.MemberDto;
import study.datajpa.dto.UsernameOnlyDto;

import javax.persistence.QueryHint;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    Page<MemberDto> findByAge(int age, Pageable pageable);

    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

//    List<UsernameOnly> findProjectionsByUsername(String username);
//    List<UsernameOnlyDto> findProjectionsByUsername(String username);
    <T>List<T> findProjectionsByUsername(String username, Class<T> type);

//    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
//    MemberDto findReadOnlyById(Long id);
}
