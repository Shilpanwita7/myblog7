package com.myblog7.service.impl;


import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.CommentDto;
import com.myblog7.repository.CommentRepository;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    //    @Autowired ar bodole dependency injection
    private CommentRepository commentRepository;

    //    mapToDto and mapToEntity method ar poriborte modelmapper
    private ModelMapper modelMapper;


    private PostRepository postRepository;

//    dependency injection
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }


    //    instead of @Autowired we did this constructor injection to tell spring ioc to create required object
//    public CommentServiceImpl(CommentRepository commentRepository) {
//        this.commentRepository = commentRepository;
//    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        //    convert dto to entity
        Comment comment = mapToEntity(commentDto);

//        exception throw kora hoyeche kn2 handle kora hoyni
//        first we have to fid the post by id then we can read the comment for that particuler post. line 45-50
        Post post= postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFound("post not found with id:"+postId)
        );
//        if we found out the post with id, then the comment will save for that particular post
        comment.setPost(post);

//        then we save that comment for the table
        Comment savedComment = commentRepository.save(comment);

//convert entity to dto
        CommentDto dto = mapToDto(savedComment);
        return dto;
    }


//   this overriden method will call custom method of commentRepository
@Override
public List<CommentDto> getCommentsByPostId(long postId) {

//        to check does the post exist
    Post post= postRepository.findById(postId).orElseThrow(
            ()->new ResourceNotFound("post not found with id:"+postId)
    );
//    call custom method findByPostId
    List<Comment> comments = commentRepository.findByPostId(postId);

    List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    return commentDtos;
}


//get comment by id for particular post id so we will use build-in method
    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {

//        exception throw kora hoyeche kn2 handle kora hoyni
//        to check does the post exist
        Post post= postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFound("post not found with id:"+postId)
        );

//        to check does the comment exist
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("comment not found with id:" + commentId)
        );
        CommentDto commentDto = mapToDto(comment);

        return commentDto;
    }

//    find all comments
    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

//    delete comment
@Override
public void deleteCommentById(Long postId, Long commentId) {

//        to check does the post exist
    Post post= postRepository.findById(postId).orElseThrow(
            ()->new ResourceNotFound("post not found with id:"+postId)
    );

//        to check does the comment exist
    Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new ResourceNotFound("comment not found with id:" + commentId)
    );
    commentRepository.deleteById(commentId);

}

    @Override
    public CommentDto updateComment(long id, CommentDto commentDto, long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found for id:" + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("comment not found for id:" + id)
        );

        Comment c = mapToEntity(commentDto);
        c.setId(comment.getId());
        c.setPost(post);
        Comment savedComment = commentRepository.save(c);

        return mapToDto(savedComment);
    }


    //    convert entity to dto
    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto= modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    //        convert dto to entity
    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment=modelMapper.map(commentDto, Comment.class);
        return comment;
    }


}
