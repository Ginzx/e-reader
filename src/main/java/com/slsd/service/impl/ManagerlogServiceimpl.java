package com.slsd.service.impl;

import com.slsd.dao.ManageLogDao;
import com.slsd.entiy.Managerlog;
import com.slsd.service.ManagerlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/

@Service("ManagelogService")
public class ManagerlogServiceimpl implements ManagerlogService {

	@Resource
	private ManageLogDao manageLogDao;

	@Override
	public List<Managerlog> Select(Integer offset, Integer length, Managerlog managerlog) {
		return manageLogDao.Select((offset-1)*length, length, managerlog);
	}

	@Override
	public boolean AddManagelog(Managerlog managerlog) {
		return manageLogDao.AddManagelog(managerlog);
	}

	@Override
	public boolean editManagelog(Managerlog managerlog) {
		return manageLogDao.editManagelog(managerlog);
	}

	@Override
	public boolean delManagerlog(Managerlog managerlog) {
		return manageLogDao.delManagerlog(managerlog);
	}

	@Override
	public Integer CountManagelog(Managerlog managerlog) {
		return manageLogDao.CountManagelog(managerlog);
	}
}
