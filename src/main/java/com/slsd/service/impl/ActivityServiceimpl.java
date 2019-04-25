package com.slsd.service.impl;

import com.slsd.dao.ActivityDao;
import com.slsd.entiy.Activity;
import com.slsd.service.ActivityService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Service("ActivityService")
public class ActivityServiceimpl implements ActivityService {

	@Resource
	private ActivityDao activityDao;

	@Override
	public List<Activity> SelectActivity(Integer offset, Integer length, Activity activity) {
		return activityDao.SelectActivity((offset - 1) * length, length, activity);
	}

	@Override
	public Integer countActivity(Activity activity) {
		return activityDao.countActivity(activity);
	}

	@Override
	public Activity SelectByid(Integer id) {
		return activityDao.SelectByid(id);
	}

	@Override
	public boolean addActivity(Activity activity) {
		return activityDao.addActivity(activity);
	}

	@Override
	public boolean updateActivity(Activity activity) {
		return activityDao.updateActivity(activity);
	}

	@Override
	public boolean delActivity(Integer id) {
		return activityDao.delActivity(id);
	}
}
