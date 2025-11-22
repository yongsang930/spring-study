package com.example.simple_board.board.service;

import com.example.simple_board.board.db.BoardEntity;
import com.example.simple_board.board.db.BoardRepository;
import com.example.simple_board.board.model.BoardDto;
import com.example.simple_board.board.model.BoardRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardConverter boardConverter;

    public BoardDto create(@Valid BoardRequest boardRequest){
        var entity = BoardEntity.builder().boardName(boardRequest.getBoardName()).status("REGISTERED").build();

        var saveEntity = boardRepository.save(entity);
        return boardConverter.toDto(saveEntity);
    }

    public BoardDto view(Long id) {
        var boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        ;
        return boardConverter.toDto(boardEntity);
    }
}
