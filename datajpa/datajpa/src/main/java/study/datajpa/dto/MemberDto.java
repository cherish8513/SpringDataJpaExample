package study.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(of = {"id", "username", "teamname"})
public class MemberDto {

    private Long id;
    private String username;
    private String teamname;

}
