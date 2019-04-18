package com.slsd.service;

import com.slsd.entiy.Feature;
import com.slsd.entiy.TreeNode;

import java.util.List;

public interface FeatureService {

	public List<Feature> SelectFather();

	public List<Feature> SelectSon(Integer parentId);

	public boolean AddFeature(Feature feature);

	public List<Feature> SelectMangerfeatureF(Integer mid);

	public List<Feature> SelectManagerFeatureS(Integer mid, Integer parentid);

	public boolean EditFeature(Feature feature);

	public boolean Delfeature(Integer fId);

	public boolean addManagerFeature(Integer mid, Integer fid);

	public boolean delManagerFeature(Integer mid);

	public List<Feature> SelectManagerFeature(Integer mid);

	List<TreeNode> getMenuTreeNodes(int mid) throws Exception;

	public void setMenus(int mid, String menuIds) throws Exception;

	public Integer MenuCount(Feature feature);

	public List<Feature> Selectall(Integer offset, Integer length, Feature feature);

	public Feature selfeaturebyid(Integer fId);
}
