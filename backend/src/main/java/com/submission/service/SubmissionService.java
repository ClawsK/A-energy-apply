package com.submission.service;

import com.submission.dto.Result;
import com.submission.dto.SubmissionDTO;
import com.submission.entity.Submission;
import com.submission.mapper.SubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 申报Service
 * @author 团队成员D
 * @since 2026-06-11
 */
@Service
public class SubmissionService {
    
    @Autowired
    private SubmissionMapper submissionMapper;
    
    /**
     * 创建申报
     */
    @Transactional
    public Result<Submission> create(SubmissionDTO dto, Long userId) {
        Submission submission = new Submission();
        submission.setUserId(userId);
        submission.setCategory(dto.getCategory());
        submission.setTitle(dto.getTitle());
        submission.setDescription(dto.getDescription());
        submission.setApplicantType(dto.getApplicantType());
        submission.setRecommendUnit(dto.getRecommendUnit());
        submission.setSealFile(dto.getSealFile());
        submission.setWorkLink(dto.getWorkLink());
        submission.setWorkFiles(dto.getWorkFiles());
        submission.setIdCard(dto.getIdCard());
        submission.setIdCardImage(dto.getIdCardImage());
        submission.setStatus("draft");
        submission.setCreateTime(LocalDateTime.now());
        submission.setUpdateTime(LocalDateTime.now());
        
        submissionMapper.insert(submission);
        return Result.success(submission);
    }
    
    /**
     * 获取申报列表
     */
    public Result<List<Submission>> getList(Long userId) {
        List<Submission> list = submissionMapper.selectByUserId(userId);
        return Result.success(list);
    }
    
    /**
     * 获取申报详情
     */
    public Result<Submission> getDetail(Long id, Long userId) {
        Submission submission = submissionMapper.selectById(id);
        if (submission == null || !submission.getUserId().equals(userId)) {
            return Result.error(404, "申报不存在");
        }
        return Result.success(submission);
    }
    
    /**
     * 更新申报
     */
    @Transactional
    public Result<Submission> update(Long id, SubmissionDTO dto, Long userId) {
        Submission submission = submissionMapper.selectById(id);
        if (submission == null || !submission.getUserId().equals(userId)) {
            return Result.error(404, "申报不存在");
        }
        
        if (!"draft".equals(submission.getStatus())) {
            return Result.error(400, "只能编辑未提交的申报");
        }
        
        submission.setCategory(dto.getCategory());
        submission.setTitle(dto.getTitle());
        submission.setDescription(dto.getDescription());
        submission.setApplicantType(dto.getApplicantType());
        submission.setRecommendUnit(dto.getRecommendUnit());
        submission.setSealFile(dto.getSealFile());
        submission.setWorkLink(dto.getWorkLink());
        submission.setWorkFiles(dto.getWorkFiles());
        submission.setIdCard(dto.getIdCard());
        submission.setIdCardImage(dto.getIdCardImage());
        submission.setUpdateTime(LocalDateTime.now());
        
        submissionMapper.update(submission);
        return Result.success(submission);
    }
    
    /**
     * 删除申报
     */
    @Transactional
    public Result<String> delete(Long id, Long userId) {
        Submission submission = submissionMapper.selectById(id);
        if (submission == null || !submission.getUserId().equals(userId)) {
            return Result.error(404, "申报不存在");
        }
        
        if (!"draft".equals(submission.getStatus())) {
            return Result.error(400, "只能删除未提交的申报");
        }
        
        submissionMapper.deleteById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 提交申报
     */
    @Transactional
    public Result<Submission> submit(Long id, Long userId) {
        Submission submission = submissionMapper.selectById(id);
        if (submission == null || !submission.getUserId().equals(userId)) {
            return Result.error(404, "申报不存在");
        }
        
        if (!"draft".equals(submission.getStatus())) {
            return Result.error(400, "申报已提交");
        }
        
        if (submission.getTitle() == null || submission.getDescription() == null || submission.getCategory() == null) {
            return Result.error(400, "信息不完整，请完善申报信息");
        }
        
        submission.setStatus("submitted");
        submission.setSubmitTime(LocalDateTime.now());
        submission.setUpdateTime(LocalDateTime.now());
        
        submissionMapper.updateStatus(submission);
        return Result.success(submission);
    }
    
    /**
     * 撤回申报
     */
    @Transactional
    public Result<Submission> withdraw(Long id, Long userId) {
        Submission submission = submissionMapper.selectById(id);
        if (submission == null || !submission.getUserId().equals(userId)) {
            return Result.error(404, "申报不存在");
        }
        
        if (!"submitted".equals(submission.getStatus())) {
            return Result.error(400, "只能撤回已提交的申报");
        }
        
        submission.setStatus("draft");
        submission.setSubmitTime(null);
        submission.setUpdateTime(LocalDateTime.now());
        
        submissionMapper.updateStatus(submission);
        return Result.success(submission);
    }
}