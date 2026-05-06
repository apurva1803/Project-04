package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import in.co.rays.proj4.bean.EmiBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EmiModel  {

    // ==================== NEXT PK ====================
    public Integer nextPK() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("select max(id) from st_emi");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    // ==================== ADD ====================
    public long add(EmiBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;
        EmiBean duplicateStatus = findByStatus(bean.getStatus());

		if (duplicateStatus != null) {
			throw new DuplicateRecordException("status Name alredy exists");

		}
        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("insert into st_emi values(?, ?, ?, ?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setDouble(2, bean.getAmount());
            pstmt.setDate(3, new java.sql.Date(bean.getDueDate().getTime()));
            pstmt.setString(4, bean.getStatus());
        	pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add Rollback Exception");
            }
            throw new ApplicationException("Exception in Add EMI");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ==================== DELETE ====================
    public void delete(EmiBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                conn.prepareStatement("delete from st_emi where id=?");

            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete Rollback Exception");
            }
            throw new ApplicationException("Exception in Delete EMI");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== UPDATE ====================
    public void update(EmiBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
    	EmiBean duplicateStatus = findByStatus(bean.getStatus());

		// Check if updated College already exist
		if (duplicateStatus != null && duplicateStatus.getId() != bean.getId()) {

			throw new DuplicateRecordException("status is already exist");
		}

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "update st_emi set amount=?, dueDate=?, status=? ,  created_by=?,modified_by=? , created_datetime=?,modified_datetime=? where id=?");

            pstmt.setDouble(1, bean.getAmount());
            pstmt.setDate(2, new java.sql.Date(bean.getDueDate().getTime()));
            pstmt.setString(3, bean.getStatus());
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
                throw new ApplicationException("Update Rollback Exception");
            }
            throw new ApplicationException("Exception in Update EMI");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== FIND BY PK ====================
    public EmiBean findByPk(long pk) throws ApplicationException {

        EmiBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("select * from st_emi where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new EmiBean();
                bean.setId(rs.getLong(1));
                bean.setAmount(rs.getDouble(2));
                bean.setDueDate(rs.getDate(3));
                bean.setStatus(rs.getString(4));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in Find By PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }
    
    
    public EmiBean findByStatus(String status) throws ApplicationException {

	     

    	EmiBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_emi where status = ?");
            pstmt.setString(1, status);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new EmiBean();
                bean.setId(rs.getLong(1));
                bean.setAmount(rs.getDouble(2));
                bean.setDueDate(rs.getDate(3));
                bean.setStatus(rs.getString(4));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
       
            throw new ApplicationException("Exception : Exception in getting College by name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ==================== SEARCH ====================
    public List search(EmiBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_emi where 1=1");

        if (bean != null) {

            if (bean.getId() > 0)
                sql.append(" and id=" + bean.getId());

            if (bean.getStatus() != null && bean.getStatus().length() > 0)
                sql.append(" and status like '" + bean.getStatus() + "%'");
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        List<EmiBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new EmiBean();
                bean.setId(rs.getLong(1));
                bean.setAmount(rs.getDouble(2));
                bean.setDueDate(rs.getDate(3));
                bean.setStatus(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in Search EMI");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ==================== LIST ====================
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        StringBuffer sql =
            new StringBuffer("select * from st_emi");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;
        EmiBean bean = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new EmiBean();
                bean.setId(rs.getLong(1));
                bean.setAmount(rs.getDouble(2));
                bean.setDueDate(rs.getDate(3));
                bean.setStatus(rs.getString(4));
                list.add(bean);
            }

            rs.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in List EMI");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}