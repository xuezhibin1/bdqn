package cn.easybuy.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻分页工具
 */
public class Pager implements Serializable{
	
	// 当前页
	private int currentPage;
	
	// 总条数
	private int rowCount;
	
	// 每页显示条数
	private int rowPerPage;
	
	// 总页数
	private int pageCount;
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public Pager(int rowCount, int rowPerPage, int currentPage) {

		this.rowCount = rowCount;
		this.rowPerPage = rowPerPage;
		this.currentPage = currentPage;
		if(this.rowCount % this.rowPerPage == 0){
			this.pageCount = this.rowCount / this.rowPerPage;
		}else if(this.rowCount % this.rowPerPage > 0){
			this.pageCount = this.rowCount / this.rowPerPage + 1;
		}else{
			this.pageCount = 0;
		}
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}
}
