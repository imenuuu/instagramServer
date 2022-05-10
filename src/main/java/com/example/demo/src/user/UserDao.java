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


    public List<GetUserProfileRes> getUserProfile(String nickName) {
        String getProfileQuery="select userNickname,profileImgUrl,\n" +
                "       userCelleb,count(Board.user_id)'boardCnt',\n" +
                "       (select count(Follower.user_id) from Follower where Follower.followerid=userIdx)'followerCnt'\n" +
                ",(select count(Follow.user_id) from Follow where Follow.user_id=userIdx)'followCnt',userName,\n" +
                "       userIntroduce,userWebsite\n" +
                "from User\n" +
                "left join Board on Board.user_id=User.userIdx where User.userNickname=?";
        String getProfileParams=nickName;
        return this.jdbcTemplate.query(getProfileQuery,
                (rs,rowNum)->new GetUserProfileRes(
                        rs.getString("userNickname"),
                        rs.getString("profileImgUrl"),
                        rs.getString("userCelleb"),
                        rs.getLong("boardCnt"),
                        rs.getLong("followerCnt"),
                        rs.getLong("followCnt"),
                        rs.getString ("userName"),
                        rs.getString("userIntroduce"),
                        rs.getString("userWebsite")

                ),getProfileParams
                );
    }
    public List<GetUserHighlightRes> getUserHighlight(String nickName) {
        String getUserHighlightQuery="select highlightImg,highlightTitle from StoryHighlightList join User on userIdx=user_id where userNickname=?";
        String getUserHighlightParams=nickName;
        return this.jdbcTemplate.query(getUserHighlightQuery,
                (rs,rowNUm)->new GetUserHighlightRes(
                        rs.getString("highlightImg"),
                        rs.getString("highlightTitle")
                ),getUserHighlightParams);
    }

    public List<GetUserBoardRes> getUserBoard(String nickName) {
        String getUserBoardQuery="select boardImgUrl from BoardImg join User on BoardImg.user_Id=User.userIdx where userNickName=? and boardImgIndex=1";
        String getUserBoardParams=nickName;
        return this.jdbcTemplate.query(getUserBoardQuery,
                (rs,rowNum)->new GetUserBoardRes(
                        rs.getString("boardImgUrl")
                ),getUserBoardParams);

    }


    public int modifyUserStatus(PatchUserStatusReq patchUserStatusReq) {
        String patchUserStatusQuery="update User set userStatus='FALSE' where userIdx=?";
        Object[] patchUserStausParams=new Object[]{
                patchUserStatusReq.getUserIdx()
        };
        return this.jdbcTemplate.update(patchUserStatusQuery,patchUserStausParams);
    }

    public int checkUserStatus(String userEmail) {
        String checkUserStatusQuery="select exists(select userEmail from User where userEmail=? and userStatus='FALSE')";
        String checkUserStatusParams=userEmail;

        return this.jdbcTemplate.queryForObject(checkUserStatusQuery,int.class,checkUserStatusParams);
    }

    public List<GetSearchUserRes> getSearchUsers(GetSearchUserReq getSearchUserReq) {
        String getSearchUserQuery="select profileImgUrl,userNickname,userName from User where userNickname like ? or userName like ?";
        String getSearchuserNicknameParmas="%"+getSearchUserReq.getUserNickname()+"%";
        String getSearchuserNameParmas="%"+getSearchUserReq.getUserName()+"%";
        return this.jdbcTemplate.query(getSearchUserQuery,
                (rs,rowNum)->new GetSearchUserRes(
                        rs.getString("profileImgUrl"),
                        rs.getString("userNickname"),
                        rs.getString("userName")
                ),getSearchuserNicknameParmas,getSearchuserNameParmas
                );
    }

    public int getSearchUsersCount(GetSearchUserReq getSearchUserReq) {
        String getSearchUsersCountQuery="select exists(select profileImgUrl,userNickname,userName " +
                "from User where userNickname like ? or userName like ?)";
        String getSearchuserNicknameParmas="%"+getSearchUserReq.getUserNickname()+"%";
        String getSearchuserNameParmas="%"+getSearchUserReq.getUserName()+"%";
        return this.jdbcTemplate.queryForObject(getSearchUsersCountQuery,int.class,getSearchuserNicknameParmas,getSearchuserNameParmas);
    }

    public int checkUserStatusbyUserNickname(String userNickname) {
        String checkUserStatusQuery="select exists(select userEmail from User where userNickname=? and userStatus='FALSE')";
        String checkUserStatusParams=userNickname;

        return this.jdbcTemplate.queryForObject(checkUserStatusQuery,int.class,checkUserStatusParams);
    }
}
