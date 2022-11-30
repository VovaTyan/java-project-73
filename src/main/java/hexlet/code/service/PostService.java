package hexlet.code.service;

import hexlet.code.dto.PostDto;
import hexlet.code.model.Post;

public interface PostService {
    Post createNewPost(PostDto dto);

    Post updatePost(long id, PostDto dto);
}
