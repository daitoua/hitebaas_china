package com.hitebaas.data.trade;





/**
 * 合约列表（每个客户端保存一个列表）
 *
 */
public class Contract {

	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String contractName;
	
	private String tokenAddress;
	
	private String createAddress;
	
	private String unit;
	
	private String tokBalance;
	
	private String tokenBalance;
	
	private String content;//合约内容
	private String icoLimitTime;//ico活动限制时间
	private String enlargeLimitTime;//代币放大活动限制时间
	private String ep;//tok兑换比例 1个tok= ep个该代币,不需要分期返利时使用
	private String ep2;//代币兑换比例 1个代币= ep2个该代币-不设置区间,不需要分期返利时使用
	private String initializeAmount;//初始化金额

	/**
	 * 控制开关。长度2，
	 * eg. "ab"，
	 * a : tok转合约地址，是否返利
	 * b : 代币转合约地址，是否返利
	 * 比如： "0001": tok转合约地址马上到账不需要返利。 代币转合约地址不会马上到账需要返利
	 */
	private String control;//控制开关
	/**
	 * tok
	 * 1、周期放大倍数
	 * 2、每期几天、共几期
	 * [0-500][15,30];[501-2000][10,20];
	 */
	private String intervalPeriod;
	/**
	 * 合约ico
	 */
	private String intervalPeriod2;
	private String createTime;
	

	/**
	 * 区块号
	 */
	private String blockIndex;
	/**
	 * 确认数
	 */
	private String confirmNumber;
	/**
	 * failure
	 * normal
	 * complete
	 */
	private String tradeStatus;
	
	
	private String kouchuBalance;//消耗的总量
	
	private String contractImg;
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getTokenAddress() {
		return tokenAddress;
	}

	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}

	public String getCreateAddress() {
		return createAddress;
	}

	public void setCreateAddress(String createAddress) {
		this.createAddress = createAddress;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIcoLimitTime() {
		return icoLimitTime;
	}

	public void setIcoLimitTime(String icoLimitTime) {
		this.icoLimitTime = icoLimitTime;
	}

	public String getEp() {
		return ep;
	}

	public void setEp(String ep) {
		this.ep = ep;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getEp2() {
		return ep2;
	}

	public void setEp2(String ep2) {
		this.ep2 = ep2;
	}

	public String getIntervalPeriod() {
		return intervalPeriod;
	}

	public void setIntervalPeriod(String intervalPeriod) {
		this.intervalPeriod = intervalPeriod;
	}

	public String getIntervalPeriod2() {
		return intervalPeriod2;
	}

	public void setIntervalPeriod2(String intervalPeriod2) {
		this.intervalPeriod2 = intervalPeriod2;
	}

	public String getInitializeAmount() {
		return initializeAmount;
	}

	public void setInitializeAmount(String initializeAmount) {
		this.initializeAmount = initializeAmount;
	}

	public String getBlockIndex() {
		return blockIndex;
	}

	public void setBlockIndex(String blockIndex) {
		this.blockIndex = blockIndex;
	}

	public String getConfirmNumber() {
		return confirmNumber;
	}

	public void setConfirmNumber(String confirmNumber) {
		this.confirmNumber = confirmNumber;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getEnlargeLimitTime() {
		return enlargeLimitTime;
	}

	public void setEnlargeLimitTime(String enlargeLimitTime) {
		this.enlargeLimitTime = enlargeLimitTime;
	}

	public String getTokBalance() {
		return tokBalance;
	}

	public void setTokBalance(String tokBalance) {
		this.tokBalance = tokBalance;
	}

	public String getTokenBalance() {
		return tokenBalance;
	}

	public void setTokenBalance(String tokenBalance) {
		this.tokenBalance = tokenBalance;
	}

	public String getKouchuBalance() {
		return kouchuBalance;
	}

	public void setKouchuBalance(String kouchuBalance) {
		this.kouchuBalance = kouchuBalance;
	}

	public String getContractImg() {
		return contractImg;
	}

	public void setContractImg(String contractImg) {
		this.contractImg = contractImg;
	}
	
	
	
}
