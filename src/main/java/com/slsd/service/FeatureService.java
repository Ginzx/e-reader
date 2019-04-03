package com.slsd.service;

import com.slsd.entiy.Feature;

import java.util.List;

public interface FeatureService {

	public List<Feature> SelectFather();
	public List<Feature> SelectSon(Integer parentId);
	public boolean AddFeature(Feature feature);
	public boolean EditFeature(Feature feature);
	public boolean Delfeature(Integer fId);
}
