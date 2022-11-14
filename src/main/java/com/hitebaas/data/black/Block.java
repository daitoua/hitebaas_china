package com.hitebaas.data.black;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class Block implements Serializable{
	private int id;
	private String blockIndex;
	private String headHash;
	private String randomNumber;
	private String path;//block file relative path
	private String createTime;
	private String endHash;
	private int counter;//Be confirmed block num
	private int onMingChain;//1 : on the chain ,0 : on other branch
	private String merkleRoot;
	private Map<String,List<String>> fileMD5;
	private Map<String,List<String>> fileNo;
	private int count;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBlockIndex() {
		return blockIndex;
	}
	public void setBlockIndex(String blockIndex) {
		this.blockIndex = blockIndex;
	}
	public String getHeadHash() {
		return headHash;
	}
	public void setHeadHash(String headHash) {
		this.headHash = headHash;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getEndHash() {
		return endHash;
	}
	public void setEndHash(String endHash) {
		this.endHash = endHash;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public String getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}
	public int getOnMingChain() {
		return onMingChain;
	}
	public void setOnMingChain(int onMingChain) {
		this.onMingChain = onMingChain;
	}
	
	public String genString() {
		return "Block [blockIndex=" + blockIndex + ", headHash=" + headHash + ", randomNumber="
				+ randomNumber + ", path=" + path + ", createTime=" + createTime + ", endHash=" + endHash + ",merkleRoot="+merkleRoot+"]";
	}
	
	@Override
	public String toString() {
		return "Block [id=" + id + ", blockIndex=" + blockIndex + ", headHash=" + headHash + ", randomNumber="
				+ randomNumber + ", path=" + path + ", createTime=" + createTime + ", endHash=" + endHash + ", counter="
				+ counter + ",merkleRoot="+merkleRoot+"]";
	}
	public String getMerkleRoot() {
		return merkleRoot;
	}
	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}
	public Map<String, List<String>> getFileMD5() {
		return fileMD5;
	}
	public void setFileMD5(Map<String, List<String>> fileMD5) {
		this.fileMD5 = fileMD5;
	}
	public Map<String, List<String>> getFileNo() {
		return fileNo;
	}
	public void setFileNo(Map<String, List<String>> fileNo) {
		this.fileNo = fileNo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	


	
	
	
	
}
