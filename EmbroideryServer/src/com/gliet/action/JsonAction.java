package com.gliet.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gliet.dbutils.JdbcUtils;
import com.gliet.domain.Admin;
import com.gliet.domain.Appreciationdetail;
import com.gliet.domain.Homedetail;
import com.gliet.domain.Informationdetail;
import com.gliet.domain.Qandadetail;
import com.gliet.domain.Save;
import com.gliet.domain.Shuxiudetail;
import com.gliet.domain.Suxiudetail;
import com.gliet.domain.User;
import com.gliet.domain.Xiangxiudetail;
import com.gliet.domain.Yuxiudetail;
import com.gliet.jsontools.JsonTools;
import com.gliet.jsontools.JsonTools2;
import com.gliet.service.JsonService;

public class JsonAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304 * 2L;// 4*2MB
	private String filedir = null;

	private static final String FLAG = "updatedImage";
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	private JsonService service;
	private String jsonString = "";
	private String filename = "";
	private String addUseruploadPictureState = "";
	private String updateUseruploadPictureState = "";

	public JsonAction() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String action_flag = request.getParameter("action_flag");
		// 文件上传
		if (action_flag.equals("upload_file")) {
			uploadFile(request, response);
		}

		// 获取用户主界面各栏目数据组
		else if (action_flag.equals("home_url")) {
			jsonString = JsonTools.createJsonString("home_url",
					service.getListHome());
		} else if (action_flag.equals("appreciation_url")) {
			jsonString = JsonTools.createJsonString("appreciation_url",
					service.getListAppreciation());
		} else if (action_flag.equals("information_url")) {
			jsonString = JsonTools.createJsonString("information_url",
					service.getlistInformation());
		} else if (action_flag.equals("qanda_url")) {
			jsonString = JsonTools.createJsonString("qanda_url",
					service.getlistQanda());
		} else if (action_flag.equals("shuxiu_url")) {
			jsonString = JsonTools.createJsonString("shuxiu_url",
					service.getlistShuxiu());
		} else if (action_flag.equals("suxiu_url")) {
			jsonString = JsonTools.createJsonString("suxiu_url",
					service.getListSuxiu());
		} else if (action_flag.equals("xiangxiu_url")) {
			jsonString = JsonTools.createJsonString("xiangxiu_url",
					service.getlistXiangxiu());
		} else if (action_flag.equals("yuxiu_url")) {
			jsonString = JsonTools.createJsonString("yuxiu_url",
					service.getlistYuxiu());
		}

		else if (action_flag.equals("get_detail_data")) {
			queryDetailDataFromDateBase(request);
		}

		// 修改密码组
		else if (action_flag.equals("update_user_pwd")) {
			String sql = "update user set  u_pwd = ?  where u_num = ? ";
			updatePwdFromDateBase(request, sql);
		} else if (action_flag.equals("update_admin_pwd")) {
			String sql = "update admin set  m_pwd = ?  where m_num = ? ";
			updatePwdFromDateBase(request, sql);
		}

		// 学者注册
		else if (action_flag.equals("user_last_url")) {
			queryUserLastFromDateBase(request);
		}

		// 收藏组
		else if (action_flag.equals("add_save")) {
			addSaveToDateBase(request);
		} else if (action_flag.equals("save_with_u_num")) {
			querySaveWithU_numFromDateBase(request);
		} else if (action_flag.equals("save")) {
			querySaveFromDateBase(request);
		}

		// 管理员组
		else if (action_flag.equals("admin")) {
			queryAdminFromDateBase(request);
		} else if (action_flag.equals("admins")) {
			jsonString = JsonTools.createJsonString("admins",
					service.getListAdmin());
		} else if (action_flag.equals("addadmin")) {
			addAdminToDateBase(request);
		} else if (action_flag.equals("updateadmin")) {
			updateAdminToDateBase(request);
		} else if (action_flag.equals("deleteadmin")) {
			deleteAdminFromDateBase(request);
		}

		// 学者组
		else if (action_flag.equals("users")) {
			jsonString = JsonTools.createJsonString("users",
					service.getListUser());
		} else if (action_flag.equals("user")) {
			queryUserFromDateBase(request);
		} else if (action_flag.equals("adduser")) {
			addUserToDateBase(request);
		} else if (action_flag.equals("updateuser")) {
			updateUserToDateBase(request);
		} else if (action_flag.equals("deleteuser")) {
			deleteUserFromDateBase(request);
		}

		// 登录组
		else if (action_flag.equals("userlogin")) {
			userLoginFromDateBase(request);
		} else if (action_flag.equals("adminlogin")) {
			adminLoginFromDateBase(request);
		}

		// 控制台显示日志
		System.out.println("服务器向客户端发送的数据---->" + jsonString);
		// 向客服端发送数据
		out.println(jsonString);
		// 每次向客户端发送数据后清空jsonString
		jsonString = "";
		out.flush();
		out.close();
	}

	private void querySaveWithU_numFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String u_num = (String) map.get("u_num");
		List<Save> list = service.getlistSaveWithU_num(u_num);
		if (list != null) {
			jsonString = JsonTools.createJsonString("save_with_u_num", list);
		}
	}

	private void querySaveFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String s_num = (String) map.get("s_num");
		String u_num = (String) map.get("u_num");
		Save save = service.getSave(s_num, u_num);
		if (save != null) {
			jsonString = JsonTools.createJsonString("save", save);
		}
	}

	private void addSaveToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into save(u_num, s_table,  s_num,  s_title, s_img)"
				+ "values(?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("u_num"));
		params.add(map.get("s_table"));
		params.add(map.get("s_num"));
		params.add(map.get("s_title"));
		params.add(map.get("s_img"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void queryDetailDataFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String reTable = (String) map.get("re_table");
		String num = (String) map.get("num");

		if (reTable.equals("home")) {
			Homedetail homedetail = service.getHomedetail(num);
			if (homedetail != null) {
				jsonString = JsonTools.createJsonString("send_to_client",
						homedetail);
			}
		} else if (reTable.equals("information")) {
			Informationdetail informationdetail = service
					.getInformationdetail(num);
			if (informationdetail != null) {
				jsonString = JsonTools.createJsonString("send_to_client",
						informationdetail);
			}
		} else if (reTable.equals("xiangxiu")) {
			Xiangxiudetail xiangxiudetail = service.getXiangxiudetail(num);
			if (xiangxiudetail != null) {
				jsonString = JsonTools.createJsonString("send_to_client",
						xiangxiudetail);
			}
		} else if (reTable.equals("shuxiu")) {
			Shuxiudetail shuxiudetail = service.getShuxiudetail(num);
			if (shuxiudetail != null) {
				jsonString = JsonTools.createJsonString("send_to_client",
						shuxiudetail);
			}
		} else if (reTable.equals("yuxiu")) {
			Yuxiudetail yuxiudetail = service.getYuxiudetail(num);
			if (yuxiudetail != null) {
				jsonString = JsonTools.createJsonString("send_to_client",
						yuxiudetail);
			}
		} else if (reTable.equals("suxiu")) {
			Suxiudetail suxiudetail = service.getSuxiudetail(num);
			if (suxiudetail != null) {
				jsonString = JsonTools.createJsonString("send_to_client",
						suxiudetail);
			}
		} else if (reTable.equals("qanda")) {
			Qandadetail qandadetail = service.getQandadetail(num);
			if (qandadetail != null) {
				jsonString = JsonTools.createJsonString("send_to_client",
						qandadetail);
			}
		} else if (reTable.equals("appreciation")) {
			Appreciationdetail appreciationdetail = service
					.getAppreciationdetail(num);
			if (appreciationdetail != null) {
				jsonString = JsonTools.createJsonString("send_to_client",
						appreciationdetail);
			}
		}

	}

	private void queryUserLastFromDateBase(HttpServletRequest request) {
		User user = service.getLastUser();
		if (user != null) {
			jsonString = JsonTools.createJsonString("user_last", user);
		}
	}

	private void updatePwdFromDateBase(HttpServletRequest request, String sql) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("user_password"));
		params.add(map.get("user_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void addAdminToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into admin( m_num,  m_name,  m_pwd, m_phone)"
				+ "values(?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("m_num"));
		params.add(map.get("m_name"));
		params.add(map.get("m_pwd"));
		params.add(map.get("m_phone"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void deleteAdminFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String m_num = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "delete from admin where m_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(m_num);
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void updateAdminToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update admin set  m_name = ?, m_pwd = ?,m_phone = ? where m_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("m_name"));
		params.add(map.get("m_pwd"));
		params.add(map.get("m_phone"));
		params.add(map.get("m_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void queryAdminFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String m_num = (String) map.get("m_num");
		Admin admin = service.getAdmin(m_num);
		if (admin != null) {
			jsonString = JsonTools.createJsonString("admin", admin);
		}
	}

	@SuppressWarnings("unchecked")
	private void uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getSession().getServletContext()
				.getRealPath("/head");
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory
															// for disk-based
															// file items
		this.upload = new ServletFileUpload(factory);// Create a new file upload
														// handler
		this.upload.setSizeMax(this.MAXSize);// Set overall request size
												// constraint 4194304
		filedir = path;
		System.out.println("filedir=" + filedir);

		PrintWriter out = response.getWriter();
		try {
			List<FileItem> items = this.upload.parseRequest(request);
			if (items != null && !items.isEmpty()) {
				for (FileItem fileItem : items) {
					filename = fileItem.getName();
					String filepath = filedir + File.separator + filename;
					System.out.println("文件保存路径为:" + filepath);
					File file = new File(filepath);
					InputStream inputSteam = fileItem.getInputStream();
					BufferedInputStream fis = new BufferedInputStream(
							inputSteam);
					FileOutputStream fos = new FileOutputStream(file);
					int f;
					while ((f = fis.read()) != -1) {
						fos.write(f);
					}
					fos.flush();
					fos.close();
					fis.close();
					inputSteam.close();
					System.out.println("文件：" + filename + "上传成功!");
				}
			}
			System.out.println("上传文件成功!");
			out.write("上传文件成功!");
			addUseruploadPictureState = SUCCESS;
			updateUseruploadPictureState = SUCCESS;
		} catch (FileUploadException e) {
			e.printStackTrace();
			out.write("上传文件失败:" + e.getMessage());
			addUseruploadPictureState = FAIL;
			updateUseruploadPictureState = FAIL;
		}

	}

	private void deleteUserFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String u_num = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "delete from user where u_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(u_num);
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void updateUserToDateBaseItem(Map<String, Object> map,
			HttpServletRequest request, String imgName) {
		boolean flag = false;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update user set  u_name = ? , u_pwd = ?,u_phone = ? ,u_img = ?  where u_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("u_name"));
		params.add(map.get("u_pwd"));
		params.add(map.get("u_phone"));
		params.add(imgName);
		params.add(map.get("u_num"));

		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void updateUserToDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String flagValue = (String) map.get(FLAG);
		System.out.println("flagValue:---------->" + flagValue);
		// 没有执行图片上传操作：也就是客户端没有修改图片
		if (flagValue.equals("false")) {
			updateUserToDateBaseItem(map, request, (String) map.get("u_img"));
		}
		// 执行了图片上传操作：也就是客户端修改了图片，由于图片上传是异步的：等待图片上传完成获取图片名称
		else if (flagValue.equals("true")) {
			boolean s = true;
			// 循环等待图片上传完成
			while (s) {
				if (updateUseruploadPictureState.equals(SUCCESS)) {
					// 将session保留的数据销毁
					updateUseruploadPictureState = "";
					updateUserToDateBaseItem(map, request, filename);
					System.out
							.println("插入到数据库的filename:---------->" + filename);
					s = false;
					// 图片未上传成功,向客户端发送图片上传不成功的消息
				} else if (updateUseruploadPictureState.equals(FAIL)) {
					// 将session保留的数据销毁
					updateUseruploadPictureState = "";
					jsonString = JsonTools.createJsonString(SUCCESS, FAIL);
					s = false;
				}
			}
		}
	}

	/**
	 */
	private void addUserToDateBase(HttpServletRequest request) {
		boolean s = true;
		// 循环等待图片上传完成
		while (s) {
			if (addUseruploadPictureState.equals(SUCCESS)) {
				// 将session保留的数据销毁
				addUseruploadPictureState = "";
				boolean flag = false;
				Map<String, Object> map = getJavaBeanMap(request);
				JdbcUtils jdbcUtils = new JdbcUtils();
				jdbcUtils.getConnection();
				String sql = "insert into user( u_num,  u_name,  u_pwd, u_phone,u_img)"
						+ "values(?,?,?,?,?)";
				List<Object> params = new ArrayList<Object>();
				params.add(map.get("u_num"));
				params.add(map.get("u_name"));
				params.add(map.get("u_pwd"));
				params.add(map.get("u_phone"));
				// 由于是上传图书图片成功之后才执行添加图书的操作，所以filename（图片的名称）是不会=""的,除非上传失败！
				params.add(filename);
				try {
					flag = jdbcUtils.updateByPreparedStatement(sql, params);
					System.out.println("----------------->" + flag);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					jdbcUtils.releaseConn();
				}
				if (flag == true) {
					jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
				}
				s = false;
				// 图片未上传成功,向客户端发送图片上传不成功的消息
			} else if (addUseruploadPictureState.equals(FAIL)) {
				// 将session保留的数据销毁
				addUseruploadPictureState = "";
				jsonString = JsonTools.createJsonString(SUCCESS, FAIL);
				s = false;
			}
		}
	}

	private void queryUserFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String u_num = (String) map.get("u_num");
		User user = service.getUser(u_num);
		if (user != null) {
			jsonString = JsonTools.createJsonString("user", user);
		}
	}

	private void userLoginFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String u_num = (String) map.get("u_num");
		User user = service.getUser(u_num);
		if (user != null) {
			jsonString = JsonTools.createJsonString("user", user);
		}
	}

	private void adminLoginFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String username = (String) map.get("m_num");
		Admin admin = service.getAdmin(username);
		if (admin != null) {
			jsonString = JsonTools.createJsonString("admin", admin);
		}

	}

	public String getClientDate(HttpServletRequest request) {

		String jsonString = "";
		BufferedInputStream in = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			in = new BufferedInputStream(request.getInputStream());
			while ((len = in.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			jsonString = new String(outputStream.toByteArray());
			// String s=new String(jsonString.getBytes("iso8859-1"),"utf-8");
			// System.out.println("从客户端传到服务端的数据---------1>"+s);
			System.out.println("从客户端传到服务端的数据jsonString----->" + jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;

	}

	public Map<String, Object> getJavaBeanMap(HttpServletRequest request) {
		// map的value的类型为Object，增强对数据库数据的包容性
		Map<String, Object> map = JsonTools2.getMaps(getClientDate(request));
		System.out.println("从客户端传到服务端的数据jsonString转换成map----->"
				+ map.toString());
		return map;
	}

	public void init() throws ServletException {
		service = new JsonService();
	}

}
