package com.slsd.service.impl;

import com.slsd.dao.CommentDao;
import com.slsd.entiy.Comment;
import com.slsd.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Service("CommentService")
public class CommentServiceimpl implements CommentService {

	@Resource
	private CommentDao commentDao;

	@Override
	public List<Comment> SelectComment(Integer offset, Integer length, Comment comment) {
		return commentDao.SelectComment((offset-1)*length,length,comment);
	}

	@Override
	public Integer CountComment(Comment comment) {
		return commentDao.CountComment(comment);
	}

	@Override
	public boolean updateComment(String cIds, Integer checkFlag) throws Exception {
		String[] ids = cIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			if (!commentDao.updateComment(Integer.valueOf(ids[i]), checkFlag)) {
				throw new Exception("更新错误，回滚");
			}
		}
		return true;
	}

	@Override
	public boolean delComment(String cIds) throws Exception {
		String[] ids = cIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			if (!commentDao.delComment(Integer.valueOf(ids[i]))) {
				throw new Exception("删除错误，回滚");
			}
		}
		return true;
	}
}
