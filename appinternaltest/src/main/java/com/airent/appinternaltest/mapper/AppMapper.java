package com.airent.appinternaltest.mapper;

import com.airent.appinternaltest.bean.App;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AppMapper {

    @Select("select * from app_")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "appName",column = "app_name"),
//            @Result(property = "createData",column = "create_data"),
            @Result(property = "downloadUrl",column = "download_url")
    })
    List<App> getAll();
}
