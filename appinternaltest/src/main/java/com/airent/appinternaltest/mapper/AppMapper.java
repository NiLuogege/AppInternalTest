package com.airent.appinternaltest.mapper;

import com.airent.appinternaltest.bean.App;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AppMapper {

    @Select("select * from app_ order by id desc")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appName", column = "app_name"),
            @Result(property = "md5Name", column = "md5_name"),
            @Result(property = "md5Name", column = "md5_name"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "nickname", column = "nickname")
    })
    List<App> getAll();

    @Insert("insert into app_(app_name,create_date,download_url,md5_name,nickname) " +
            "values(#{appName},#{createDate},#{downloadUrl},#{md5Name},#{nickname})")
    void insert(App app);

    @Delete("delete from app_ where id = #{id}")
    void delete(int id);

    @Update("update app_ set nickname = #{nickname} where id = #{id}")
    void update(App app);

    @Select("select * from app_ where app_name = #{appName}")
    List<App> getAppByAppName(String appName);
}
