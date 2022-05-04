package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.board.model.GetBoardCommentRes;
import com.example.demo.src.board.model.GetBoardFollowRes;
import com.example.demo.src.board.model.GetBoardRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

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
     * [GET] /boards? Email=
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


}
