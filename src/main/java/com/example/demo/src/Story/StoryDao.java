package com.example.demo.src.Story;

import com.example.demo.src.Story.model.GetStorykeepRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StoryDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetStorykeepRes> getStoryKeep(Long userIdx) {
        String getStorykeepQuery = "select concat(MONTH(storyCreated_date),'월 ',DAY(storyCreated_date),'일')'storyCreated' ,storyUrl\n" +
                "from Story join User on Story.user_id=User.userIdx\n" +
                "where User.userIdx=? ";
        Long getStoryKeepParams=userIdx;
        return this.jdbcTemplate.query(getStorykeepQuery,
                (rs,rowNum)->new GetStorykeepRes(
                        rs.getString("storyCreated"),
                        rs.getString("storyUrl")
                ),getStoryKeepParams);
    }
}
