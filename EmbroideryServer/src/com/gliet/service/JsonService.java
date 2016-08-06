package com.gliet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gliet.dbutils.JdbcUtils;
import com.gliet.domain.Admin;
import com.gliet.domain.Appreciation;
import com.gliet.domain.Appreciationdetail;
import com.gliet.domain.Home;
import com.gliet.domain.Homedetail;
import com.gliet.domain.Information;
import com.gliet.domain.Informationdetail;
import com.gliet.domain.Qanda;
import com.gliet.domain.Qandadetail;
import com.gliet.domain.Save;
import com.gliet.domain.Shuxiu;
import com.gliet.domain.Shuxiudetail;
import com.gliet.domain.Suxiu;
import com.gliet.domain.Suxiudetail;
import com.gliet.domain.User;
import com.gliet.domain.Xiangxiu;
import com.gliet.domain.Xiangxiudetail;
import com.gliet.domain.Yuxiu;
import com.gliet.domain.Yuxiudetail;

public class JsonService {

	public JsonService() {
	}

	// 根据student的s_num从数据库中获得该student表的一条记录
	public Xiangxiudetail getXiangxiudetail(String num) {
		Xiangxiudetail xiangxiudetail = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from xiangxiudetail where num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		try {
			xiangxiudetail = jdbcUtils.findSimpleRefResult(sql, params,
					Xiangxiudetail.class);
			System.out.println("----------------------------->"
					+ xiangxiudetail);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return xiangxiudetail;
	}

	// 根据student的s_num从数据库中获得该student表的一条记录
	public Informationdetail getInformationdetail(String num) {
		Informationdetail informationdetail = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from informationdetail where num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		try {
			informationdetail = jdbcUtils.findSimpleRefResult(sql, params,
					Informationdetail.class);
			System.out.println("----------------------------->"
					+ informationdetail);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return informationdetail;
	}

	// 根据student的s_num从数据库中获得该student表的一条记录
	public Homedetail getHomedetail(String num) {
		Homedetail homedetail = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from homedetail where num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		try {
			homedetail = jdbcUtils.findSimpleRefResult(sql, params,
					Homedetail.class);
			System.out.println("----------------------------->" + homedetail);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return homedetail;
	}

	// 根据student的s_num从数据库中获得该student表的一条记录
	public Shuxiudetail getShuxiudetail(String num) {
		Shuxiudetail shuxiudetail = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from shuxiudetail where num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		try {
			shuxiudetail = jdbcUtils.findSimpleRefResult(sql, params,
					Shuxiudetail.class);
			System.out.println("----------------------------->" + shuxiudetail);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return shuxiudetail;
	}

	// 根据student的s_num从数据库中获得该student表的一条记录
	public Yuxiudetail getYuxiudetail(String num) {
		Yuxiudetail yuxiudetail = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from yuxiudetail where num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		try {
			yuxiudetail = jdbcUtils.findSimpleRefResult(sql, params,
					Yuxiudetail.class);
			System.out.println("----------------------------->" + yuxiudetail);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return yuxiudetail;
	}

	// 根据student的s_num从数据库中获得该student表的一条记录
	public Suxiudetail getSuxiudetail(String num) {
		Suxiudetail suxiudetail = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from suxiudetail where num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		try {
			suxiudetail = jdbcUtils.findSimpleRefResult(sql, params,
					Suxiudetail.class);
			System.out.println("----------------------------->" + suxiudetail);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return suxiudetail;
	}

	// 根据student的s_num从数据库中获得该student表的一条记录
	public Qandadetail getQandadetail(String num) {
		Qandadetail qandadetail = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from qandadetail where num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		try {
			qandadetail = jdbcUtils.findSimpleRefResult(sql, params,
					Qandadetail.class);
			System.out.println("----------------------------->" + qandadetail);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return qandadetail;
	}

	// 根据student的s_num从数据库中获得该student表的一条记录
	public Appreciationdetail getAppreciationdetail(String num) {
		Appreciationdetail appreciationdetail = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from appreciationdetail where num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		try {
			appreciationdetail = jdbcUtils.findSimpleRefResult(sql, params,
					Appreciationdetail.class);
			System.out.println("----------------------------->"
					+ appreciationdetail);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return appreciationdetail;
	}

	// 查询message表的最后一条记录
	public Admin getLastAdmin() {
		Admin admin = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from admin order by m_id desc limit 1";
		try {
			admin = jdbcUtils.findSimpleRefResult(sql, null, Admin.class);
			System.out.println("----------------------------->" + admin);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return admin;
	}

	// 查询message表的最后一条记录
	public User getLastUser() {
		User user = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from user order by u_id desc limit 1";
		try {
			user = jdbcUtils.findSimpleRefResult(sql, null, User.class);
			System.out.println("----------------------------->" + user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return user;
	}

	public List<Xiangxiu> getlistXiangxiu() {
		List<Xiangxiu> list = new ArrayList<Xiangxiu>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from xiangxiu";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Xiangxiu.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Appreciation> getListAppreciation() {
		List<Appreciation> list = new ArrayList<Appreciation>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from appreciation";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Appreciation.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	// 从数据库中获得home表的全部数据
	public List<Home> getListHome() {
		List<Home> list = new ArrayList<Home>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from home";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Home.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Admin> getListAdmin() {
		List<Admin> list = new ArrayList<Admin>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from admin";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Admin.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Save> getListSave() {
		List<Save> list = new ArrayList<Save>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from save";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Save.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public User getUser(String u_num) {
		User user = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from user where u_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(u_num);
		try {
			user = jdbcUtils.findSimpleRefResult(sql, params, User.class);
			System.out.println("----------------------------->" + user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return user;
	}

	public List<User> getListUser() {
		List<User> list = new ArrayList<User>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from user";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, User.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Admin getAdmin(String m_num) {
		Admin admin = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from admin where m_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(m_num);
		try {
			admin = jdbcUtils.findSimpleRefResult(sql, params, Admin.class);
			System.out.println("----------------------------->" + admin);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return admin;
	}

	public List<Save> getlistSaveWithU_num(String u_num) {
		List<Save> list = new ArrayList<Save>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from save where  u_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(u_num);
		try {
			list = jdbcUtils.findMoreRefResult(sql, params, Save.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Save getSave(String s_num, String u_num) {
		Save save = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from save where s_num= ? and u_num=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(s_num);
		params.add(u_num);
		try {
			save = jdbcUtils.findSimpleRefResult(sql, params, Save.class);
			System.out.println("----------------------------->" + save);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return save;
	}

	public List<Admin> getlistAdmin() {
		List<Admin> list = new ArrayList<Admin>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from admin";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Admin.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Information getInformation(String num) {
		Information information = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from information where num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		try {
			information = jdbcUtils.findSimpleRefResult(sql, params,
					Information.class);
			System.out.println("----------------------------->" + information);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return information;
	}

	public List<Suxiu> getListSuxiu() {
		List<Suxiu> list = new ArrayList<Suxiu>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from suxiu";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Suxiu.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Information> getlistInformation() {
		List<Information> list = new ArrayList<Information>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from information";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Information.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Shuxiu> getlistShuxiu() {
		List<Shuxiu> list = new ArrayList<Shuxiu>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from  shuxiu";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Shuxiu.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Qanda> getlistQanda() {
		List<Qanda> list = new ArrayList<Qanda>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from qanda";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Qanda.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Yuxiu> getlistYuxiu() {
		List<Yuxiu> list = new ArrayList<Yuxiu>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from yuxiu";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Yuxiu.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> getListMaps() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("id", 1001);
		map1.put("name", "jack");
		map1.put("address", "beijing");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("id", 1002);
		map2.put("name", "rose");
		map2.put("address", "shanghai");
		list.add(map1);
		list.add(map2);
		return list;
	}
}
