package com.submission.mapper;

import com.submission.entity.Submission;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 申报Mapper - 角色D：郭哲宇
 * @author 郭哲宇
 * @since 2026-06-11
 */
@Mapper
public interface SubmissionMapper {

    @Insert("INSERT INTO t_submission (user_id, category, title, description, applicant_type, recommend_unit, seal_file, work_link, work_files, id_card, id_card_image, status, create_time, update_time) " +
            "VALUES (#{userId}, #{category}, #{title}, #{description}, #{applicantType}, #{recommendUnit}, #{sealFile}, #{workLink}, #{workFiles}, #{idCard}, #{idCardImage}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Submission submission);

    @Select("SELECT * FROM t_submission WHERE id = #{id}")
    Submission selectById(Long id);

    @Select("SELECT * FROM t_submission WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<<Submission> selectByUserId(Long userId);

    @Update("UPDATE t_submission SET category = #{category}, title = #{title}, description = #{description}, " +
            "applicant_type = #{applicantType}, recommend_unit = #{recommendUnit}, seal_file = #{sealFile}, " +
            "work_link = #{workLink}, work_files = #{workFiles}, id_card = #{idCard}, id_card_image = #{idCardImage}, " +
            "update_time = #{updateTime} WHERE id = #{id}")
    int update(Submission submission);

    @Update("UPDATE t_submission SET status = #{status}, submit_time = #{submitTime}, update_time = #{updateTime} WHERE id = #{id}")
    int updateStatus(Submission submission);

    @Delete("DELETE FROM t_submission WHERE id = #{id}")
    int deleteById(Long id);
}
