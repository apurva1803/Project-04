package in.co.rays.proj4.bean;

public class StockBean extends BaseBean{

	private String stockName;
	private double investmentAmount;
	private String investmentType;
	
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public double getInvestmentAmount() {
		return investmentAmount;
	}

	public void setInvestmentAmount(double investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public String getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}

	@Override
	public String getKey() {
		return stockName;
	}

	@Override
	public String getValue() {
		return stockName;
	}

}
