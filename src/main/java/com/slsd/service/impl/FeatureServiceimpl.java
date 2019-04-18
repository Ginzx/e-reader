package com.slsd.service.impl;

import com.slsd.dao.FeatureDao;
import com.slsd.entiy.Feature;
import com.slsd.entiy.TreeNode;
import com.slsd.service.FeatureService;
import com.slsd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

	@Override
	public List<Feature> SelectManagerFeature(Integer mid) {
		return SelectManagerFeature(mid);
	}

	@Override
	public List<TreeNode> getMenuTreeNodes(int mid) throws Exception {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<Feature> Menus = featureDao.Selectall(0,1000,null);
		List<Feature> menus = featureDao.SelectManagerFeature(mid);
		TreeNode treeNode = new TreeNode();
		treeNode.setId(0);
		treeNode.setChecked(true);
		treeNode.setName("菜单");
		treeNode.setIsParent(true);
		treeNode.setPId(0);
		treeNodes.add(treeNode);
		for (Feature menu : Menus) {
			treeNode = new TreeNode();
			treeNode.setId(menu.getfId());
			treeNode.setName(menu.getfName());
			if (menu.getParent_id() == 0) {
				treeNode.setIsParent(true);
			} else {
				treeNode.setIsParent(false);
			}
			treeNode.setPId(menu.getParent_id());
			for (Feature menu1 : menus) {
				if (menu.getfId() == menu1.getfId()) {
					treeNode.setChecked(true);
					break;
				} else {
					treeNode.setChecked(false);
				}
			}
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/**
	 * 设置角色的菜单
	 *
	 * @param mid
	 * @param menuIds
	 * @throws Exception
	 */
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void setMenus(int mid, String menuIds) throws Exception {
		featureDao.delManagerFeature(mid);
		String[] ids = menuIds.split(",");
		for (String menuId : ids) {
			if (!StringUtil.isEmpty(menuId)) {
				int fId = Integer.parseInt(menuId);
				if (fId > 0) {
					featureDao.addManagerFeature(mid, fId);
				}
			}
		}
	}

	@Override
	public Integer MenuCount(Feature feature) {
		return featureDao.MenuCount(feature);
	}

	@Override
	public List<Feature> Selectall(Integer offset, Integer length, Feature feature) {
		return featureDao.Selectall((offset-1)*length, length, feature);
	}

	@Override
	public Feature selfeaturebyid(Integer fId) {
		return featureDao.selfeaturebyid(fId);
	}


}
