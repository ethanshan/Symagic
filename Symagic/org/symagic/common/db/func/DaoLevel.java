package org.symagic.common.db.func;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.symagic.common.db.bean.BeanLevel;
import org.symagic.common.db.pool.ConnectionPool;

/**
 * 对score_level操作的封装类
 * @author wanran
 *
 */
public class DaoLevel {
	private Connection conn;
	private PreparedStatement ps;
	private Statement st;
	private ResultSet rs;
	private int count = 0;
	
	/**
	 * 添加等级标准
	 * @param level 封装等级标准BeanLevel对象的实例
	 * @return	true 添加成功	false 添加失败
	 */
	public boolean add(BeanLevel level)
	{
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("insert into score_level (name, lowlimit, uplimit, rate) values" +
					"(?, ?, ?, ?)");
			ps.setString(1, level.getName());
			ps.setInt(2, level.getLowLimit());
			ps.setInt(3, level.getUpLimit());
			ps.setInt(4, level.getRate());
			ps.execute();
			if (ps.getUpdateCount() == 1) 
				return true;
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 更新等级标准
	 * @param level	封装等级标准BeanLevel对象的实例
	 * @return	true 添加成功	false 添加失败
	 */
	public boolean update(BeanLevel level)
	{
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("update score_level set " +
					"name=?, lowlimit=?, uplimit=?, rate=?");
			ps.setString(1, level.getName());
			ps.setInt(2, level.getLowLimit());
			ps.setInt(3, level.getUpLimit());
			ps.setInt(4, level.getRate());
			if (ps.executeUpdate() == 1) 
				return true;
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
}







