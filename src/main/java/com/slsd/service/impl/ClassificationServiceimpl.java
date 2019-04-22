package com.slsd.service.impl;

import com.slsd.dao.ClassificationDao;
import com.slsd.entiy.Classification;
import com.slsd.service.ClassificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description: 书籍分类管理
 * @author: Mr.Wang
 **/
@Service("ClassificationService")
public class ClassificationServiceimpl implements ClassificationService {

	@Resource
	private ClassificationDao classificationDao;

	@Override
	public List<Classification> Select() {
		return classificationDao.Select();
	}

	@Override
	public Classification Selectbyid(Integer id) {
		return classificationDao.Selectbyid(id);
	}

	@Override
	public boolean AddClass(Classification classification) {
		return classificationDao.AddClass(classification);
	}

	@Override
	public boolean editClass(Classification classification) {
		return classificationDao.editClass(classification);
	}

	@Override
	public boolean delClass(Integer id) {
		return classificationDao.delClass(id);
	}
}
