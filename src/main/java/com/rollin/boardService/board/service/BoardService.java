package com.rollin.boardService.board.service;

import com.rollin.boardService.board.model.BoardEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {
    Boolean postBoard(BoardEntity boardEntity);
    List<BoardEntity> findAllBoard();
}
