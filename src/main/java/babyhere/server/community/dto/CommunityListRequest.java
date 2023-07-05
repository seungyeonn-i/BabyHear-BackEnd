package babyhere.server.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityListRequest {
    private String title;
    private String nickname;
    private String detail;
    private String date;
}
