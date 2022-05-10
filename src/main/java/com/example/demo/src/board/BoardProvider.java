package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.model.GetBoardCommentRes;
import com.example.demo.src.board.model.GetBoardFollowRes;
import com.example.demo.src.board.model.GetBoardRes;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class BoardProvider {
    private final BoardDao boardDao;

    public BoardProvider(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public List<GetBoardRes> getBoards() throws BaseException {
        try{
            List<GetBoardRes> getBoardRes=boardDao.getBoards();
            return getBoardRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBoardRes> getBoardsByBoardIdx(Long boardIdx) throws BaseException {
        try{
            List<GetBoardRes> getBoardRes=boardDao.getBoardsByBoardIdx(boardIdx);
            return getBoardRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /*
    public List<GetBoardCommentRes> getBoardsCommentByBoardIdx(Long boardIdx)throws BaseException{
        try{
            List<GetBoardCommentRes> getBoardCommentRes=boardDao.getBoardsCommentByBoardIdx(boardIdx);
            return getBoardCommentRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }*/
    public List<GetBoardCommentRes> getBoardComment(Long boardIdx) throws BaseException{
        try{
            List<GetBoardCommentRes> getBoardCommentRes=boardDao.getBoardComment(boardIdx);
            return getBoardCommentRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBoardFollowRes> getBoardFollow(Long userIdx) throws BaseException {
        try {
            List<GetBoardFollowRes> getBoardFollowRes = boardDao.getBoardFollow(userIdx);
            return getBoardFollowRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
