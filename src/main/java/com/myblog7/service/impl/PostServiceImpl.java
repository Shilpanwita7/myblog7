package com.myblog7.service.impl;

import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.PostService;
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

//    mapToDto and mapToEntity method ar poriborte modelmapper
    private ModelMapper modelMapper;

//    modelmapper dependency injection

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    //    instead of @Autowired we did this constructor injection to tell spring ioc to create required object
//    public PostServiceImpl(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }



    @Override
    public PostDto savePost(PostDto postDto) {

        Post post = mapToEntity(postDto);

//        getter setter injection
//    convert dto to entity
//        Post post= new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        Post savedPost = postRepository.save(post);

//convert entity to dto
//        PostDto dto=new PostDto();
//        dto.setId(savedPost.getId());
//        dto.setTitle(savedPost.getTitle());
//        dto.setDescription(savedPost.getDescription());
//        dto.setContent(savedPost.getContent());

        PostDto dto = mapToDto(savedPost);//uporer line gulo bade mapToDto method banano holo
        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
//                lamdas expression
                () -> new ResourceNotFound("Post not found with id:" + id)
        );

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);
//        convert it to dto
        PostDto dto = mapToDto(updatePost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:" + id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }

//    find all without using pagination
    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
//        convert entity into dto using stream api and lamdas exression
        List<PostDto> postDto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

//        convert entity into dto using stream api and method reference in this case we have make mapToDto method static bcz we going call call it via classname
//        List<PostDto> postDto= posts.stream().map(PostServiceImpl::mapToDto).collect(Collectors.toList());
        return postDto;
    }


//    find all with pagination
    @Override
    public List<PostDto> getAllPostByPagination(int pageNumber, int pageSize) {
//        get post with pagination, map them to mapToDto, and collect to List
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> postPage = this.postRepository.findAll(pageable);
        List<Post> postList = postPage.getContent();
        return postList.stream().map(post -> this.mapToDto(post)).collect(Collectors.toList());
    }

//    find all with pagination and sorting
    @Override
//    public List<PostDto> getAllPostByPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection)
    public PostResponse getAllPostByPaginationAndSorting(int pageNumber, int pageSize, String sortBy, String sortDirection){

//      convert String sortBy into Sort object, parameter a sortBy String ache but PageRequest.of(Sort sortBy)
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

//       to sort into asc and desc order and convert String into Sort object
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postPage = this.postRepository.findAll(pageable);
        List<Post> content = postPage.getContent();
        List<PostDto> postDtos=content.stream().map(post -> this.mapToDto(post)).collect(Collectors.toList());


        PostResponse postResponse= new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setLast(postPage.isLast());
        postResponse.setTotalPages(postPage.getTotalPages());
        return postResponse;



//        get post with pagination and sorting, map them to mapToDto, and collect to List and replacement of 119 line
//        return content.stream().map(post -> this.mapToDto(post)).collect(Collectors.toList());
    }


    //   method to convert entity into dto
    PostDto mapToDto(Post post) {
        PostDto dto=modelMapper.map(post, PostDto.class);

//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }

    // method to convert dto into entity
    Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }


}


