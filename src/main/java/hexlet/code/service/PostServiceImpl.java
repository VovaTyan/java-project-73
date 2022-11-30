package hexlet.code.service;

import hexlet.code.dto.PostDto;
import hexlet.code.model.Post;
import hexlet.code.model.User;
import hexlet.code.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public Post createNewPost(final PostDto dto) {
        final Post newpost = fromDto(dto);
        return postRepository.save(newpost);
    }

    @Override
    public Post updatePost(final long id, final PostDto dto) {
        final Post post = postRepository.findById(id).get();
        merge(post, dto);
        return postRepository.save(post);
    }

    private void merge(final Post post, final PostDto postDto) {
        final Post newPost = fromDto(postDto);
        post.setTitle(newPost.getTitle());
        post.setBody(newPost.getBody());
    }

    private Post fromDto(final PostDto dto) {
        final User author = userService.getCurrentUser();

        return Post.builder()
                .author(author)
                .title(dto.getTitle())
                .body(dto.getBody())
                .build();
    }
}
