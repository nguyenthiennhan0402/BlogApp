package com.springbootBlog.BlogApp.service.Impl;

import com.springbootBlog.BlogApp.dto.PostDTO;
import com.springbootBlog.BlogApp.dto.PostRespone;
import com.springbootBlog.BlogApp.entity.Post;
import com.springbootBlog.BlogApp.exception.ResourceNotFoundException;
import com.springbootBlog.BlogApp.repository.PostRepository;
import com.springbootBlog.BlogApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper){
        this.postRepository = postRepository;
        this.mapper = mapper;
    }
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        //convert dto to entity
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        Post newPost = postRepository.save(post);
        //convert entity to dto
        PostDTO postRespone = maptoDTO(post);
        return postRespone;
    }

    @Override
    public PostRespone getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page <Post> posts =  postRepository.findAll(pageable);
        //get all contents for page object
        List<Post> listOfPost =  posts.getContent();
        List<PostDTO> content =  posts.stream().map(post-> maptoDTO(post)).collect(Collectors.toList());
        PostRespone postRespone = new PostRespone();
        postRespone.setContent(content);
        postRespone.setPageNo(posts.getNumber());
        postRespone.setPageSize(posts.getSize());
        postRespone.setTotalElements(posts.getTotalElements());
        postRespone.setTotalPages(posts.getTotalPages());
        postRespone.setLast(posts.isLast());
        return postRespone;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return maptoDTO(post);
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO newPost) {
        Post oldPost = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        oldPost.setTitle(newPost.getTitle());
        oldPost.setDescription(newPost.getDescription());
        oldPost.setContent(newPost.getContent());
        postRepository.save(oldPost);
        return maptoDTO(oldPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        postRepository.delete(post);
    }

    //convert entity to dto
    private PostDTO maptoDTO(Post post){
        PostDTO postDTO = mapper.map(post,PostDTO.class);
//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());
        return postDTO;
    }
    private Post maptoEntity(PostDTO postDTO){
        Post post = mapper.map(postDTO, Post.class);
        return post;
    }
}