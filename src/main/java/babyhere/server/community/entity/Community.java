package babyhere.server.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
//@NoArgsConstructor
@AllArgsConstructor
public class Community {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String nickname;
    private String detail;
    private LocalDateTime date;
    private Long hits;

    public Community(String title, String nickname, String detail, LocalDateTime date, Long hits) {
        this.title = title;
        this.nickname = nickname;
        this.detail = detail;
        this.date = date;
        this.hits = hits;
    }
}
