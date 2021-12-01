package study.datajpa.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass // 속성을 같이 쓰는 클래스
@Getter
public class JpaBaseTimeEntity {

    @Column(updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        modifiedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
