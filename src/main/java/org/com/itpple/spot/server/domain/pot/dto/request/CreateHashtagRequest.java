package org.com.itpple.spot.server.domain.pot.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHashtagRequest {

    @NotEmpty
    @Size(min = 1, max = 10, message = "해시태그는 한 번에 1~10개까지 등록할 수 있습니다.")
    private List<String> hashtagList;
}
