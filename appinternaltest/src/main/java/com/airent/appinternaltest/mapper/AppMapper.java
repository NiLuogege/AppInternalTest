package com.airent.appinternaltest.mapper;

import com.airent.appinternaltest.bean.App;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AppMapper {

    @Select("select * from app_ order by id desc")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "appName", column = "app_name"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "downloadUrl", column = "download_url"),
            @Result(property = "qrPath", column = "qr_path")
    })
    List<App> getAll();

    @Insert("insert into app_(app_name,create_date,download_url,qr_path) values(#{appName},#{createDate},#{downloadUrl},#{qrPath})")
    void insert(App app);
}
