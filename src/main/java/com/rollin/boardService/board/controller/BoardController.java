package com.rollin.boardService.board.controller;

import com.rollin.boardService.board.model.BoardEntity;
import com.rollin.boardService.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("board")
@Slf4j
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardEntity boardEntity;

    @PostMapping("/")
    public Boolean postBoard(@RequestBody BoardEntity boardEntity){
//        log.info(String.valueOf(securityService.getIdAtToken()));
//        boardEntity.setUserId(securityService.getIdAtToken());
        return boardService.postBoard(boardEntity);
    }

    @GetMapping("/")
    public List<BoardEntity> getAllBoard(){
        return boardService.findAllBoard();
    }
}
