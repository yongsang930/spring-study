package com.example.simple_board.reply.service;

import com.example.simple_board.crud.CRUDAbstractService;
import com.example.simple_board.post.service.PostConverter;
import com.example.simple_board.reply.db.ReplyEntity;
import com.example.simple_board.reply.db.ReplyRepository;
import com.example.simple_board.reply.model.ReplyDto;
import com.example.simple_board.reply.model.ReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService extends CRUDAbstractService<ReplyDto, ReplyEntity> {

    /*
    private final ReplyRepository replyRepository;
    private final PostConverter postConverter;

    public ReplyEntity create(ReplyRequest replyRequest) {

        var postEntity = replyRepository.findById(replyRequest.getPostId())
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));

        var entity = ReplyEntity.builder()
                .post(postEntity.getPost())
                .userName(replyRequest.getUserName())
                .password(replyRequest.getPassword())
                .status("REGISTERED")
                .title(replyRequest.getTitle())
                .content(replyRequest.getContent())
                .repliedAt(LocalDateTime.now())
                .build()
                ;

        return replyRepository.save(entity);
    }

    public List<ReplyEntity> findAllByPostId(Long postId) {
        return replyRepository.findAllByPostIdAndStatusOrderByIdDesc(postId, "REGISTERED");
    }
    */
}

