package com.slsd.service;

import com.slsd.entiy.Classification;

import java.util.List;

public interface ClassificationService {

	public List<Classification> Select();
	public Classification Selectbyid(Integer id);
	public boolean AddClass(Classification classification);
	public boolean editClass(Classification classification);
	public boolean delClass(Integer id);

}
