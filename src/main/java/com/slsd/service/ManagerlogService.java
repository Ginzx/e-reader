package com.slsd.service;

import com.slsd.entiy.Managerlog;

import java.util.List;

public interface ManagerlogService {

	public List<Managerlog> Select(Managerlog managerlog);

	public boolean AddManagelog(Managerlog managerlog);

	public boolean editManagelog(Managerlog managerlog);

	public boolean delManagerlog(Managerlog managerlog);


}
