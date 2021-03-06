package org.symagic.common.db.func;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.symagic.common.db.bean.BeanOrder;
import org.symagic.common.db.bean.BeanUser;
import org.symagic.common.db.pool.ConnectionPool;

/**
 * 用于User相关数据库操作的功能封装
 * @author wanran
 *
 */
public class DaoUser {
	private Connection	conn;
	private PreparedStatement	ps;
	private Statement	st;
	private ResultSet	rs;
	
	/**
	 * 验证用户名与密码是否对应
	 * @param username	用户名
	 * @param password	密码（未加密状态）
	 * @return	true:用户名、密码对应	false:用户名密码不对应
	 */
	public boolean validateUser(String username, String password)
	{
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			
			ps	= conn.prepareStatement("select userid, username from user where username=?");	// 获取给定用户名在对应的记录信息
			ps.setString(1, username);
			rs	= ps.executeQuery();
			if (!rs.next()) {
				conn.close();
				return false;	// 如果没有给定用户名的记录，返回false
			}
			
			int id	= rs.getInt("userid");
			
			ps	= conn.prepareStatement("select password from secret where userid=?");	// 获取给定用户名对应password
			ps.setInt(1, id);
			rs	= ps.executeQuery();
			conn.close();	// 连接使用完关闭
			rs.next();
	
			
			 if (rs.getString("password").equals(Util.getMD5(password.getBytes())))
				return true;		// 如果给定的密码和从数据库中得到的密码相同，返回true
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 验证给定用户名是否可用
	 * @param username	要验证的用户名
	 * @return true: 数据库中不存在此用户名	false: 数据库中存在此用户名
	 */
	public boolean validateUserName(String username)
	{
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("select count(*) from user where username=?");	// 获取给定用户名在User中的个数
			ps.setString(1, username);
			String s = ps.toString();
			rs	= ps.executeQuery();
			conn.close();	// 连接用完关闭（必要）
			
			while(rs.next()) {
				// 如果表中此名字个数不为0，则返回false，否则返回true
				if (rs.getInt(1) != 0)
					return false;
				else 
					return true;
			}
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
	 * 添加用户，涉及两张表user和secret,先要插入user后获得userid,
	 * 再将password、userid插入secret表
	 * @param user	BeanUser对象，封装者所有有关用户的信息
	 * @return	true:	插入成功	
	 * 			false:	插入失败
	 */
	public boolean addUser(BeanUser user) 
	{
		int count	= 0;
		if (!this.validateUserName(user.getUsername()))
			return false;
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("insert into user (" +
					"username, nickname, score, question, answer, registedate)" +
					"values (" +
					"?, ?, ?, ?, ?, date(now())" +
					")");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getNickname());
			ps.setInt(3, user.getScore());
			ps.setString(4, user.getQuestion());
			ps.setString(5, Util.getMD5(user.getAnswer().getBytes()));
			
			// 执行用户插入
			ps.execute();
			count = ps.getUpdateCount();	// 获取插入条数
			// 如果插入0条，表示插入失败，返回false
			if (count == 0)
				return false;


			
			// 如果用户基础信息插入成功，则查询插入记录的id,将用户密码插入secret表
			ps	= conn.prepareStatement("select userid from user where username=?");	// 获取给定用户ID
			ps.setString(1, user.getUsername());
			rs	= ps.executeQuery();
			if (!rs.next()) {
				conn.close();
				return false;
			}
			ps	= conn.prepareStatement("insert into secret (" +
					"userid, password)" +
					"values (" +
					"?, ?)");
			ps.setInt(1, rs.getInt("userid"));
			ps.setString(2, Util.getMD5(user.getPassword().getBytes()));
			ps.execute();
			
			if (ps.getUpdateCount() == 0) {
				// 在未使用事务的情况下，这一步需要将上一步插入到User表中的数据删除
				conn.close();
				return false;
			}
			conn.close();
			return true;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 更新用户昵称
	 * @param username	用户名，用于唯一标示用户
	 * @param nickname	要更改的用户昵称
	 * @return	true	更新成功		false	更新失败
	 */
	public boolean updateNickname(String username, String nickname)
	{
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("update user set nickname=? " +
					"where username=?");
			ps.setString(1, nickname);
			ps.setString(2, username);
			
			// 更新成功，既返回更新记录条数等于1
			if (ps.executeUpdate() == 1) {
				
				conn.close();
				return true;	// 更新成功，返回true
			}
			conn.close();
			return false;	// 更新失败，返回false
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 获得用户积分
	 * @param username	用户名
	 * @return	int 用户积分
	 */
	public int getScore(String username)
	{
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("select score from user " +
					" where username=?");
			ps.setString(1, username);
			rs	= ps.executeQuery();
			// 如果有下一条有效记录，及查询成功
			if (rs.next()) {
				conn.close();
				return rs.getInt(1);
			}
			conn.close();
			return -1;
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
		return 0;
	}
	
	/**
	 * 更改用户密码
	 * @param username	用户名
	 * @param password	用户新密码
	 * @param oldPassword	用户旧密码
	 * @return
	 */
	public boolean updatePassword(String username, String password, String oldPassword)
	{
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("select user.username, secret.password, user.userid " +
					"from user, secret " +
					"where user.userid=secret.userid and user.username=?");
			ps.setString(1, username);
			rs	= ps.executeQuery();
			// 如果查询成功
			if (rs.next()) {
				// 如过oldpassword与指定用户数据库预存密码相等，则可进行更新
				if (rs.getString("password").equals(Util.getMD5(oldPassword.getBytes()))) {
					ps	= conn.prepareStatement("update secret set password=? where userid=?");
					ps.setString(1, Util.getMD5(password.getBytes()));
					ps.setInt(2, rs.getInt("userid"));
					if (ps.executeUpdate() == 1) {
						conn.close();
						return true;
					}
					
				}
			}
			// 查询不成功,或更新失败
			conn.close();
			return false;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 获取注册用户数
	 * @return	int 注册用户数目
	 */
	public int getUserNum()
	{
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("select count(*) from user");
			rs	= ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
			return -1;
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
		return -1;
	}
	
	/**
	 * 获取指定用户的所有信息
	 * @param username	指定用户名
	 * @return	BeanUser封装者用户信息的Bean实例
	 */
	public BeanUser getUser(String username)
	{
		BeanUser user	= null;
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("select * from user where username=?");
			ps.setString(1, username);
			rs	= ps.executeQuery();
			if (rs.next()) {
				user	= new BeanUser();
				user.setAnswer(rs.getString("answer"));
				user.setNickname(rs.getString("nickname"));
				user.setQuestion(rs.getString("question"));
				user.setScore(rs.getInt("score"));
				user.setUsername(rs.getString("username"));
			}
			return user;
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
		return null;
	}
	
	/**
	 * 按照指定条件搜索用户
	 * @param req	UserRequire实例，封装着用户搜索条件
	 * @return	List<BeanUser>null 搜索出错	not null 搜索成功
	 */
	public List<BeanUser> search(UserRequire req)
	{
		List<BeanUser> list	= null;
		String sql	= "select * from user where ";
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			
			if (req.getUserLevel() != null) {
				ps	= conn.prepareStatement("select lowlimit,uplimit from score_level where id=?");
				ps.setInt(1, req.getUserLevel());
				rs	= ps.executeQuery();
			}
			
			
			sql += " username like " + "'%" + req.getUsername() + "%'";
			if (req.getStartTime() != null)
				sql += "and" + " date(registedate) >= " + "'" + req.getStartTime() + "'";
			if (req.getEndTime() != null)
				sql += " and " + " date(registedate) <= " + "'" + req.getEndTime() + "'";
			if (req.getUserLevel() != null){
				rs.next();
				sql += " and " + " score >= " + "'" + rs.getInt("lowlimit") + "'";
				sql += " and " + " score < " + "'" + rs.getInt("uplimit") + "'";
			}
			
			
			sql += " order by userid asc limit " 
				+ (req.getPage()-1)*req.getLines()
				+ ", " + req.getLines();
			
			st	= conn.createStatement();
			rs	= st.executeQuery(sql);
			
			list	= new ArrayList<BeanUser>();
			
			while (rs.next()) {
				BeanUser user	= new BeanUser();
				user.setAnswer(rs.getString("answer"));
				user.setNickname(rs.getString("nickname"));
				user.setQuestion(rs.getString("question"));
				user.setScore(rs.getInt("score"));
				user.setUsername(rs.getString("username"));
				user.setRegistedate(rs.getString("registedate"));
				list.add(user);
			}
			
			return list;
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
		return list;
	}
	
	/**
	 * 获取搜索获取的总数目
	 * @param req	所搜条件
	 * @return	int -1 所搜出错	>=0 搜索成功
	 */
	public int getSearchNum(UserRequire req)
	{
		int count	= 0;
		String sql	= "select * from user where ";
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			if (req.getUserLevel() != null) {
				ps	= conn.prepareStatement("select lowlimit,uplimit from score_level where id=?");
				ps.setInt(1, req.getUserLevel());
				rs	= ps.executeQuery();
			}

			sql += " username like " + "'%" + req.getUsername() + "%'";
			if (req.getStartTime() != null)
				sql += "and" + " registedate > " + "'" + req.getStartTime() + "'";
			if (req.getEndTime() != null)
				sql += " and " + " registedate < " + "'" + req.getEndTime() + "'";
			if (req.getUserLevel() != null) {
				rs.next();
				sql += " and " + " score >= " + "'" + rs.getInt("lowlimit") + "'";
				sql += " and " + " score < " + "'" + rs.getInt("uplimit") + "'";
			}
				
			
			
			st	= conn.createStatement();
			rs	= st.executeQuery(sql);
			
			count	= 0;
			while (rs.next()) {
				count++;
			}
			return count;
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
		return -1;
	}
	
	/**
	 * 更新用户积分
	 * @return	true 更新成功	false 更新失败
	 */
	public boolean updateScore(int score, String username)
	{
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("update user set score=? where username=?");
			ps.setInt(1, score);
			ps.setString(2, username);
			ps.execute();
			if (ps.getUpdateCount() == 1)
				return true;
			return false;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 更新用户问题与答案
	 * @param username	指定用户名
	 * @param question	指定用户问题	
	 * @param answer
	 * @return
	 */
	public boolean updateQA(String username, String question, String answer)
	{
		try {
			conn	= ConnectionPool.getInstance().getConnection();
			ps	= conn.prepareStatement("update user set question=?, answer=? " +
					"where username=?");
			ps.setString(1, question);
			ps.setString(2, Util.getMD5(answer.getBytes()));
			ps.setString(3, username);
			ps.execute();
			
			if (ps.getUpdateCount() == 1)
				return true;
			return false;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}
