package com.submission.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 申报实体类 - 角色D：郭哲宇
 * @author 郭哲宇
 * @since 2026-06-11
 */
@Data
public class Submission {

    /** 申报ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 申报类别 */
    private String category;

    /** 作品名称 */
    private String title;

    /** 作品描述 */
    private String description;

    /** 申报类型 */
    private String applicantType;

    /** 推荐单位 */
    private String recommendUnit;

    /** 盖章文件路径 */
    private String sealFile;

    /** 作品网络链接 */
    private String workLink;

    /** 相关文件路径（JSON数组存储） */
    private String workFiles;

    /** 身份证信息 */
    private String idCard;

    /** 身份证照片路径 */
    private String idCardImage;

    /** 状态：draft-未提交，submitted-已提交 */
    private String status;

    /** 提交时间 */
    private LocalDateTime submitTime;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
