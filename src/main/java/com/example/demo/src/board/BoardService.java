package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_CREATECOMMENT;

@Service
public class BoardService {
    private final BoardDao boardDao;
    private final BoardProvider boardProvider;
    @Autowired
    DataSource dataSource;

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

    public void deleteComment(DeleteCommentReq deleteCommentReq) throws BaseException, SQLException {
        TransactionSynchronizationManager.initSynchronization(); // 트랜잭션 동기화 작업 초기화
        Connection conn= DataSourceUtils.getConnection(dataSource);
        conn.setAutoCommit(false);
        try{
            boardDao.deleteComment(deleteCommentReq);
            boardDao.deleteReComment(deleteCommentReq);
            conn.commit();

        } catch(SQLException exception){
            conn.rollback();
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }finally{
            DataSourceUtils.releaseConnection(conn,dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }


    }

    public void postBoard(PostBoardReq postBoardReq) throws SQLException {
            TransactionSynchronizationManager.initSynchronization(); // 트랜잭션 동기화 작업 초기화
            Connection conn= DataSourceUtils.getConnection(dataSource);
            conn.setAutoCommit(false);
            try {
                boardDao.postBoard(postBoardReq);
                boardDao.postBoardImg(postBoardReq);
                conn.commit();
            }catch (SQLException e){
                conn.rollback();
            }finally{
                DataSourceUtils.releaseConnection(conn,dataSource);
                TransactionSynchronizationManager.unbindResource(this.dataSource);
                TransactionSynchronizationManager.clearSynchronization();
            }

    }
}
