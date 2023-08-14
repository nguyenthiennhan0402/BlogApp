package com.springbootBlog.BlogApp.service;

import com.springbootBlog.BlogApp.dto.PostDTO;
import com.springbootBlog.BlogApp.dto.PostRespone;
import com.springbootBlog.BlogApp.entity.Post;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDto);
    PostRespone getAllPost(int pageNom, int pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Long id);
    PostDTO updatePost(Long id, PostDTO postDTO);
    void deletePost(Long id);

}

