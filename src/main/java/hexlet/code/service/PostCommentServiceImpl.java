package hexlet.code.service;

import hexlet.code.dto.PostCommentDto;
import hexlet.code.model.Post;
import hexlet.code.model.PostComment;
import hexlet.code.model.User;
import hexlet.code.repository.PostCommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final UserService userService;

    @Override
    public PostComment createNewPostComment(final PostCommentDto dto) {
        final PostComment newPostComment = fromDto(dto);
        return postCommentRepository.save(newPostComment);
    }

    @Override
    public PostComment updatePostComment(final long id, final PostCommentDto dto) {
        final PostComment postComment = postCommentRepository.findById(id).get();
        merge(postComment, dto);
        return postCommentRepository.save(postComment);
    }

    private void merge(final PostComment postComment, final PostCommentDto postCommentDto) {
        final PostComment newPostComment = fromDto(postCommentDto);
        postComment.setBody(newPostComment.getBody());
    }

    private PostComment fromDto(final PostCommentDto dto) {
        final User author = userService.getCurrentUser();
        final Post post = new Post(dto.getPostId());

        return PostComment.builder()
                .author(author)
                .body(dto.getBody())
                .post(post)
                .build();
    }
}
