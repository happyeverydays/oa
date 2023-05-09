package com.huizhixin.smart.workflow.camunda.mapper;

import java.util.List;

import com.huizhixin.smart.workflow.camunda.entity.CommentExtEntity;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public interface CommentMapper {
	void insertCommentExt(CommentExtEntity commentExtEntity);
	List<CommentExtEntity> selectCommentsByProcessInstanceId(String processInstanceId);
}
