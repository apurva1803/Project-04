package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.JobQueueBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class JobQueueModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from JobQueue");
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
	 * @throws ApplicationException     on database error
	 * @throws DuplicateRecordException when course name already exists
	 */
	public long add(JobQueueBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		JobQueueBean duplicateCourse = findByName(bean.getJobName());

		if (duplicateCourse != null) {
			throw new DuplicateRecordException("Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into JobQueue values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getJobCode());
			pstmt.setString(3, bean.getJobName());
			pstmt.setString(4, bean.getPriority());
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
				ex.printStackTrace();
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
	 * @throws ApplicationException     on database error
	 * @throws DuplicateRecordException if name duplicates another course
	 */
	public void update(JobQueueBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		JobQueueBean duplicateCourse = findByName(bean.getJobName());
		
		if (duplicateCourse != null && duplicateCourse.getId() != bean.getId()) {
			throw new DuplicateRecordException("Record already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update JobQueue set jobCode = ?, jobName = ?, priority = ?, status = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getJobCode());
			pstmt.setString(2, bean.getJobName());
			pstmt.setString(3, bean.getPriority());
			pstmt.setString(4, bean.getStatus());
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
	public void delete(JobQueueBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from JobQueue where id = ?");
			pstmt.setLong(1, bean.getId());
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
	public JobQueueBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from JobQueue where id = ?");
		JobQueueBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new JobQueueBean();
				bean.setId(rs.getLong(1));
				bean.setJobCode(rs.getString(2));
				bean.setJobName(rs.getString(3));
				bean.setPriority(rs.getString(4));
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
	public JobQueueBean findByName(String name) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from JobQueue where jobName = ?");
		
		JobQueueBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new JobQueueBean();

				bean.setId(rs.getLong(1));
				bean.setJobCode(rs.getString(2));
				bean.setJobName(rs.getString(3));
				bean.setPriority(rs.getString(4));
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
	public List<JobQueueBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Searches for courses based on parameters.
	 *
	 * @param bean     filter
	 * @param pageNo   page number
	 * @param pageSize number of records
	 * @return list of courses
	 * @throws ApplicationException on failure
	 */
	public List<JobQueueBean> search(JobQueueBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from JobQueue where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getJobCode() != null && bean.getJobCode().length() > 0) {
				sql.append(" and jobCode like '" + bean.getJobCode() + "%'");
			}
			if (bean.getJobName() != null && bean.getJobName().length() > 0) {
				sql.append(" and jobName like '" + bean.getJobName() + "%'");
			}
			if (bean.getPriority() != null && bean.getPriority().length() > 0) {
				sql.append(" and priority like '" + bean.getPriority() + "%'");
			}
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<JobQueueBean> list = new ArrayList<JobQueueBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new JobQueueBean();

				bean.setId(rs.getLong(1));
				bean.setJobCode(rs.getString(2));
				bean.setJobName(rs.getString(3));
				bean.setPriority(rs.getString(4));
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
