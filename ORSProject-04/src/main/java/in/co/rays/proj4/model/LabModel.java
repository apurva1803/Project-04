package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.LabBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class LabModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_lab");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	// add

	public long add(LabBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;
		
		LabBean existBean =findByName(bean.getName());
		if(existBean!=null) {
			throw new DuplicateRecordException("name already exist");
		}
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_lab values (?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setDouble(3, bean.getCost());
			pstmt.setDate(4, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			int i = pstmt.executeUpdate();
			if (i == 0) {
				throw new ApplicationException("Insert failed");
			}
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void update(LabBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		
		LabBean existBean=findByName(bean.getName());
		if(existBean!=null&&existBean.getId()!=bean.getId()) {
			throw new DuplicateRecordException("already exist");
		}
		
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_lab set name=?,cost=?,date=?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getName());
			pstmt.setDouble(2, bean.getCost());
			pstmt.setDate(3, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	// delete

	public void delete(LabBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_lab where id =?");
			pstmt.setLong(1, bean.getId());

			int i = pstmt.executeUpdate();
			System.out.println(i + "row affected");
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public LabBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		LabBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_lab where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new LabBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCost(rs.getInt(3));
				bean.setDate(rs.getDate(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public LabBean findByName(String name) throws ApplicationException {
		Connection conn = null;
		LabBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_lab where name=?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new LabBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCost(rs.getInt(3));
				bean.setDate(rs.getDate(4));

				pstmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<LabBean> list(int pageNo, int pageSize) {
		Connection conn = null;
		LabBean bean = null;
		ArrayList<LabBean> list = new ArrayList<LabBean>();
		StringBuffer sql = new StringBuffer("select * from st_lab");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit" + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new LabBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCost(rs.getInt(3));
				bean.setDate(rs.getDate(4));
				list.add(bean);
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	public List search(LabBean bean,int pageNo,int pageSize) throws ApplicationException {
		Connection conn=null;
		
		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer("select * from st_lab where 1=1");
		
		if(bean!=null) {
			if(bean.getId()>0) {
				sql.append(" and id = " +bean.getId());
			}
			
			if(bean.getName()!=null && bean.getName().length()>0) {
				sql.append(" and name like '" +bean.getName() +"%'");
			}
			if(bean.getCost()>0) {
				sql.append(" and cost = " +bean.getCost());
			}
			
			if(bean.getDate()!=null && bean.getDate().getTime()>0) {
				sql.append(" and date like '"+new java.sql.Date(bean.getDate().getTime()) +"%'");
			}
			
		}
		
		if(pageSize>0) {
			pageNo=(pageNo-1)*pageSize;
			sql.append(" limit "+pageNo +"," +pageSize);
		}
		try {
		conn=JDBCDataSource.getConnection();
		PreparedStatement pstmt=conn.prepareStatement(sql.toString());
		ResultSet rs=pstmt.executeQuery();
		
		while (rs.next()) {
			bean = new LabBean();

			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setCost(rs.getInt(3));
			bean.setDate(rs.getDate(4));
			list.add(bean);
		}
		pstmt.close();
	} 
	catch (Exception e) {
		e.printStackTrace();
	}

	finally {
		JDBCDataSource.closeConnection(conn);
	}
	return list;
}
	}
