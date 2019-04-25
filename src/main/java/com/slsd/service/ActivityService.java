package com.slsd.service;

import com.slsd.entiy.Activity;

import java.util.List;

public interface ActivityService  {

	public List<Activity> SelectActivity(Integer offset, Integer length, Activity activity);

	public Integer countActivity(Activity activity);

	public Activity SelectByid(Integer id);

	public boolean addActivity(Activity activity);

	public boolean updateActivity(Activity activity);

	public boolean delActivity(Integer id);
}
