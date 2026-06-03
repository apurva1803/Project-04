package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.JobportalBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class JobportalModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_jobportal");

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

	public long add(JobportalBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;
		
		JobportalBean existBean =findByName(bean.getCompanyName());
		if(existBean!=null) {
			throw new DuplicateRecordException("name already exist");
		}
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_jobportal values (?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			
			pstmt.setString(2, bean.getCompanyName());
			pstmt.setString(3, bean.getJobRole());
			pstmt.setDouble(4, bean.getSalaryPackage());
			pstmt.setInt(5, bean.getExperienceRequired());
			
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
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

	public void update(JobportalBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		
		JobportalBean existBean=findByName(bean.getCompanyName());
		
		if(existBean!=null&&existBean.getId()!=bean.getId()) {
			throw new DuplicateRecordException("already exist");
		}
		
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_jobportal set companyName=?,jobRole=?,salaryPackage=?, experienceRequired=?,createdBy = ?, modifiedBy = ?, createdDatetime = ?, modifiedDatetime = ? where id = ?");
			pstmt.setString(1, bean.getCompanyName());
			pstmt.setString(2, bean.getJobRole());
			pstmt.setDouble(3, bean.getSalaryPackage());
			pstmt.setInt(4, bean.getExperienceRequired());
			
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in Update User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	// delete

	public void delete(JobportalBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_jobportal where id =?");
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

	public JobportalBean findByPk(long pk) throws ApplicationException {
		
		Connection conn = null;
		JobportalBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_jobportal where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new JobportalBean();
				bean.setId(rs.getLong(1));
				
				bean.setCompanyName(rs.getString(2));
				bean.setJobRole(rs.getString(3));
				bean.setSalaryPackage(rs.getDouble(4));
				bean.setExperienceRequired(rs.getInt(5));
				
				
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
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

	public JobportalBean findByName(String name) throws ApplicationException {
		Connection conn = null;
		JobportalBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_jobportal where companyName=?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new JobportalBean();
				bean.setId(rs.getLong(1));
				
				bean.setCompanyName(rs.getString(2));
				bean.setJobRole(rs.getString(3));
				bean.setSalaryPackage(rs.getDouble(4));
				bean.setExperienceRequired(rs.getInt(5));

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

	public List<JobportalBean> list(int pageNo, int pageSize) {
		
		Connection conn = null;
		JobportalBean bean = null;
		ArrayList<JobportalBean> list = new ArrayList<JobportalBean>();
		StringBuffer sql = new StringBuffer("select * from st_jobportal");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit" + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new JobportalBean();

				bean.setId(rs.getLong(1));
				
				bean.setCompanyName(rs.getString(2));
				bean.setJobRole(rs.getString(3));
				bean.setSalaryPackage(rs.getDouble(4));
				bean.setExperienceRequired(rs.getInt(5));
				
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

	public List search(JobportalBean bean,int pageNo,int pageSize) throws ApplicationException {
		Connection conn=null;
		
		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer("select * from st_jobportal where 1=1");
		
		if(bean!=null) {
			if(bean.getId()>0) {
				sql.append(" and id = " +bean.getId());
			}
			
			if(bean.getCompanyName()!=null && bean.getCompanyName().length()>0) {
				sql.append(" and companyName like '" +bean.getCompanyName() +"%'");
			}
			if(bean.getJobRole()!=null && bean.getJobRole().length()>0) {
				sql.append(" and jobRole like '" +bean.getJobRole()+"%'");
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
			bean = new JobportalBean();

			bean.setId(rs.getLong(1));
			bean.setCompanyName(rs.getString(2));
			bean.setJobRole(rs.getString(3));
			bean.setSalaryPackage(rs.getDouble(4));
			bean.setExperienceRequired(rs.getInt(5));
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
