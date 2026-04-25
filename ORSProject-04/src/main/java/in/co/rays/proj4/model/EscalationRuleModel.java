package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.EscalationRuleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EscalationRuleModel {

public Integer nextPk() throws DatabaseException {
		
		
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(ruleId) from EscalationRule");
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

	 /**
     * Adds a new course.
     *
     * @param bean CourseBean containing course data
     * @return generated primary key
     * @throws ApplicationException on database error
     * @throws DuplicateRecordException when course name already exists
     */
	public long add(EscalationRuleBean bean) throws ApplicationException, DuplicateRecordException {
		
		
		Connection conn = null;
		int pk = 0;

		EscalationRuleBean duplicateCourse = findByName(bean.getAssignedTo());

		if (duplicateCourse != null) {
			throw new DuplicateRecordException("Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into EscalationRule values(?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getRuleCode());
			pstmt.setString(3, bean.getLevel());
			pstmt.setString(4, bean.getAssignedTo());
			pstmt.setString(5, bean.getStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	 /**
     * Updates an existing course.
     *
     * @param bean CourseBean containing updated data
     * @throws ApplicationException on database error
     * @throws DuplicateRecordException if name duplicates another course
     */
	public void update(EscalationRuleBean bean) throws ApplicationException, DuplicateRecordException {
		
		 
		Connection conn = null;

		EscalationRuleBean duplicateCourse = findByName(bean.getAssignedTo());
		if (duplicateCourse != null && duplicateCourse.getId() != bean.getId()) {
			throw new DuplicateRecordException("Course already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update EscalationRule set ruleCode = ?, level = ?, assignedTo = ?, status=?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where ruleId = ?");
			
			pstmt.setString(1, bean.getRuleCode());
			pstmt.setString(2, bean.getLevel());
			pstmt.setString(3, bean.getAssignedTo());
			pstmt.setString(4, bean.getStatus());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getRuleId());
			
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
     * Deletes a course.
     *
     * @param bean CourseBean containing id
     * @throws ApplicationException if deletion fails
     */
	public void delete(EscalationRuleBean bean) throws ApplicationException {
		

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from EscalationRule where ruleId = ?");
			pstmt.setLong(1, bean.getRuleId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
     * Finds course by primary key.
     *
     * @param pk primary key
     * @return CourseBean
     * @throws ApplicationException when lookup fails
     */
	public EscalationRuleBean findByPk(long pk) throws ApplicationException {
		
		
		StringBuffer sql = new StringBuffer("select * from EscalationRule where id = ?");
		EscalationRuleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new EscalationRuleBean();
				bean.setRuleId(rs.getLong(1));
				bean.setRuleCode(rs.getString(2));
				bean.setLevel(rs.getString(3));
				bean.setAssignedTo(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
     * Finds course by name.
     *
     * @param name course name
     * @return CourseBean or null
     * @throws ApplicationException on error
     */
	public EscalationRuleBean findByName(String name) throws ApplicationException {
		
		
		StringBuffer sql = new StringBuffer("select * from EscalationRule where name = ?");
		EscalationRuleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new EscalationRuleBean();
				
				bean.setRuleId(rs.getLong(1));
				bean.setRuleCode(rs.getString(2));
				bean.setLevel(rs.getString(3));
				bean.setAssignedTo(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
     * Returns all courses.
     *
     * @return list of courses
     * @throws ApplicationException on database failure
     */
	public List<EscalationRuleBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
     * Searches for courses based on parameters.
     *
     * @param bean filter
     * @param pageNo page number
     * @param pageSize number of records
     * @return list of courses
     * @throws ApplicationException on failure
     */
	public List<EscalationRuleBean> search(EscalationRuleBean bean, int pageNo, int pageSize) throws ApplicationException {
		
		
		StringBuffer sql = new StringBuffer("select * from EscalationRule where 1=1");

		if (bean != null) {
			if (bean.getRuleId() > 0) {
				sql.append(" and ruleId = " + bean.getRuleId());
			}
			if (bean.getRuleCode() != null && bean.getRuleCode().length() > 0) {
				sql.append(" and ruleCode like '" + bean.getRuleCode() + "%'");
			}
			if (bean.getLevel() != null && bean.getLevel().length() > 0) {
				sql.append(" and level like '" + bean.getLevel() + "%'");
			}
			if (bean.getAssignedTo() != null && bean.getAssignedTo().length() > 0) {
				sql.append(" and assignedTo like '" + bean.getAssignedTo() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<EscalationRuleBean> list = new ArrayList<EscalationRuleBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new EscalationRuleBean();
				
				bean.setRuleId(rs.getLong(1));
				bean.setRuleCode(rs.getString(2));
				bean.setLevel(rs.getString(3));
				bean.setAssignedTo(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
