package com.slsd.service;

import com.slsd.entiy.Managelog;

import java.util.List;

public interface ManagelogService {

	public List<Managelog> Select(Managelog managelog);

	public boolean AddManagelog(Managelog managelog);

	public boolean editManagelog(Managelog managelog);

	public boolean delManagerlog(Managelog managelog);


}
