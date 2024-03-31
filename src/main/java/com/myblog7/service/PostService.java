package com.myblog7.service;


import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto);

    void deletePost(long id);

    PostDto updatePost(long id, PostDto postDto);

    PostDto getPostById(long id);

    List<PostDto> getAllPost();

    List<PostDto> getAllPostByPagination(int pageNumber, int pageSize);

//    List<PostDto> getAllPostByPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection);

    PostResponse getAllPostByPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection);

}


