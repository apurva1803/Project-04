package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.GymworkoutBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class GymworkoutModel {
//nextPk***************************************
	public Integer nextPK() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_gymworkout");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception: Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	// add******************************************

	public long add(GymworkoutBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		GymworkoutBean name = findByName(bean.getName());

		if (name != null) {
			throw new DuplicateRecordException("name exists");

		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_gymworkout values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getWorkout());
			pstmt.setString(4, bean.getTrainerName());
			pstmt.setString(5, bean.getSchedule());

			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// delete************************************************************************
	public void delete(GymworkoutBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_gymworkout where id = ?");

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

			throw new ApplicationException("Exception : Exception in delete College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// update****************************************************
	public void update(GymworkoutBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		GymworkoutBean name = findByName(bean.getName());

		if (name != null && name.getId() != bean.getId()) {

			throw new DuplicateRecordException("name already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_gymworkout set name = ?, workout = ?, trainerName = ?, schedule = ?, createdBy = ?, modifiedBy = ?, createdDatetime = ?, modifiedDatetime = ? where id = ?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getWorkout());
			pstmt.setString(3, bean.getTrainerName());
			pstmt.setString(4, bean.getSchedule());

			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

			pstmt.setLong(9, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();

				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating GymWorkout");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// name********************************************

	public GymworkoutBean findByName(String name) throws ApplicationException {

		GymworkoutBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_gymworkout where name = ?");
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new GymworkoutBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setWorkout(rs.getString(3));
				bean.setTrainerName(rs.getString(4));
				bean.setSchedule(rs.getString(5));

				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting R name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// PK***************************************
	public GymworkoutBean findByPk(long pk) throws ApplicationException {

		GymworkoutBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_gymworkout where id = ?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new GymworkoutBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setWorkout(rs.getString(3));
				bean.setTrainerName(rs.getString(4));
				bean.setSchedule(rs.getString(5));

				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting Gym by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// search by filter
	public List search() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<GymworkoutBean> search(GymworkoutBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_gymworkout where 1=1");

		if (bean != null) {

			if (bean.getId() > 0)
				sql.append(" and id = " + bean.getId());

			if (bean.getName() != null && bean.getName().length() > 0)
				sql.append(" and name like '" + bean.getName() + "%'");

			if (bean.getWorkout() != null && bean.getWorkout().length() > 0)
				sql.append(" and workout like '" + bean.getWorkout() + "%'");

			if (bean.getTrainerName() != null && bean.getTrainerName().length() > 0)
				sql.append(" and trainer_name like '" + bean.getTrainerName() + "%'");

			if (bean.getSchedule() != null && bean.getSchedule().length() > 0)
				sql.append(" and schedule like '" + bean.getSchedule() + "%'");
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		List<GymworkoutBean> list = new ArrayList<>();
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				GymworkoutBean newBean = new GymworkoutBean();

				newBean.setId(rs.getLong(1));
				newBean.setName(rs.getString(2));
				newBean.setWorkout(rs.getString(3));
				newBean.setTrainerName(rs.getString(4));
				newBean.setSchedule(rs.getString(5));

				newBean.setCreatedBy(rs.getString(6));
				newBean.setModifiedBy(rs.getString(7));
				newBean.setCreatedDatetime(rs.getTimestamp(8));
				newBean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(newBean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search Gymworkout");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// all data
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_gymworkout");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		GymworkoutBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new GymworkoutBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setWorkout(rs.getString(3));
				bean.setTrainerName(rs.getString(4));
				bean.setSchedule(rs.getString(5));

				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}
}