package com.example.simple_board.post.service;

import com.example.simple_board.board.db.BoardRepository;
import com.example.simple_board.crud.Converter;
import com.example.simple_board.post.db.PostEntity;
import com.example.simple_board.post.db.PostRepository;
import com.example.simple_board.post.model.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostConverter implements Converter<PostDto, PostEntity> {

    private final BoardRepository boardRepository;

    public PostDto toDto(PostEntity postEntity){
        return PostDto.builder()
                .id(postEntity.getId())
                .userName(postEntity.getUserName())
                .status(postEntity.getStatus())
                .email(postEntity.getEmail())
                .password(postEntity.getPassword())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .postedAt(postEntity.getPostedAt())
                .boardId(postEntity.getBoard().getId())
                .build()
                ;
    }

    @Override
    public PostEntity toEntity(PostDto postDto) {

        var boardEntity = boardRepository.findById(postDto.getBoardId());

        return PostEntity.builder()
                .id(postDto.getId())
                .board(boardEntity.orElseGet(()->null))
                .status((postDto.getStatus() != null) ? postDto.getStatus() : "REGISTERED")
                .userName(postDto.getUserName())
                .email(postDto.getEmail())
                .password(postDto.getPassword())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .postedAt((postDto.getPostedAt() != null) ? postDto.getPostedAt() : LocalDateTime.now())
                .build()
                ;
    }
}
