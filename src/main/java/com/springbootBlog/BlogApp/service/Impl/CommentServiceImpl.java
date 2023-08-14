package com.springbootBlog.BlogApp.service.Impl;

import com.springbootBlog.BlogApp.dto.CommentDTO;
import com.springbootBlog.BlogApp.entity.Comment;
import com.springbootBlog.BlogApp.entity.Post;
import com.springbootBlog.BlogApp.exception.BlogAPIException;
import com.springbootBlog.BlogApp.exception.ResourceNotFoundException;
import com.springbootBlog.BlogApp.repository.CommentRepository;
import com.springbootBlog.BlogApp.repository.PostRepository;
import com.springbootBlog.BlogApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository  postRepository;
    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepositor, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = maptoEntity(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id", postId)
        );


        //set post to comment entity
        comment.setPost(post);
        //comment entity to db
        Comment newComment = commentRepository.save(comment);
        return maptoDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> maptoDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment", "id", commentId));
        if(comment.getPost().getId() != post.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return maptoDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest) {
       Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id", postId));
       Comment comment =  commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id", commentId));
       if(comment.getPost().getId() != post.getId()){
           throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
       }
       comment.setName(commentRequest.getName());
       comment.setEmail(commentRequest.getEmail());
       comment.setBody(commentRequest.getBody());
       Comment updateComment = commentRepository.save(comment);
       return maptoDTO(updateComment);
    }
    @Override
    public void deleteComment(Long postId, Long commentId) {
         Post post = postRepository.findById(postId).orElseThrow(
                 ()-> new ResourceNotFoundException("Post","id", postId)
         );

         Comment comment = commentRepository.findById(commentId).orElseThrow(
                 ()->new ResourceNotFoundException("Comment","id",commentId)
         );

         if(comment.getPost().getId() != post.getId())
             throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
         commentRepository.delete(comment);
    }

    private CommentDTO maptoDTO(Comment comment){
        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    private Comment maptoEntity(CommentDTO commentDTO) {
          Comment comment = mapper.map(commentDTO, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setBody(comment.getBody());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
        return comment;
    }
}
