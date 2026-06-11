package com.submission.controller;

import com.submission.dto.Result;
import com.submission.dto.SubmissionDTO;
import com.submission.entity.Submission;
import com.submission.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/submissions")
@Validated
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    /**
     * 创建申报
     */
    @PostMapping
    public Result<<Submission> create(@Valid @RequestBody SubmissionDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return submissionService.create(dto, userId);
    }

    /**
     * 获取申报列表
     */
    @GetMapping
    public Result<List<<Submission>> getList(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return submissionService.getList(userId);
    }

    /**
     * 获取申报详情
     */
    @GetMapping("/{id}")
    public Result<<Submission> getDetail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return submissionService.getDetail(id, userId);
    }

    /**
     * 更新申报
     */
    @PutMapping("/{id}")
    public Result<<Submission> update(@PathVariable Long id,
                                     @Valid @RequestBody SubmissionDTO dto,
                                     HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return submissionService.update(id, dto, userId);
    }

    /**
     * 删除申报
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return submissionService.delete(id, userId);
    }

    /**
     * 提交申报
     */
    @PostMapping("/{id}/submit")
    public Result<<Submission> submit(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return submissionService.submit(id, userId);
    }

    /**
     * 撤回申报
     */
    @PostMapping("/{id}/withdraw")
    public Result<<Submission> withdraw(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return submissionService.withdraw(id, userId);
    }
}
