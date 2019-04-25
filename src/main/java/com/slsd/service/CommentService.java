package com.slsd.service;

import com.slsd.entiy.Comment;

import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
public interface CommentService {

	public List<Comment> SelectComment(Integer offset, Integer length,Comment comment);

	public Integer CountComment(Comment comment);

	public boolean updateComment(String cIds, Integer checkFlag) throws Exception;

	public boolean delComment(String cIds) throws Exception;
}
