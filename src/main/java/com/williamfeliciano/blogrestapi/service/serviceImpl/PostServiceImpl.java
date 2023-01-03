package com.williamfeliciano.blogrestapi.service.serviceImpl;

import com.williamfeliciano.blogrestapi.dto.PostDto;
import com.williamfeliciano.blogrestapi.dto.PostResponseDto;
import com.williamfeliciano.blogrestapi.entity.Post;
import com.williamfeliciano.blogrestapi.exception.ResourceNotFoundException;
import com.williamfeliciano.blogrestapi.repository.PostRepository;
import com.williamfeliciano.blogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto createPost(PostDto postDto) {

        // Create a new post
        Post post = mapToEntity(postDto);

        // Save the post to the database
        Post newPost = postRepository.save(post);

        // return convert entity to DTO
        return mapToDTO(newPost);

    }

    @Override
    public PostResponseDto getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
       // Create a sort object
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        // Create Pageable object
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        // Return a Page<Post>
        Page<Post> posts= postRepository.findAll(pageable);
        // get the content from page object
        List<Post> postList = posts.getContent();
        // convert from posts list to a list of postdto
        List<PostDto> content = postList.stream().map(this::mapToDTO).collect(Collectors.toList());
        // Create the response object with tbe pagination content
        PostResponseDto postResponse = new PostResponseDto();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(postResponse.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        return mapToDTO(postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id)));
    }

    @Override
    public PostDto updatePost(PostDto updatedPost, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        post.setTitle(updatedPost.getTitle());
        post.setDescription(updatedPost.getDescription());
        post.setContent(updatedPost.getContent());
        Post updated = postRepository.save(post);
        return mapToDTO(updated);
    }

    @Override
    public void DeletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

}
