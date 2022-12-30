package com.williamfeliciano.blogrestapi.service;

import com.williamfeliciano.blogrestapi.dto.PostDto;
import com.williamfeliciano.blogrestapi.dto.PostResponseDto;



public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponseDto getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getPostById(long id);

    PostDto updatePost(PostDto updatedPost,long id);

    void DeletePost(long id);
}
