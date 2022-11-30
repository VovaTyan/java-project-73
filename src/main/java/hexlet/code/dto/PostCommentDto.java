package hexlet.code.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PostCommentDto {
    @NotNull
    @NotBlank
    @Size(max = 200)
    private String body;

    private Long postId;

}
