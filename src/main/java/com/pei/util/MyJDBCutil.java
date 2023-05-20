package com.pei.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

 
public class MyJDBCutil{
	// 使用druid连接池完成连接的获取
	// 将属性书写为静态属性 通过静态代码块进行赋值
	private static DataSource ds;

	// 在静态代码块中读取配置文件创建线程池对象
	static {
		try {
			Properties p = new Properties();
			InputStream is = MyJDBCutil.class.getClassLoader().getResourceAsStream("druid.properties");
			p.load(is);

			ds = DruidDataSourceFactory.createDataSource(p);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 获取连接方法
	public static Connection getCon() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 关闭方法
	// 对于连接池中的连接对象 调用close时不是关闭而是归还
	// 需要书写多个关闭释放资源方法
	private static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 关闭更新语句连接资源
	public static void close(Statement statement, Connection con) {
		close(statement);
		close(con);
	}

	// 关闭查询语句连接资源
	public static void close(ResultSet rs, Statement statement, Connection con) {
		close(rs);
		close(statement);
		close(con);
	}

	// dml更新方法
	// 使用不确定参数为占位符进行赋值 (未解决事务)
	public static int dml(String sql, Object... values) {
		Connection con = getCon();
		PreparedStatement statement = null;
		int count = 0;
		try {
			statement = con.prepareStatement(sql);
			for (int i = 0; i < values.length; i++) {
				statement.setObject(i + 1, values[i]);
			}
			count = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(statement, con);
		}
		return count;
	}

	// dml更新方法
	// 使用不确定参数为占位符进行赋值 (解决事务)
	public static int dml(Connection con, String sql, Object... values) throws SQLException {
		// 开启事务
		PreparedStatement statement = null;
		int count = 0;
		con.setAutoCommit(false);
		statement = con.prepareStatement(sql);
		for (int i = 0; i < values.length; i++) {
			statement.setObject(i + 1, values[i]);
		}
		count = statement.executeUpdate();

		return count;
	}

	// dql语句执行功能
	public static <E> ArrayList<E> dql(String sql, Class<E> c, Object... values) {
		ArrayList<E> list = new ArrayList<>();
		Connection con = getCon();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = con.prepareStatement(sql);
			// 获取原数据对象 将列数以及对应的列名获取
			ResultSetMetaData metaData = statement.getMetaData();
			int columnCount = metaData.getColumnCount();
			// 根据列数创建对应长度的数组 并进行列名的存储
			String[] names = new String[columnCount];
			for (int i = 0; i < names.length; i++) {
				names[i] = metaData.getColumnName(i + 1);
			}
			// System.out.println(Arrays.toString(names));
			for (int i = 0; i < values.length; i++) {
				statement.setObject(i + 1, values[i]);
			}
			rs = statement.executeQuery();
			while (rs.next()) {
				// 通过反射创建指定对象
				E e = c.newInstance();

				// 遍历列名数组 通过反射获取对应字段对象
				for (String filedName : names) {
					// 获取对应字段对象
					Field field = c.getDeclaredField(filedName);
					// 获取对应列当前行的值
					Object value = rs.getObject(filedName);
					field.setAccessible(true);
					field.set(e, value);
				}
				list.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, statement, con);
		}
		return list;

	}

	// 自动生成当前连接数据库所有bean对象保存至指定包下
	public static void autoGenerateAllBeanS(String beanUrl) {
		try {
			Connection con = getCon();
			String sql = "show tables";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				autoGenerateFormatBean(rs.getString(1), beanUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 自动生成当前连接数据库所有bean对象dao接口 daoimpl保存至指定包下
	public static void autoGenerateAllBeanS(String beanUrl, String daoUrl, String daoImplUrl) {
		try {
			Connection con = getCon();
			String sql = "show tables";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				autoGenerateBeanAndDao(rs.getString(1), beanUrl, daoUrl, daoImplUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void autoGenerateFormatBean(String tables, String beanUrl) {
		String sql = "select * from " + tables;

		try {
			File directory = new File("");// 参数为空
			String courseFile = directory.getCanonicalPath();// 获取当前项目路径
			String replace = "src/" + beanUrl.replace(".", "/");
			String beanNane = tables.toUpperCase().charAt(0) + tables.substring(1);// 当前类名
			File file = new File(courseFile, replace + "/" + beanNane + ".java");// 当前类的文件对象
			file.createNewFile();
			Connection con = getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSetMetaData metaData = ps.getMetaData();
			// 创建map集合 key为列名 value为对应的java类型
			HashMap<String, String> names = new HashMap<>();
			// 创建list集合存储所有的属性（顺序）
			ArrayList<String> nameList = new ArrayList<>();

			for (int i = 0; i < metaData.getColumnCount(); i++) {
				String columnLabel = metaData.getColumnLabel(i + 1);// 获取指定列名
				String columnTypeName = metaData.getColumnTypeName(i + 1);// 获取列类型
				names.put(columnLabel, dbTOjava(columnTypeName));
				nameList.add(columnLabel);
			}

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			// 书写包
			bw.write("package " + beanUrl + ";");
			bw.newLine();
			bw.newLine();
			// 书写导入包
			bw.write("import java.util.*;");
			bw.write("import java.sql.*;");
			bw.newLine();
			bw.newLine();
			// 书写类名
			bw.write("public class " + beanNane + " {");
			bw.newLine();
			// 生成全部属性
			for (String key : nameList) {
				String value = names.get(key);
				bw.write("	private " + value + " " + key + ";");
				bw.newLine();
			}
			bw.newLine();
			// 生成无参构造方法
			bw.write("	public " + beanNane + "() {");
			bw.newLine();
			bw.write("		super();");
			bw.newLine();
			bw.write("	}");
			bw.newLine();
			bw.newLine();
			// 生成全参构造方法
			bw.write("	public " + beanNane + "(");
			for (int i = 0; i < nameList.size(); i++) {
				String key = nameList.get(i);
				if (i != nameList.size() - 1) {
					bw.write(names.get(key) + " " + key + ",");
				} else {
					bw.write(names.get(key) + " " + key);
				}
			}
			bw.write(") {");
			bw.newLine();
			bw.write("		super();");
			bw.newLine();

			for (String key : nameList) {
				bw.write("		this." + key + " = " + key + ";");
				bw.newLine();
			}
			bw.write("	}");
			bw.newLine();
			bw.newLine();
			// 生成全部属性对应getset方法
			for (String key : nameList) {
				String value = names.get(key);
				bw.write("	public " + value + " get" + key.toUpperCase().charAt(0) + key.substring(1) + "() {");
				bw.newLine();
				bw.write("		return this." + key + ";");
				bw.newLine();
				bw.write("	}");
				bw.newLine();
				bw.newLine();
				bw.write("	public void set" + key.toUpperCase().charAt(0) + key.substring(1) + "(" + value + " " + key
						+ ") {");
				bw.newLine();
				bw.write("		this." + key + " = " + key + ";");
				bw.newLine();
				bw.write("	}");
				bw.newLine();
				bw.newLine();
			}

			for (int i = 0; i < nameList.size(); i++) {
				String string = nameList.get(i);

				nameList.set(i, string + "= \"+ " + string + " + \"");
			}
			// 生成toString方法
			bw.write("	@Override");
			bw.newLine();
			bw.write("	public String toString() {");
			bw.newLine();
			bw.write("		return \"" + beanNane + " ");
			bw.write(nameList.toString());
			bw.write("\";");
			bw.newLine();
			bw.write("	}");
			bw.newLine();
			bw.write("}");
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void autoGenerateBeanAndDao(String tableName, String beanUrl, String daoUrl, String daoImplUrl) {
		String sql = "select * from " + tableName;
		try {
			File directory = new File("");// 参数为空
			String courseFile = directory.getCanonicalPath();// 获取当前项目路径
			String replace = "src/" + beanUrl.replace(".", "/");
			String daoUrls = courseFile + "/src/" + daoUrl.replace(".", "/");
			String beanNane = tableName.toUpperCase().charAt(0) + tableName.substring(1);// 当前类名
			String daoName = beanNane + "Dao";
			String daoImplName = beanNane + "DaoImpl";// dao实现类名

			// 生成bean
			File file = new File(courseFile, replace + "/" + beanNane + ".java");// 当前类的文件对象
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			// 生成dao接口
			File daoFile = new File(daoUrls, daoName + ".java");// dao接口文件对象
			FileWriter daoFilefw = new FileWriter(daoFile);
			BufferedWriter daoFilebw = new BufferedWriter(daoFilefw);
			// 生成daoiml实现类
			String impldaoUrls = courseFile + "/src/" + daoImplUrl.replace(".", "/");
			File daoImpFile = new File(impldaoUrls, daoImplName + ".java");// dao实现类文件对象
			FileWriter daoImpFilefw = new FileWriter(daoImpFile);
			BufferedWriter daoImpFilebw = new BufferedWriter(daoImpFilefw);

			Connection con = getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSetMetaData metaData = ps.getMetaData();
			// 创建map集合 key为列名 value为对应的java类型
			HashMap<String, String> names = new HashMap<>();
			// 创建list集合存储所有的属性（顺序）
			ArrayList<String> nameList = new ArrayList<>();
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				String columnLabel = metaData.getColumnLabel(i + 1);// 获取指定列名
				String columnTypeName = metaData.getColumnTypeName(i + 1);// 获取列类型
				names.put(columnLabel, dbTOjava(columnTypeName));
				nameList.add(columnLabel);
			}
			// bean内容书写
			// 书写包
			bw.write("package " + beanUrl + ";");
			bw.newLine();
			bw.newLine();
			// 书写导入包
			bw.write("import java.sql.*;");
			bw.newLine();
			bw.newLine();
			// 书写类名
			bw.write("public class " + beanNane + " {");
			bw.newLine();
			// 生成全部属性
			for (String key : nameList) {
				String value = names.get(key);
				bw.write("	private " + value + " " + key + ";");
				bw.newLine();
			}
			bw.newLine();
			// 生成无参构造方法
			bw.write("	public " + beanNane + "() {");
			bw.newLine();
			bw.write("		super();");
			bw.newLine();
			bw.write("	}");
			bw.newLine();
			bw.newLine();
			// 生成全参构造方法
			bw.write("	public " + beanNane + "(");
			for (int i = 0; i < nameList.size(); i++) {
				String key = nameList.get(i);
				if (i != nameList.size() - 1) {
					bw.write(names.get(key) + " " + key + ",");
				} else {
					bw.write(names.get(key) + " " + key);
				}
			}
			bw.write(") {");
			bw.newLine();
			bw.write("		super();");
			bw.newLine();

			for (String key : nameList) {
				bw.write("		this." + key + " = " + key + ";");
				bw.newLine();
			}
			bw.write("	}");
			bw.newLine();
			bw.newLine();
			// 生成全部属性对应getset方法
			for (String key : nameList) {
				String value = names.get(key);
				bw.write("	public " + value + " get" + key.toUpperCase().charAt(0) + key.substring(1) + "() {");
				bw.newLine();
				bw.write("		return this." + key + ";");
				bw.newLine();
				bw.write("	}");
				bw.newLine();
				bw.newLine();
				bw.write("	public void set" + key.toUpperCase().charAt(0) + key.substring(1) + "(" + value + " " + key
						+ ") {");
				bw.newLine();
				bw.write("		this." + key + " = " + key + ";");
				bw.newLine();
				bw.write("	}");
				bw.newLine();
				bw.newLine();
			}

			ArrayList<String> nl = new ArrayList<>();
			for (int i = 0; i < nameList.size(); i++) {
				String string = nameList.get(i);
				nl.add(i, string + "= \"+ " + string + " + \"");
			}
			// 生成toString方法
			bw.write("	@Override");
			bw.newLine();
			bw.write("	public String toString() {");
			bw.newLine();
			bw.write("		return \"" + beanNane + " ");
			bw.write(nl.toString());
			bw.write("\";");
			bw.newLine();
			bw.write("	}");
			bw.newLine();
			bw.write("}");
			bw.flush();
			bw.close();

			// dao内容书写
			daoFilebw.write("package " + daoUrl + ";");
			daoFilebw.newLine();
			daoFilebw.newLine();
			daoFilebw.write("import java.util.*;");
			daoFilebw.newLine();
			daoFilebw.write("import " + beanUrl + ".*;");
			daoFilebw.newLine();
			daoFilebw.newLine();
			daoFilebw.write("public interface " + daoName + " {");
			daoFilebw.newLine();
			daoFilebw.write("	//查询所有数据方法");
			daoFilebw.newLine();
			daoFilebw.write("	public ArrayList<" + beanNane + "> selectAll();");
			daoFilebw.newLine();
			daoFilebw.newLine();
			// 生成根据每个字段单独查询方法
			for (String filename : nameList) {
				daoFilebw.write("	//根据" + filename + "查询数据方法");
				daoFilebw.newLine();
				daoFilebw.write("	public ArrayList<" + beanNane + "> selectBy" + filename.toUpperCase().charAt(0)
						+ filename.substring(1) + "(" + names.get(filename) + " " + filename + ");");
				daoFilebw.newLine();
				daoFilebw.newLine();
			}
			// 生成根据任意字段查询方法
			for (int i = 0; i < nameList.size() - 1; i++) {
				String filename1 = nameList.get(i);
				for (int j = i + 1; j < nameList.size(); j++) {
					String filename2 = nameList.get(j);
					daoFilebw.write("	//根据" + filename1 + "与" + filename2 + "查询数据方法");
					daoFilebw.newLine();
					daoFilebw.write("	public ArrayList<" + beanNane + "> selectBy" + filename1.toUpperCase().charAt(0)
							+ filename1.substring(1) + "And" + filename2.toUpperCase().charAt(0)
							+ filename2.substring(1) + "(" + names.get(filename1) + " " + filename1 + ","
							+ names.get(filename2) + " " + filename2 + ");");
					daoFilebw.newLine();
					daoFilebw.newLine();
				}
			}
			// 生成根据每列修改其他列数据方法
			for (String filename : nameList) {
				daoFilebw.write("	//根据" + filename + "修改数据方法");
				daoFilebw.newLine();
				daoFilebw.write("	public int updateBy" + filename.toUpperCase().charAt(0) + filename.substring(1)
						+ "(" + beanNane + " bean);");
				daoFilebw.newLine();
				daoFilebw.newLine();
			}

			daoFilebw.newLine();
			daoFilebw.write("}");
			daoFilebw.newLine();
			daoFilebw.flush();
			daoFilebw.close();

			// 通过反射获取接口方法
			Method[] methods = Class.forName(daoUrl + "." + daoName).getDeclaredMethods();
			// for (Method method : methods) {
			// System.out.println(method.getName());//获取方法名
			// System.out.println(method.getParameterCount());//获取方法参数个数
			// }

			// 生成daoimpl实现类
			daoImpFilebw.write("package " + daoImplUrl + ";");
			daoImpFilebw.newLine();
			daoImpFilebw.newLine();
			daoImpFilebw.write("import java.util.*;");
			daoImpFilebw.newLine();
			daoImpFilebw.write("import " + beanUrl + ".*;");
			daoImpFilebw.newLine();
			daoImpFilebw.write("import " + daoUrl + ".*;");
			daoImpFilebw.newLine();
			String name = MyJDBCutil.class.getPackage().getName();
			daoImpFilebw.write("import " + name + ".MyJDBCutil;");
			daoImpFilebw.newLine();
			daoImpFilebw.write("public class " + daoImplName + " implements " + daoName + "{");
			daoImpFilebw.newLine();
			daoImpFilebw.write("	String selectSql=\"select * from " + beanNane + "\";");
			String updateString = "";
			for (String s : nameList) {
				updateString += ",`" + s + "`=?";
			}
			daoImpFilebw.newLine();
			daoImpFilebw
					.write("	String updateSql=\"update " + beanNane + " set " + updateString.substring(1) + "\";");
			// System.out.println(updateString);
			daoImpFilebw.newLine();
			daoImpFilebw.newLine();

			for (Method method : methods) {
				String methodName = method.getName();
				int methodFileCount = method.getParameterCount();
				// 所有方法的重写
				daoImpFilebw.write("	@Override");
				daoImpFilebw.newLine();
				// 查询所有方法
				if (methodName.contains("All") && methodFileCount == 0) {
					daoImpFilebw.write("	public ArrayList<" + beanNane + "> selectAll() {");
					daoImpFilebw.newLine();
					daoImpFilebw.write("		return MyJDBCutil.dql(selectSql, " + beanNane + ".class);");
					daoImpFilebw.newLine();
					daoImpFilebw.write("	}");
					daoImpFilebw.newLine();
					// 根据单个字段查询方法
				} else if (methodName.contains("select") && methodFileCount == 1) {
					String filename = methodName.split("By")[1].toLowerCase();
					daoImpFilebw.write("	public ArrayList<" + beanNane + "> " + methodName + "("
							+ names.get(filename) + " " + filename + ") {");
					daoImpFilebw.newLine();
					daoImpFilebw.write("		String sql=selectSql+\" where " + filename + "=?\";");
					daoImpFilebw.newLine();
					daoImpFilebw
							.write("		return MyJDBCutil.dql(sql," + beanNane + ".class," + filename + ");");
					daoImpFilebw.newLine();
					daoImpFilebw.write("	}");
					daoImpFilebw.newLine();
				} else if (methodName.contains("select") && methodFileCount == 2) {
					String[] split = methodName.replace("selectBy", "").split("And");
					String one = split[0].toLowerCase();
					String two = split[1].toLowerCase();
					daoImpFilebw.write("	public ArrayList<" + beanNane + "> " + methodName + "(" + names.get(one)
							+ " " + one + "," + names.get(two) + " " + two + ") {");
					daoImpFilebw.newLine();
					daoImpFilebw.write("		String sql=selectSql+\" where `" + one + "`=?" + " and `" + two + "`=?\";");
					daoImpFilebw.newLine();
					daoImpFilebw.write(
							"		return MyJDBCutil.dql(sql," + beanNane + ".class," + one + "," + two + ");");
					daoImpFilebw.newLine();
					daoImpFilebw.newLine();
					daoImpFilebw.write("	}");
					daoImpFilebw.newLine();
				} else if (methodName.contains("update") && methodFileCount == 1) {
					String filedname = methodName.split("By")[1];
					String string = "get" + filedname;
					daoImpFilebw.write("	public int " + methodName + "(" + beanNane + " bean) {");
					daoImpFilebw.newLine();

					daoImpFilebw.write("		String sql=selectSql+\" where " + filedname.toLowerCase() + "=?\";");
					daoImpFilebw.newLine();
					daoImpFilebw.write("		return MyJDBCutil.dml(sql,bean." + string + "());");
					daoImpFilebw.newLine();
					daoImpFilebw.newLine();
					daoImpFilebw.write("	}");
					daoImpFilebw.newLine();
				}
			}
			daoImpFilebw.newLine();
			daoImpFilebw.write("}");
			daoImpFilebw.flush();
			daoImpFilebw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 转换方法 将数据库列对象转换为指定java类型字符串
	private static String dbTOjava(String type) {
		if (type.equals("INT")) {
			return "int";
		} else if (type.equals("VARCHAR") || type.equals("CHAR")) {
			return "String";
		} else if (type.equals("DOUBLE")) {
			return "double";
		} else if (type.equals("FLOAT")) {
			return "float";
		} else if (type.equals("TIMESTAMP") || type.equals("DATETIME") || type.equals("DATE") || type.equals("TIME")) {
			return "Date";
		} else {
			return "Object";
		}
	}

	public static void toXlS(String tableName, String FileUril) {
		Connection con = getCon();
		File file = new File(FileUril);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String sql = "select * from " + tableName;
			ps = con.prepareStatement(sql);
			ResultSetMetaData metaData = ps.getMetaData();
			int columnCount = metaData.getColumnCount();
			rs = ps.executeQuery();

			HSSFWorkbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet(tableName);
			int startRow = 0;
			Row row1 = sheet.createRow(startRow);
			for (int i = 0; i < columnCount; i++) {
				row1.createCell(i).setCellValue(metaData.getColumnName(i + 1));
			}

			while (rs.next()) {
				startRow++;
				Row row = sheet.createRow(startRow);
				for (int i = 0; i < columnCount; i++) {
					row.createCell(i).setCellValue(rs.getString(i + 1));
				}
			}
			workbook.write(file);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, con);
		}

	}

	public static void DBtoXlS(String FileUrl) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		Connection con = getCon();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "show tables";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				int startRow = 0;
				String tableName = rs.getString(1);
				HSSFSheet sheet = workbook.createSheet(tableName);
				String tableSql = "select * from " + tableName;
				PreparedStatement ps1 = con.prepareStatement(tableSql);
				ResultSetMetaData metaData = ps1.getMetaData();
				int columnCount = metaData.getColumnCount();
				HSSFRow firstRow = sheet.createRow(startRow);
				for (int i = 0; i < columnCount; i++) {
					// 分别为第一行每个单元格设置列名
					firstRow.createCell(i).setCellValue(metaData.getColumnName(i + 1));
				}
				// 执行sql获取对应表中数据
				ResultSet tableValue = ps1.executeQuery();
				while (tableValue.next()) {
					startRow++;
					HSSFRow valueRow = sheet.createRow(startRow);
					for (int i = 0; i < columnCount; i++) {
						// 分别为每个单元格设置列名
						valueRow.createCell(i).setCellValue(tableValue.getString(i + 1));
					}
				}

			}
			workbook.write(new File(FileUrl));
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, con);
		}

	}

}
