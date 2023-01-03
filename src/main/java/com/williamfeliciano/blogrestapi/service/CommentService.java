package com.williamfeliciano.blogrestapi.service;

import com.williamfeliciano.blogrestapi.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto  createComment(Long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(Long postId);

    CommentDto getCommentById(Long postId,Long commentId);

    CommentDto updateComment(Long postId,Long commentId,CommentDto commentDto);

    void deleteComment(Long postId,Long commentId);
}
