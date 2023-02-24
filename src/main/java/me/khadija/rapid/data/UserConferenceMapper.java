package me.khadija.rapid.data;

import me.khadija.rapid.data.conference.Conference;
import me.khadija.rapid.data.user.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Set;

@Mapper
public interface UserConferenceMapper {

    @Select("SELECT user_id, " +
            "conference_id, " +
            "u.username as username, " +
            "u.hashed_password as hashed_password, " +
            "u.first_name as first_name, " +
            "u.last_name as last_name, " +
            "u.email as email, " +
            "u.enabled as enabled " +
            "FROM user_conference " +
            "INNER JOIN users u ON user_id = u.id " +
            "INNER JOIN conferences c ON conference_id = c.id " +
            "WHERE conference_id = #{id}")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "hashedPassword", column = "hashed_password"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "enabled", column = "enabled")
    })
    Set<User> findMembers(Conference conference);
    @Select("SELECT user_id, " +
            "conference_id, " +
            "c.name as name, " +
            "c.owner as owner, " +
            "c.title as title, " +
            "c.description as description " +
            "FROM user_conference " +
            "INNER JOIN users u ON user_id = u.id " +
            "INNER JOIN conferences c ON conference_id = c.id " +
            "WHERE user_id = #{id}")
    @Results({
            @Result(property = "id", column = "conference_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "owner",
                    column = "owner",
                    javaType = User.class,
                    one = @One(select = "me.khadija.rapid.data.user.UserMapper.find")
            )
    })
    Set<Conference> findConferences(User user);

    @Insert("INSERT INTO user_conference (user_id, conference_id) VALUES( #{user.id}," +
            "#{conference.id}"+
            ")")
    void insert(User user, Conference conference);
//
//    @Delete("DELETE FROM user_conference WHERE user_id = #{user.id} AND conference_id = #{conference.id}")
//    void delete(User user, Conference conference);

    @Update("CREATE TABLE IF NOT EXISTS user_conference (id int(11) NOT NULL AUTO_INCREMENT," +
            "user_id int(11) NOT NULL," +
            "conference_id int(11) NOT NULL,"+
            "PRIMARY KEY (`id`)," +
            "FOREIGN KEY (user_id) REFERENCES users(id)," +
            "FOREIGN KEY (conference_id) REFERENCES conferences(id)" +
            ")")
    void createTableIfNotExists();

}
