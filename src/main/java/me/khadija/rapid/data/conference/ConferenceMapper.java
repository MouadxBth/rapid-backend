package me.khadija.rapid.data.conference;

import me.khadija.rapid.data.user.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface ConferenceMapper {

    @Select("SELECT * FROM conferences")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "member_limit", column = "member_limit"),
            @Result(property = "owner",
                    column = "owner",
                    javaType = User.class,
                    one = @One(select = "me.khadija.rapid.data.user.UserMapper.find")
            )
    })
    List<Conference> findAll();

    @Select("SELECT * FROM conferences WHERE name = #{name}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "member_limit", column = "member_limit"),
            @Result(property = "owner",
                    column = "owner",
                    javaType = User.class,
                    one = @One(select = "me.khadija.rapid.data.user.UserMapper.find")
            )
    })
    Conference find(@Param("name") String name);

    @Insert("INSERT INTO conferences VALUES( #{id} , #{name}," +
            "#{owner.username}," +
            "#{title}," +
            "#{description}," +
            "#{member_limit}" +
            ")")
    void insert(Conference conference);

    @Update("UPDATE conferences SET name = #{name}," +
            "owner = #{owner.username}," +
            "title = #{title}," +
            "description = #{description}, " +
            "member_limit = #{member_count} " +
            "WHERE id = #{id}")
    void update(Conference conference);

    @Delete("DELETE FROM conferences WHERE id = #{id}")
    void delete(Conference conference);

    @Update("CREATE TABLE IF NOT EXISTS conferences (id int(11) NOT NULL AUTO_INCREMENT," +
            "name varchar(255) DEFAULT NULL," +
            "owner varchar(255) DEFAULT NULL," +
            "title varchar(255) DEFAULT NULL," +
            "description varchar(128) DEFAULT NULL,"+
            "member_limit int DEFAULT -1,"+
            "PRIMARY KEY (`id`)," +
            "FOREIGN KEY (owner) REFERENCES users(username)," +
            "UNIQUE (`name`)" +
            ")")
    void createTableIfNotExists();

}
