package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.board.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/boards")
public class BoardController {
    final Logger logger= LoggerFactory.getLogger(this.getClass());


    @Autowired
    private final BoardProvider boardProvider;
    @Autowired
    private final BoardService boardService;
    @Autowired
    private final JwtService jwtService;

    public BoardController(BoardProvider boardProvider, BoardService boardService, JwtService jwtService) {
        this.boardProvider = boardProvider;
        this.boardService = boardService;
        this.jwtService = jwtService;
    }
    /**
     * 게시물조회
     * [GET] /boards
     * @return BaseResponse<List<GetBoardRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/boards
    public BaseResponse<List<GetBoardRes>> getBoards(@RequestParam(required=false) Long BoardIdx) {
        try{
            if (BoardIdx==null){
                List<GetBoardRes> getBoardRes=boardProvider.getBoards();
                return new BaseResponse<>(getBoardRes);
            }
            List<GetBoardRes> getBoardRes = boardProvider.getBoardsByBoardIdx(BoardIdx);
            return new BaseResponse<>(getBoardRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/comments/{boardIdx}")
    public BaseResponse<List<GetBoardCommentRes>> getBoardComment(@PathVariable("boardIdx") Long boardIdx){
        try {
            List<GetBoardCommentRes> getBoardCommentRes=boardProvider.getBoardComment(boardIdx);
            return new BaseResponse<>(getBoardCommentRes);
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetBoardFollowRes>> getBoardUser(@PathVariable("userIdx") Long userIdx){
        try {
            int userIdxByJwt=jwtService.getUserIdx();
            if(userIdx!=userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetBoardFollowRes> getBoardFollowRes=boardProvider.getBoardFollow(userIdx);
            return new BaseResponse<>(getBoardFollowRes);
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> postBoard(@RequestBody Board board){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            PostBoardReq postBoardReq=new PostBoardReq((long) userIdxByJwt,board.getPositionInfo_id(),
                    board.getBoardDescription(),board.getBoardImgUrl());
            boardService.postBoard(postBoardReq);
            String result="";
            return new BaseResponse<>(result);
        }catch (BaseException e){
            return new BaseResponse<>((e.getStatus()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/comments/{boardIdx}")
    public BaseResponse<String> createComment(@PathVariable("boardIdx") Long boardIdx,@RequestBody Comment comment){
        try {
            int userIdx=jwtService.getUserIdx();
            PostBoardCommentReq postBoardCommentReq = new PostBoardCommentReq(boardIdx, (long) userIdx,comment.getComment());
            boardService.createComment(postBoardCommentReq);
            String result="";
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/comments/{boardIdx}/{commentIdx}")
    public BaseResponse<String> createRecomment(@PathVariable("boardIdx") Long boardIdx, @PathVariable("commentIdx") Long commentIdx,@RequestBody ReComment reComment){
        try {
            int userIdx=jwtService.getUserIdx();
            PostBoardRecommentReq postBoardRecommentReq = new PostBoardRecommentReq(boardIdx, commentIdx,(long) userIdx,reComment.getRecomment());
            boardService.createRecomment(postBoardRecommentReq);
            String result="";
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }
    @ResponseBody
    @PostMapping("/board-like/{boardIdx}")
    public BaseResponse<String> createBoardLike(@PathVariable("boardIdx") Long boardIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            PostBoardLikeReq postBoardLikeReq=new PostBoardLikeReq(boardIdx, (long) userIdx);
            boardService.createBoardLike(postBoardLikeReq);
            String result="";
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @ResponseBody
    @PostMapping("/comment-like/{commentIdx}")
    public BaseResponse<String> createCommentLike(@PathVariable("commentIdx") Long commentIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            PostCommentLikeReq postCommentLikeReq=new PostCommentLikeReq(commentIdx, (long) userIdx);
            boardService.createCommentLike(postCommentLikeReq);
            String result="";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @ResponseBody
    @PostMapping("/recomment-like/{commentIdx}/{reCommentIdx}")
    public BaseResponse<String> createRecommentLike(@PathVariable("commentIdx") Long commentIdx,@PathVariable("reCommentIdx") Long reCommentIdx) {
        try {
            int userIdx = jwtService.getUserIdx();
            PostRecommentLikeReq postRecommentLikeReq=new PostRecommentLikeReq(commentIdx,reCommentIdx, (long) userIdx);
            boardService.createRecommentLike(postRecommentLikeReq);
            String result="";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/comments/{commentIdx}")
    public BaseResponse<String> deleteComment(@PathVariable("commentIdx") Long commentIdx){
        try{
            int userIdx = jwtService.getUserIdx();
            DeleteCommentReq deleteCommentReq = new DeleteCommentReq(commentIdx, (long) userIdx);
            boardService.deleteComment(deleteCommentReq);
            String result="";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
/*
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postUserReq.getUserEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getUserEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
*/

}
