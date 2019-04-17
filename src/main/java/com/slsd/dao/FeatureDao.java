package com.slsd.dao;

import com.slsd.entiy.Feature;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description:管理员功能分类
 * @author: Mr.Wang
 **/
@Repository
public class FeatureDao {


	@Resource
	private JdbcTemplate jdbcTemplate;

	public List<Feature> Selectall() {
		String sql = "select * from feature order by f_id ";
		try {
			RowMapper<Feature> rowMapper = BeanPropertyRowMapper.newInstance(Feature.class);
			return jdbcTemplate.query(sql, rowMapper);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * 查找父类菜单
	 *
	 * @return
	 */
	public List<Feature> SelectFather() {
		String sql = "select * from feature where parent_id=0 order by f_id ";
		try {
			RowMapper<Feature> rowMapper = BeanPropertyRowMapper.newInstance(Feature.class);
			return jdbcTemplate.query(sql, rowMapper);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * 查找子类菜单
	 *
	 * @param parentId
	 * @return
	 */
	public List<Feature> SelectSon(Integer parentId) {
		String sql = "select * from feature where parent_id=? order by f_id";
		try {
			RowMapper<Feature> rowMapper = BeanPropertyRowMapper.newInstance(Feature.class);
			return jdbcTemplate.query(sql, rowMapper, parentId);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * 根据用户id查找父类
	 *
	 * @param mid
	 * @return
	 */
	public List<Feature> SelectMangerfeatureF(Integer mid) {
		String sql = "select * from feature  where f_id in" +
				"(select f_id from manager_feature where m_id=?) " +
				"and parent_id=0 ORDER BY feature.f_id ";
		try {
			RowMapper<Feature> rowMapper = BeanPropertyRowMapper.newInstance(Feature.class);
			return jdbcTemplate.query(sql, rowMapper, mid);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public List<Feature> SelectManagerFeatureS(Integer mid, Integer parentid) {
		String sql = "select * from feature  where f_id in" +
				"(select f_id from manager_feature where m_id=?) " +
				"and parent_id=? ORDER BY feature.f_id ";
		try {
			RowMapper<Feature> rowMapper = BeanPropertyRowMapper.newInstance(Feature.class);
			return jdbcTemplate.query(sql, rowMapper, mid, parentid);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public List<Feature> SelectManagerFeature(Integer mid) {
		String sql = "select f_id from manager_feature where m_id=?";
		try {
			RowMapper<Feature> rowMapper = BeanPropertyRowMapper.newInstance(Feature.class);
			return jdbcTemplate.query(sql, rowMapper, mid);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean AddFeature(Feature feature) {
		String sql = "insert into feature(f_id,f_name,parent_id,parent_adress) Values(?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, feature.getfId(), feature.getfName(), feature.getParent_id(), feature.getParentAdress()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean EditFeature(Feature feature) {
		String sql = "update feature set f_id=?,f_name=?,parent_id=?,parent_adress=? ";
		try {
			return jdbcTemplate.update(sql, feature.getfId(), feature.getfName(), feature.getParent_id(), feature.getParentAdress()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean Delfeature(Integer fId) {
		String sql = "delete from feature where f_id=? ";
		try {
			return jdbcTemplate.update(sql, fId) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}


	public boolean addManagerFeature(Integer mid, Integer fid) {
		String sql = "insert into manager_feature (m_id,f_id) Values(?,?)";
		try {
			return jdbcTemplate.update(sql, mid, fid) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delManagerFeature(Integer mid) {
		String sql = "delete from manager_feature where m_id=?";
		try {
			return jdbcTemplate.update(sql, mid) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
