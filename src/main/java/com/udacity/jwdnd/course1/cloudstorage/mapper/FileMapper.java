package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{id}")
    File getFileById(int id);

    @Select("SELECT * FORM FILES WHERE filename = #{name}")
    File getFileByName(String name);

    @Insert("INSERT INTO FILES (filename, contentType, fileSize, fileData, userId) VALUES (#{filename}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Update("UPDATE FILES SET filename = #{filename}, contentType = #{contentType}, fileSize = #{fileSize}, fileData = #{fileData} WHERE fileId = #{fileId} AND userId = #{userId}")
    int updateFile(File file);

    @Delete("DELETE * FROM FILES WHERE userId = #{file.userId} AND fileId = #{file.fileId}")
    int deleteFile(File file);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

}
