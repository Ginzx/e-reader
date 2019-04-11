package com.slsd.sql;

import com.slsd.dao.ManageDao;
import com.slsd.entiy.Manager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:Spring/root-context.xml" })
public class ManagerServiceImplTest {

	@Resource
	private ManageDao manageDao;

	@Test
	public void login() {
		Manager m=new Manager();
		m=manageDao.login("admin","admin");
		System.out.println(m.toString());
	}
}