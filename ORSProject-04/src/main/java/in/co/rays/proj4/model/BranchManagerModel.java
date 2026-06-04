package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BranchManagerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class BranchManagerModel {
	
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_branchManager");

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

	

	public long add(BranchManagerBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;
		
		BranchManagerBean existBean =findByName(bean.getBranchName());
		if(existBean!=null) {
			throw new DuplicateRecordException("name already exist");
		}
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_branchManager values (?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getManagerName());
			pstmt.setString(3, bean.getBranchName());
			pstmt.setString(4, bean.getContactNumber());
			
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

	public void update(BranchManagerBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		
		BranchManagerBean existBean = findByName(bean.getBranchName());
		
		if(existBean!=null && existBean.getId()!=bean.getId()) {
			throw new DuplicateRecordException("already exist");
		}
		
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_branchManager set managerName=?, branchName=?, contactNumber=?, createdBy = ?, modifiedBy = ?, createdDatetime = ?, modifiedDatetime = ? where id = ?");
			
			pstmt.setString(1, bean.getManagerName());
			pstmt.setString(2, bean.getBranchName());
			pstmt.setString(3, bean.getContactNumber());
			
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
				throw new ApplicationException("Exception : update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in update User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	

	public void delete(BranchManagerBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_branchManager where id =?");
			pstmt.setLong(1, bean.getId());

			int i = pstmt.executeUpdate();
			System.out.println(i + "row affected");
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public BranchManagerBean findByPk(long pk) throws ApplicationException {
		
		Connection conn = null;
		BranchManagerBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_branchManager where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
				bean = new BranchManagerBean();
				bean.setId(rs.getLong(1));
				bean.setManagerName(rs.getString(2));
				bean.setBranchName(rs.getString(3));
				bean.setContactNumber(rs.getString(4));
				
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
				throw new ApplicationException("Exception : rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in FindByUser");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public BranchManagerBean findByName(String name) throws ApplicationException {
		
		Connection conn = null;
		BranchManagerBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_branchManager where branchName=?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
				bean = new BranchManagerBean();
				bean.setId(rs.getLong(1));
				bean.setManagerName(rs.getString(2));
				bean.setBranchName(rs.getString(3));
				bean.setContactNumber(rs.getString(4));

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

	public List<BranchManagerBean> list(int pageNo, int pageSize) {
		Connection conn = null;
		BranchManagerBean bean = null;
		ArrayList<BranchManagerBean> list = new ArrayList<BranchManagerBean>();
		StringBuffer sql = new StringBuffer("select * from st_branchManager");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit" + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new BranchManagerBean();

				bean.setId(rs.getLong(1));
				bean.setManagerName(rs.getString(2));
				bean.setBranchName(rs.getString(3));
				bean.setContactNumber(rs.getString(4));
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

	public List search(BranchManagerBean bean,int pageNo,int pageSize) throws ApplicationException {
		Connection conn=null;
		
		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer("select * from st_branchManager where 1=1");
		
		if(bean!=null) {
			if(bean.getId()>0) {
				sql.append(" and id = " +bean.getId());
			}
			
			if(bean.getManagerName()!=null && bean.getManagerName().length()>0) {
				sql.append(" and managerName like '" +bean.getManagerName() +"%'");
			}
			
			if(bean.getBranchName()!=null && bean.getBranchName().length()>0) {
				sql.append(" and branchName like '" +bean.getBranchName() +"%'");
			}
			
			if(bean.getContactNumber()!=null && bean.getContactNumber().length()>0) {
				sql.append(" and contactNumber like '" +bean.getContactNumber() +"%'");
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
			bean = new BranchManagerBean();

			bean.setId(rs.getLong(1));
			bean.setManagerName(rs.getString(2));
			bean.setBranchName(rs.getString(3));
			bean.setContactNumber(rs.getString(4));
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
