package hexlet.code.controller;

import hexlet.code.dto.PostDto;
import hexlet.code.model.Post;
import hexlet.code.repository.PostRepository;
import hexlet.code.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
// Для авторизованных запросов в сваггере
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static hexlet.code.controller.PostController.POST_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "javainuseapi")
@RequestMapping("${base-url}" + POST_CONTROLLER_PATH)
public class PostController {

    public static final String POST_CONTROLLER_PATH = "/posts";
    public static final String ID = "/{id}";
    public static final String BY = "/by";

    private static final String ONLY_AUTHOR_BY_ID = """
            @PostRepository.findById(#id).get().getAuthor().getEmail() == authentication.getName()
        """;

    private final PostRepository postRepository;
    private final PostService postService;

    @Operation(summary = "Get All posts")
    @GetMapping
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Operation(summary = "Get post by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "post found"),
            @ApiResponse(responseCode = "404", description = "post with that id not found")
    })
    @GetMapping(ID)
    public Post getById(@PathVariable final Long id) {
        return postRepository.findById(id).get();
    }

    @Operation(summary = "Create new post")
    @ApiResponse(responseCode = "201", description = "post created")
    @PostMapping
    @ResponseStatus(CREATED)
    public Post createNewpost(@RequestBody @Valid final PostDto dto) {
        return postService.createNewPost(dto);
    }

    @Operation(summary = "Update post")
    @ApiResponse(responseCode = "200", description = "post updated")
    @PutMapping(ID)
    @PreAuthorize(ONLY_AUTHOR_BY_ID)
    public Post updatePost(@PathVariable final Long id,
                           // Schema используется, чтобы указать тип данных для параметра
                           @Parameter(schema = @Schema(implementation = PostDto.class))
                           @RequestBody @Valid  final PostDto dto) {
        return postService.updatePost(id, dto);
    }

    @Operation(summary = "Delete post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "post deleted"),
            @ApiResponse(responseCode = "404", description = "post with that id not found")
    })
    @DeleteMapping(ID)
    @PreAuthorize(ONLY_AUTHOR_BY_ID)
    public void deletePost(@PathVariable final Long id) {
        postRepository.deleteById(id);
    }
}
