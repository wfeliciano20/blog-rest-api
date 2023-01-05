package com.williamfeliciano.blogrestapi.controller;

import com.williamfeliciano.blogrestapi.dto.PostDto;
import com.williamfeliciano.blogrestapi.dto.PostResponseDto;
import com.williamfeliciano.blogrestapi.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import static com.williamfeliciano.blogrestapi.utils.AppConstants.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostResponseDto> GetAllPosts(
            @RequestParam(value = "pageNo", defaultValue=DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue=DEFAULT_PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue=DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false ) String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> GetPostById(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> CreatePost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> UpdatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeletePost(@PathVariable(name="id") long id){
        postService.DeletePost(id);
        String response = "Successfully deleted post with id " + id;
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
