package com.slsd.service.impl;

import com.slsd.dao.FeatureDao;
import com.slsd.entiy.Feature;
import com.slsd.service.FeatureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description: 功能分区模块
 * @author: Mr.Wang
 **/
@Service("FeatureService")
public class FeatureServiceimpl implements FeatureService {

	@Resource
	private FeatureDao featureDao;

	@Override
	public List<Feature> SelectFather() {
		return featureDao.SelectFather();
	}

	@Override
	public List<Feature> SelectSon(Integer parentId) {
		return featureDao.SelectSon(parentId);
	}

	@Override
	public boolean AddFeature(Feature feature) {
		return featureDao.AddFeature(feature);
	}

	@Override
	public List<Feature> SelectMangerfeatureF(Integer mid) {
		return featureDao.SelectMangerfeatureF(mid);
	}

	@Override
	public List<Feature> SelectManagerFeatureS(Integer mid, Integer parentid) {
		return featureDao.SelectManagerFeatureS(mid, parentid);
	}

	@Override
	public boolean EditFeature(Feature feature) {
		return featureDao.EditFeature(feature);
	}

	@Override
	public boolean Delfeature(Integer fId) {
		return featureDao.Delfeature(fId);
	}

	@Override
	public boolean addManagerFeature(Integer mid, Integer fid) {
		return addManagerFeature(mid, fid);
	}

	@Override
	public boolean delManagerFeature(Integer mid) {
		return delManagerFeature(mid);
	}
}
