package com.submission.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 申报DTO - 角色D：郭哲宇
 * @author 郭哲宇
 * @since 2026-06-11
 */
@Data
public class SubmissionDTO {

    private Long id;

    @NotBlank(message = "申报类别不能为空")
    private String category;

    @NotBlank(message = "作品名称不能为空")
    private String title;

    @NotBlank(message = "作品描述不能为空")
    private String description;

    @NotBlank(message = "申报类型不能为空")
    private String applicantType;

    private String recommendUnit;
    private String sealFile;
    private String workLink;
    private String workFiles;
    private String idCard;
    private String idCardImage;
}
