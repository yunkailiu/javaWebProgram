package com.mingrisoft;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class News {
	// 建立数据库连接
	DBConnection DBConn = new DBConnection();
	// 引入Function的功能函数
	Function Fun = new Function();

	public News() {

	}

	// 新闻列表的查询方法
	public String ListNewsFront(String sPage, String strPage) {
		try {

			Connection Conn = DBConn.getConn();// 建立连接
			Statement stmt = Conn.createStatement();// 创建数据查询
			ResultSet rs = null;// 定义返回结果集
			StringBuffer sb = new StringBuffer();

			int i;
			int intPage = 1;
			int intPageSize = 5;
			// 定义sql语句，并执行该sql语句，返回到结果集中
			String sSql = "select * from News order by NewsID desc";
			rs = stmt.executeQuery(sSql);
			// 判断结果集中是否有数据条目
			if (!rs.next()) {
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"5\">\r\n");
				sb.append("<div align=\"center\"><b>没有记录!</b></div></td></tr>\r\n");
			} else {
				intPage = Fun.StrToInt(strPage);
				sPage = Fun.CheckReplace(sPage);
				// 设置起始页
				if (intPage == 0)
					intPage = 1;
				/*
				 * ResultSet中的absolute函数是对返回结果集进行每行编号，如第一行为行1，第二行为行2
				 * absolute(1)表示第一行
				 */
				// 这里表示每到新的一页要返回数据的第几行
				rs.absolute((intPage - 1) * intPageSize + 1);
				i = 0;
				while (i < intPageSize && !rs.isAfterLast()) {
					int NewsID = rs.getInt("NewsID");
					String NewsTitle = rs.getString("NewsTitle");
					String NewsTime = rs.getString("NewsTime");
					String AdminName = rs.getString("AdminName");

					sb.append("<tr>");
					sb.append("<td>" + NewsTitle + "</td>");
					sb.append("<td >" + AdminName + "</td>");
					sb.append("<td >" + NewsTime + "</td>");
					sb.append("<td ><a style=\"color:#3F862E\" target=\"_blank\" href=\"newsFrontDetail.jsp?newsId="
							+ NewsID + "\">详情</a></td></tr>");

					rs.next();
					i++;
				}
				sb.append(Fun.PageFront(sPage, rs, intPage, intPageSize));
			}
			rs.close();
			stmt.close();
			Conn.close();
			return sb.toString();
		} catch (Exception e) {
			return "No";
		}
	}

	public String ListNews(String sPage, String strPage) {
		try {

			Connection Conn = DBConn.getConn();
			Statement stmt = Conn.createStatement();
			ResultSet rs = null;
			StringBuffer sb = new StringBuffer();

			int i;
			int intPage = 1;
			int intPageSize = 5;

			String sSql = "select * from News order by NewsID desc";
			rs = stmt.executeQuery(sSql);

			if (!rs.next()) {
				sb.append("<tr height=\"25\" bgcolor=\"#d6dff7\"  class=\"info1\"><td colspan=\"4\">\r\n");
				sb.append("<div align=\"center\"><b>没有记录!</b></div></td></tr>\r\n");
			} else {

				intPage = Fun.StrToInt(strPage);
				sPage = Fun.CheckReplace(sPage);
				if (intPage == 0)
					intPage = 1;

				rs.absolute((intPage - 1) * intPageSize + 1);
				i = 0;
				while (i < intPageSize && !rs.isAfterLast()) {
					int NewsID = rs.getInt("NewsID");
					String NewsTitle = rs.getString("NewsTitle");
					String NewsContent = rs.getString("NewsContent");
					String NewsTime = rs.getString("NewsTime");
					String AdminName = rs.getString("AdminName");

					sb.append("<tr>");
					sb.append("<td class=\"table-id\">" + NewsID + "</td>");

					sb.append("<td>" + NewsTitle + "</td>");
					sb.append("<td class=\"table-title\">" + AdminName + "</td>");
					sb.append("<td class=\"table-title\">" + NewsTime + "</td>");
					sb.append("<td><div class=\"am-btn-toolbar\">");
					sb.append("<div class=\"am-btn-group am-btn-group-xs\">");
					sb.append("<input type=\"hidden\" value=\"" + NewsID + "\">");
					sb.append("<input type=\"hidden\" value=\"" + NewsContent + "\">");
					sb.append("<input type=\"hidden\" value=\"" + NewsTitle + "\">");
					sb.append("<a onclick=\"edit(this);\"");
					sb.append("class=\"am-btn am-btn-primary am-btn-xs \"");
					sb.append("href=\"javascript:void(0);\"> <span></span> 修改 <a> ");
					sb.append("<a rel=\"" + NewsID
							+ "\" onclick=\"del(this);\" class=\"am-btn am-btn-danger am-btn-xs \" href=\"javascript:void(0);\"> "
							+ "<span></span>删除<a>");
					sb.append("</div></div></td></tr>");

					rs.next();
					i++;
				}
				sb.append(Fun.Page(sPage, rs, intPage, intPageSize));
			}
			rs.close();
			stmt.close();
			Conn.close();
			return sb.toString();
		} catch (Exception e) {
			return "No";
		}
	}

	// 向数据库中添加新闻
	public String AddNews(String[] s, String s1, String s2) {
		try {
			Connection Conn = DBConn.getConn();
			Statement stmt = Conn.createStatement();

			ResultSet rs = null;
			String sSql = "select * from News order by NewsID desc";
			rs = stmt.executeQuery(sSql);
			int z = 0;
			int newNum = 0;
			// 设置NewsID
			if (!rs.next()) {
				newNum = 1;
			} else {
				while (z < 1 && !rs.isAfterLast()) {
					int NewsID = rs.getInt("NewsID");
					newNum = NewsID + 1;
					break;
				}
			}
			// 设置NewsTitle和NewsContent
			for (int i = 0; i < s.length; i++) {
				if (i != 1)
					s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
				else
					s[i] = Fun.getStrCN(s[i]);
			}
			// 设置NewsTime
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String newsTime = format1.format(new Date());
			// 定义sql插入语句
			StringBuffer sql = new StringBuffer();
			sql.append("insert into News (NewsID,NewsTitle,NewsContent,NewsTime,AdminName) values (" + " '" + newNum
					+ "'," + " '" + s[0] + "'," + " '" + s[1] + "'," + " '" + newsTime + "'," + " '" + s1 + "')");

			System.out.println(sql);

			try {
				// 执行插入操作
				Conn.setAutoCommit(false);
				stmt.execute(sql.toString());
				Conn.commit();
				Conn.setAutoCommit(true);
				stmt.close();
				Conn.close();

				return "Yes";
			} catch (Exception e) {
				Conn.rollback();
				e.printStackTrace();
				Conn.close();
				return "添加成功!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "添加失败";
		}
	}
	//编辑新闻，sql的更新操作
	public String EditNews(String[] s, String s0, String s1, String s2) {
		try {

			Connection Conn = DBConn.getConn();
			Statement stmt = Conn.createStatement();

			for (int i = 0; i < s.length; i++) {
				s[i] = Fun.getStrCN(Fun.CheckReplace(s[i]));
			}

			int NewsID = Fun.StrToInt(s0);

			StringBuffer sql = new StringBuffer();
			sql.append("update News set NewsTitle='" + s[0] + "'" + " ,NewsContent='" + s[1] + "'" + " where NewsID='"
					+ NewsID + "'");

			stmt.executeUpdate(sql.toString());
			stmt.close();
			Conn.close();

			return "Yes";

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return "编辑错误!";
		}
	}
	//删除新闻，过程同添加，但比添加简单
	public boolean DelNews(String s0, String s1, String s2) {

		try {
			Connection Conn = DBConn.getConn();
			Statement stmt = Conn.createStatement();
			int NewsID = Fun.StrToInt(s0);
			if (NewsID == 0)
				return false;
			else {
				try {
					String sql = "delete from News where NewsID=" + NewsID;

					Conn.setAutoCommit(false);
					stmt.executeUpdate(sql);

					Conn.commit();
					Conn.setAutoCommit(true);

					stmt.close();
					Conn.close();
					return true;
				} catch (Exception e) {
					Conn.rollback();
					// e.printStackTrace();
					Conn.close();
					return false;
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.print(e.getMessage());

			return false;
		}

	}

	// 新闻内容查询
	public String FrontNewsDetail(String s0) {
		try {
			Connection Conn = DBConn.getConn();
			Statement stmt = Conn.createStatement();
			ResultSet rs = null;
			// 通过新闻的ID进行内容查询
			int NewsID = Fun.StrToInt(s0);
			if (NewsID == 0)
				return "No";
			else {
				try {
					String sql = "select * from News where NewsID=" + NewsID;
					rs = stmt.executeQuery(sql);

					StringBuffer sb = new StringBuffer();

					int i = 0;
					while (i < 1 && !rs.isAfterLast()) {
						rs.next();
						String NewsTitle = rs.getString("NewsTitle");
						String NewsContent = rs.getString("NewsContent");

						String[] content = NewsContent.split("#");

						sb.append("<br><h2 style=\"font-size:28px;margin-left:30%\">" + NewsTitle + "</h2>");

						for (int j = 0; j < content.length; j++) {
							sb.append("<p>" + content[j] + "</p>");
						}
						rs.next();
						i++;
					}

					rs.close();
					stmt.close();
					Conn.close();
					return sb.toString();
				} catch (Exception e) {
					Conn.rollback(); // JDBC回滚
					// e.printStackTrace();
					Conn.close();
					return "No";
				}
			}
		} catch (Exception e) {

			return "No";
		}

	}

}
