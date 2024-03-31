package com.myblog7.controller;


import com.myblog7.payload.CommentDto;

import com.myblog7.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    //    @Autowired
    private CommentService commentService;

    //    instead of @Autowired we did this constructor based injection to tell spring ioc to create required object
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //    handler method return type is String. all methods of controller are handler method
//    to give Response back we use ResponseEntity, @RequestBody convert the data from json to java
//    http://localhost:8080/api/comments/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId") long postId,@RequestBody CommentDto commentDto){

//        Whenever we saving the record we have to use created. the created returns back status code is 201
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);//201
    }

//    http://localhost:8080/api/comments/posts/1/comments
//    get all the comment by post_id, i.e. all comments for particular post, we have to build custom method in repository layer
@GetMapping("/posts/{postId}/comments")
public List<CommentDto> getCommentsByPostId(@PathVariable(value="postId") Long postId){

    return commentService.getCommentsByPostId(postId);
}

//    http://localhost:8080/api/comments/posts/1/comments/1
//get comment based on comment id that is assign for particular post id. we will use built-in method here.
@GetMapping("/posts/{postId}/comments/{commentId}")
public CommentDto getCommentsById(@PathVariable(value="postId") Long postId,
                                        @PathVariable(value="commentId") Long commentId){

    return commentService.getCommentsById(postId,commentId);
}

//http://localhost:8080/api/comments/allcomments
//    get all the comments
@GetMapping("/allcomments")

public List<CommentDto> getAllComments(){
        return commentService.getAllComments();
    }


//    http://localhost:8080/api/comments/posts/1/comments/1
//    @PathVariable copy the value from url and give it to controller
@DeleteMapping("/posts/{postId}/comments/{commentId}")
public ResponseEntity<String> deleteComment(@PathVariable(value="postId") Long postId,
                            @PathVariable(value="commentId") Long commentId){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("comment is deleted", HttpStatus.OK);
}


//@PathVariable copy the id from url to controller, @RequestBody giving the data to CommentDto
@PutMapping("/{id}/post/{postId}")
public ResponseEntity<CommentDto> updateComment(@PathVariable long id, @RequestBody CommentDto commentDto, @PathVariable long postId){
    CommentDto dto = commentService.updateComment(id, commentDto, postId);
    return new ResponseEntity<>(dto, HttpStatus.OK);
}


}
