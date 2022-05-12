package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    DataSource dataSource;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복

        if(userProvider.checkEmail(postUserReq.getUserEmail()) ==1){
            throw new BaseException(DUPLICATED_EMAIL);
        }
        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getUserPassword());
            postUserReq.setUserPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            Long userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void createUserFollow(GetUserFollowReq getUserFollowReq) throws SQLException, BaseException {

        if(userDao.getUserFollow(getUserFollowReq)==0) {


            TransactionSynchronizationManager.initSynchronization(); // 트랜잭션 동기화 작업 초기화
            Connection conn = DataSourceUtils.getConnection(dataSource);
            conn.setAutoCommit(false);
            try {
                userDao.createUserFollow(getUserFollowReq);
                userDao.createUserFollower(getUserFollowReq);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
            } finally {
                DataSourceUtils.releaseConnection(conn, dataSource);
                TransactionSynchronizationManager.unbindResource(this.dataSource);
                TransactionSynchronizationManager.clearSynchronization();
            }
        }
        else {
            throw new BaseException(EXISTS_FOLLOW);
        }

    }


    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        if (userProvider.checkuserNickname(patchUserReq.getUserNickname())==1)
        {
            throw new BaseException(USERS_EXISTS_NICKNAME);
        }
        try{

            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void modifyUserStatus(PatchUserStatusReq patchUserStatusReq) throws BaseException {
        try{
            int result=userDao.modifyUserStatus(patchUserStatusReq);
            if(result==0){
                throw new BaseException(MODIFY_FAIL_USERSTATUS);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserInfo(PutUserReq putUserReq) throws BaseException{
        if (userProvider.checkuserNickname(putUserReq.getUserNickname())==1)
        {
            throw new BaseException(USERS_EXISTS_NICKNAME);
        }
        try {
            int result =userDao.modifyUserInfo(putUserReq);
            if(result ==0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public String getKaKaoAccessToken(String code){
        String access_Token="";
        String refresh_Token ="";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=98dde62928c7a676ea17b1241492fe0b"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:9000/app/users/oauth"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }



}
