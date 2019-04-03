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

	public List<Feature> SelectFather(){
		String sql="select * from feature where parent_id=0";
		try {
			RowMapper<Feature> rowMapper= BeanPropertyRowMapper.newInstance(Feature.class);
			return  jdbcTemplate.query(sql,rowMapper);
		}catch (IndexOutOfBoundsException e){
			return null;
		}
	}

	public List<Feature> SelectSon(Integer parentId){
		String sql="select * from feature where parent_id=?";
		try {
			RowMapper<Feature> rowMapper= BeanPropertyRowMapper.newInstance(Feature.class);
			return  jdbcTemplate.query(sql,rowMapper,parentId);
		}catch (IndexOutOfBoundsException e){
			return null;
		}
	}

	public boolean AddFeature(Feature feature){
		String sql="insert into feature(f_id,f_name,parent_id) Values(?,?,?)";
		try {
			return  jdbcTemplate.update(sql,feature.getfId(),feature.getfName(),feature.getParent_id()) > 0 ? true : false;
		}catch (Exception e){
			return false;
		}
	}

	public boolean EditFeature(Feature feature){
		String sql="update feature set f_id=?,f_name=?,parent_id=? ";
		try {
			return  jdbcTemplate.update(sql,feature.getfId(),feature.getfName(),feature.getParent_id()) > 0 ? true : false;
		}catch (Exception e){
			return false;
		}
	}

	public boolean Delfeature(Integer fId){
		String sql="delete from feature where f_id=? ";
		try{
			return jdbcTemplate.update(sql,fId)> 0 ? true : false;
		}catch (Exception e){
			return false;
		}
	}
}
