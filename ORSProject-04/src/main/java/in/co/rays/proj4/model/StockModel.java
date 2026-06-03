package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.LabBean;
import in.co.rays.proj4.bean.StockBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class StockModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_stock");

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

	public long add(StockBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;
		
		StockBean existBean =findByName(bean.getStockName());
		if(existBean!=null) {
			throw new DuplicateRecordException("name already exist");
		}
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_stock values (?,?,?,?,?,?,?,?)");
			
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getStockName());
			pstmt.setDouble(3, bean.getInvestmentAmount());
			pstmt.setString(4, bean.getInvestmentType());
			
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

	public void update(StockBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		
		StockBean existBean=findByName(bean.getStockName());
		
		if(existBean!=null && existBean.getId()!=bean.getId()) {
			throw new DuplicateRecordException("already exist");
		}
		
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_stock set stockName=?, investmentAmount=?, investmentType=?, createdBy = ?, modifiedBy = ?, createdDatetime = ?, modifiedDatetime = ? where id = ?");
			
			pstmt.setString(1, bean.getStockName());
			pstmt.setDouble(2, bean.getInvestmentAmount());
			pstmt.setString(3, bean.getInvestmentType());
			
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

	public void delete(StockBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_stock where id =?");
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

	public StockBean findByPk(long pk) throws ApplicationException {
		
		Connection conn = null;
		StockBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_stock where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StockBean();
				bean.setId(rs.getLong(1));
				
				bean.setStockName(rs.getString(2));
				bean.setInvestmentAmount(rs.getDouble(3));
				bean.setInvestmentType(rs.getString(4));
				
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

	public StockBean findByName(String name) throws ApplicationException {
		
		Connection conn = null;
		StockBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_stock where stockName=?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StockBean();
				bean.setId(rs.getLong(1));
				
				bean.setStockName(rs.getString(2));
				bean.setInvestmentAmount(rs.getDouble(3));
				bean.setInvestmentType(rs.getString(4));

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

	public List<StockBean> list(int pageNo, int pageSize) {
		
		Connection conn = null;
		StockBean bean = null;
		
		ArrayList<StockBean> list = new ArrayList<StockBean>();
		StringBuffer sql = new StringBuffer("select * from st_stock");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit" + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StockBean();

				bean.setId(rs.getLong(1));
				bean.setStockName(rs.getString(2));
				bean.setInvestmentAmount(rs.getDouble(3));
				bean.setInvestmentType(rs.getString(4));
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

	public List search(StockBean bean,int pageNo,int pageSize) throws ApplicationException {
		Connection conn=null;
		
		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer("select * from st_stock where 1=1");
		
		if(bean!=null) {
			if(bean.getId()>0) {
				sql.append(" and id = " +bean.getId());
			}
			
			if(bean.getStockName()!=null && bean.getStockName().length()>0) {
				sql.append(" and stockName like '" +bean.getStockName() +"%'");
			}
			if(bean.getInvestmentAmount()>0) {
				sql.append(" and investmentAmount = " +bean.getInvestmentAmount());
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
			bean = new StockBean();

			bean.setId(rs.getLong(1));
			
			bean.setStockName(rs.getString(2));
			bean.setInvestmentAmount(rs.getDouble(3));
			bean.setInvestmentType(rs.getString(4));
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
