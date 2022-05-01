package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }
/*
    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, password,email,userName,ID from UserInfo where ID = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("ID"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email")
                ),
                getPwdParams
                );

    }
*/

}
