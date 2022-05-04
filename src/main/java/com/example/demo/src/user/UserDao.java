package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select userIdx,userName,userPhonenumber,userEmail,userNickName,userPassword,userBirth " +
                "from User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getLong("userIdx"),
                        rs.getString("userName"),
                        rs.getString("userPhonenumber"),
                        rs.getString("userEmail"),
                        rs.getString("userNickname"),
                        rs.getString("userPassword"),
                        rs.getDate("userBirth"))
                );
    }

    public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select userIdx,userName,userPhonenumber,userEmail,userNickName,userPassword,userBirth from User where userEmail =?";
        String getUsersByEmailParams = email;

        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getLong("userIdx"),
                        rs.getString("userName"),
                        rs.getString("userPhonenumber"),
                        rs.getString("userEmail"),
                        rs.getString("userNickname"),
                        rs.getString("userPassword"),
                        rs.getDate("userBirth")),
                getUsersByEmailParams);


    }
    public List<GetSearchUserRes> getSearchUsers(String searchContent) {
        String getSearchUsersBysearchContentQuery="select profileImgUrl,userNickname,userName from User where userNickname like '%searchContent%' or userName like '%searchContent%'";
        String getSearchUsersBysearchContentParams="%"+searchContent+"%";

        return this.jdbcTemplate.query(getSearchUsersBysearchContentQuery,
                (rs,rowNum)-> new GetSearchUserRes(
                        rs.getString("profileImgUrl"),
                        rs.getString("userNickname"),
                        rs.getString("userName")),getSearchUsersBysearchContentParams
                );


    }

    public GetUserRes getUser(Long userIdx){
        String getUserQuery = "select userIdx,userName,userPhonenumber,userEmail,userNickName,userPassword,userBirth from User where userIdx = ?";
        Long getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getLong("userIdx"),
                        rs.getString("userName"),
                        rs.getString("userPhonenumber"),
                        rs.getString("userEmail"),
                        rs.getString("userNickname"),
                        rs.getString("userPassword"),
                        rs.getDate("userBirth")),
                getUserParams);
    }
    

    public Long createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (userName, userPhonenumber, userEmail,userNickname,userPassword,userBirth) values (?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{
                postUserReq.getUserName(), postUserReq.getUserPhonenumber(),postUserReq.getUserEmail(), postUserReq.getUserNickname(),
                postUserReq.getUserPassword(), postUserReq.getUserBirth() };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,Long.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select userEmail from User where userEmail = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }
    public int checkuserNickname(String userNickname) {
        String checkuserNicknameQuery = "select exists(select userNickname from User where userNickname=?)";
        String checkuserNicknameParams=userNickname;
        return this.jdbcTemplate.queryForObject(checkuserNicknameQuery,
                int.class,
                checkuserNicknameParams);
    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set userNickname = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserNickname(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public int modifyUserInfo(PutUserReq putUserReq){
        String modifyUserInfoQuery = "update User set profileImgUrl=?,userName=?,userNickname=?, userIntroduce=?, userWebsite=? where userIdx=?";
        Object[] modifyUserInfoParams = new Object[]{putUserReq.getProfileImgUrl(),putUserReq.getUserName(),
                putUserReq.getUserNickname(),putUserReq.getUserIntroduce(),putUserReq.getUserWebsite(),putUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserInfoQuery,modifyUserInfoParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select profileImgUrl,userIdx,userName,userPhonenumber,userEmail,userNickName,userPassword,userBirth,userWebsite,userIntroduce from User where userEmail = ?";
        String getPwdParams = postLoginReq.getUserEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getLong("userIdx"),
                        rs.getString("profileImgUrl"),
                        rs.getString("userName"),
                        rs.getString("userPhonenumber"),
                        rs.getString("userEmail"),
                        rs.getString("userNickname"),
                        rs.getString("userPassword"),
                        rs.getDate("userBirth"),
                        rs.getString("userWebsite"),
                        rs.getString("userIntroduce")
                ),
                getPwdParams
                );

    }



}
