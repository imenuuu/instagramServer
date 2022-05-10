package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BoardService {
    private final BoardDao boardDao;
    private final BoardProvider boardProvider;

    @Autowired
    public BoardService(BoardDao boardDao, BoardProvider boardProvider) {
        this.boardDao = boardDao;
        this.boardProvider = boardProvider;
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

    public void createRecomment(PostBoardRecommentReq postBoardReommentReq) throws BaseException {
        try {
            int result=boardDao.createRecomment(postBoardReommentReq);
            if(result==0){
                throw new BaseException(MODIFY_FAIL_CREATECOMMENT);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createBoardLike(PostBoardLikeReq postBoardCommentReq) throws BaseException{
        try {
            int result = boardDao.createBoardLike(postBoardCommentReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_CREATECOMMENT);
            }
        }catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createCommentLike(PostCommentLikeReq postCommentLikeReq) throws BaseException{
        try{
            int result=boardDao.createCommentLike(postCommentLikeReq);
            if(result ==0){
                throw new BaseException(MODIFY_FAIL_CREATECOMMENT);
            }
        }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createRecommentLike(PostRecommentLikeReq postRecommentLikeReq) throws BaseException {
        try{
            int result=boardDao.createRecommentLike(postRecommentLikeReq);
            if(result ==0){
                throw new BaseException(MODIFY_FAIL_CREATECOMMENT);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteComment(DeleteCommentReq deleteCommentReq) throws BaseException{

            int result = boardDao.deleteComment(deleteCommentReq);


    }

}
