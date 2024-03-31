package com.myblog7.controller;

import com.myblog7.entity.Post;
import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.service.PostService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

//    @Autowired
    private PostService postService;

//    instead of @Autowired we did this constructor based injection to tell spring ioc to create required object
    public PostController(PostService postService){
        this.postService=postService;
    }


//    handler method return type is String. all methods of controller are handler method
//    to give Response back we use ResponseEntity, @RequestBody copy the data from json to java
//    http://localhost:8080/api/post
    @PreAuthorize("hasRole('ROLE_ADMIN')")//only an admin can create a post or spring security
    @PostMapping
//    public ResponseEntity<PostDto> savePost
//    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){
    public ResponseEntity<?> savePost(@RequestBody PostDto postDto){


//        if(result.hasErrors()){
//            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        PostDto dto=postService.savePost(postDto);

//        Whenever we saving the record we have to use created. the created returns back status code is 201
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//201
    }

//    http://localhost:8080/api/post/1
//    deletion, updation, reading status code is 200
//    unable to login, wrong username and password status code is 401
//    any logical problem status code is 500 internal server error
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
      postService.deletePost(id);
      return new ResponseEntity<>("Post is deleted", HttpStatus.OK);//200
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto=postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


//    read all post without pagination and sorting
//    http://localhost:8080/api/post
    @GetMapping
    public List<PostDto> getAllPost(){
        List<PostDto> postDto= postService.getAllPost();
        return postDto;
    }

//    read all post with pagination and without sorting
//    http://localhost:8080/api/post/Pagination?pageNumber=0&pageSize=3
    @GetMapping("/Pagination")
    public List<PostDto> getAllPostWithPagination
    (@RequestParam(value = "pageNumber", required = false,defaultValue = "0") int pageNumber,
     @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize){
//        get and return all post with pagination
        return this.postService.getAllPostByPagination(pageNumber,pageSize);
    }

//    read all post with pagination and sorting
//    http://localhost:8080/api/post/Pagination?pageNumber=0&pageSize=?&sortBy=?&sortDirection=?
    @GetMapping("PaginationAndSorting")
//    public List<PostDto> getAllPostWithPaginationAndSorting
    public PostResponse getAllPostWithPaginationAndSorting
    (@RequestParam(value = "pageNumber", required = false,defaultValue = "0") int pageNumber,
     @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
    @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
    @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection){
//        get and return all post with pagination
//        return this.postService.getAllPostByPaginationAndSorting(pageNumber,pageSize,sortBy,sortDirection);

        PostResponse postResponse=postService.getAllPostByPaginationAndSorting(pageNumber,pageSize,sortBy,sortDirection);
        return postResponse;
    }

}
