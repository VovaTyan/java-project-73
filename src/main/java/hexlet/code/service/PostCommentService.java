package hexlet.code.service;

import hexlet.code.dto.PostCommentDto;
import hexlet.code.model.PostComment;

public interface PostCommentService {
    PostComment createNewPostComment(PostCommentDto dto);

    PostComment updatePostComment(long id, PostCommentDto dto);
}
