package com.example.demo.src.Story;

import com.example.demo.src.Story.model.GetStorykeepRes;
import com.example.demo.src.Story.model.PostHighLightReq;
import com.example.demo.src.Story.model.PostHighLightList;
import com.example.demo.src.Story.model.PostStoryReq;
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

    public int createStory(PostStoryReq posStoryReq) {
        String createStoryQuery="insert into Story(user_id, storyUrl) values(?,?) ";
        Object[] createStoryParams =new Object[]{
                posStoryReq.getUser_id(),posStoryReq.getStoryUrl()
        };

        return this.jdbcTemplate.update(createStoryQuery,createStoryParams);
    }

    public Long createHighlightList(PostHighLightList postHighlightList) {
        String createHighlightList="insert into StoryHighlightList(user_id,highlightImg,highlightTitle) values(?,?,?)";
        Object[] createHighlightListParams=new Object[]{
                postHighlightList.getUser_id(),postHighlightList.getHighlightImg(),postHighlightList.getHighlightTitle()
        };
        this.jdbcTemplate.update(createHighlightList,createHighlightListParams);

        String lastInsertIdQuery="select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,Long.class);
    }

    public int createHighlight(PostHighLightReq postHighLightReq) {
        String createHighlightQuery = "insert into StoryHighlight(story_id,highlight_id,user_id) values(?,?,?)";
        Object[] createHighlightParams = new Object[]{
                postHighLightReq.getStory_id(), postHighLightReq.getHighlight_id(), postHighLightReq.getUser_id()
        };
        return this.jdbcTemplate.update(createHighlightQuery, createHighlightParams);
    }
}
