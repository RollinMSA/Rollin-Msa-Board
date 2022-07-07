package com.rollin.boardService.board.controller;

import com.rollin.boardService.board.model.BoardEntity;
import com.rollin.boardService.board.service.BoardService;
import com.rollin.boardService.config.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("board")
@Slf4j
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardEntity boardEntity;
    @Autowired
    SecurityService securityService;

    @PostMapping("/")
    public Boolean postBoard(@RequestBody BoardEntity boardEntity){
        log.info(String.valueOf(securityService.getIdAtToken()));
        boardEntity.setUserId(Integer.valueOf(securityService.getIdAtToken().getSubject()));
        boardEntity.setUserName(String.valueOf(securityService.getIdAtToken().get("name")));
        boardEntity.setUserImg(String.valueOf(securityService.getIdAtToken().get("img")));
        return boardService.postBoard(boardEntity);
    }

    @GetMapping("/")
    public List<BoardEntity> getAllBoard(){
        return boardService.findAllBoard();
    }
}
