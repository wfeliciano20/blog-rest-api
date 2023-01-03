package com.williamfeliciano.blogrestapi.service.serviceImpl;


import com.williamfeliciano.blogrestapi.dto.CommentDto;
import com.williamfeliciano.blogrestapi.entity.Comment;
import com.williamfeliciano.blogrestapi.entity.Post;
import com.williamfeliciano.blogrestapi.exception.BlogApiException;
import com.williamfeliciano.blogrestapi.exception.ResourceNotFoundException;
import com.williamfeliciano.blogrestapi.repository.CommentRepository;
import com.williamfeliciano.blogrestapi.repository.PostRepository;
import com.williamfeliciano.blogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl  implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepositor,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepositor;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        // Return a List<Comment>
        List<Comment> comments= commentRepository.findByPostId(postId);
        // convert from comments list to a list of commentsdto
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId,Long commentId) {

        Post post = getPostById(postId);

        Comment comment = getCommentById(commentId);

        DoesCommentNotBelongToPost(post.getId(),comment.getPost().getId());

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = getPostById(postId);

        Comment comment = getCommentById(commentId);

        DoesCommentNotBelongToPost(post.getId(),comment.getPost().getId());

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);
        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = getPostById(postId);
        Comment comment = getCommentById(commentId);
        DoesCommentNotBelongToPost(post.getId(),comment.getPost().getId());

        commentRepository.delete(comment);
    }

    private void DoesCommentNotBelongToPost(long postId, long commentId) throws BlogApiException{
        if(commentId != postId) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not exist in post");
        }
    }
    private Post getPostById(long postId) throws ResourceNotFoundException {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","Id",postId));
    }

    private Comment getCommentById(long commentId) throws ResourceNotFoundException {
        return commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Comment","Id",commentId));
    }

    private CommentDto mapToDto(Comment comment){
        //        commentDto.setId(comment.getId());
        //        commentDto.setName(comment.getName());
        //        commentDto.setEmail(comment.getEmail());
        //        commentDto.setBody(comment.getBody());
        return mapper.map(comment,CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto){
        //        comment.setId(commentDto.getId());
        //        comment.setName(commentDto.getName());
        //        comment.setEmail(commentDto.getEmail());
        //        comment.setBody(commentDto.getBody());
        return mapper.map(commentDto,Comment.class);
    }
}
