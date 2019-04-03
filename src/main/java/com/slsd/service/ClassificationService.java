package com.slsd.service;

import com.slsd.entiy.Classification;

import java.util.List;

public interface ClassificationService {

	public List<ClassificationService> SelectFather();
	public List<ClassificationService> SelectSon(Integer parent_id);
	public boolean AddClass(Classification classification);
	public boolean editClass(Classification classification);
	public boolean delClass(Integer id);

}
