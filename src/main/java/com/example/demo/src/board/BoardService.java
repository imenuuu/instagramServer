package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.model.PostBoardCommentReq;
import com.example.demo.src.board.model.PostBoardCommentRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BoardService {
    private final BoardDao boardDao;

    @Autowired
    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public void createComment(PostBoardCommentReq postBoardCommentReq) throws BaseException {
        try {
            int result=boardDao.createComment(postBoardCommentReq);
            if(result ==0){
                throw new BaseException(MODIFY_FAIL_CREATECOMMENT);
            }

        }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }

    }

}
