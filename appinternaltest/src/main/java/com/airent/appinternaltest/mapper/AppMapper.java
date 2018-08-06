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
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "downloadUrl", column = "download_url")
    })
    List<App> getAll();

    @Insert("insert into app_(app_name,create_date,download_url,md5_name) " +
            "values(#{appName},#{createDate},#{downloadUrl},#{md5Name})")
    void insert(App app);

    @Delete("delete from app_ where id = #{id}")
    void delete(int id);

    @Select("select * from app_ where app_name = #{appName}")
    List<App> getAppByAppName(String appName);
}
