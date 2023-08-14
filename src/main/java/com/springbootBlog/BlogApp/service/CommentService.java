package com.springbootBlog.BlogApp.service;

import com.springbootBlog.BlogApp.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long id, CommentDTO commentDTO);
    List<CommentDTO> getCommentByPostId(long postId);
    CommentDTO getCommentById(Long id, Long commentId);
    CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest);
    void deleteComment(Long postId, Long  commentId);
}
