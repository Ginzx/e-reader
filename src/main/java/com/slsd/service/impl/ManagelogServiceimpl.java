package com.slsd.service.impl;

import com.slsd.dao.ManageLogDao;
import com.slsd.entiy.Managelog;
import com.slsd.service.ManagelogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/

@Service("ManagelogService")
public class ManagelogServiceimpl implements ManagelogService {

	@Resource
	private ManageLogDao manageLogDao;
	@Override
	public List<Managelog> Select(Managelog managelog) {
		return manageLogDao.Select(managelog);
	}

	@Override
	public boolean AddManagelog(Managelog managelog) {
		return manageLogDao.AddManagelog(managelog);
	}

	@Override
	public boolean editManagelog(Managelog managelog) {
		return manageLogDao.editManagelog(managelog);
	}

	@Override
	public boolean delManagerlog(Managelog managelog) {
		return manageLogDao.delManagerlog(managelog);
	}
}
