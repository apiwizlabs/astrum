package io.apiwiz.astrum.api.common;

import org.springframework.data.domain.Page;

public class PaginationRule {
	private Integer totalPages;
	private Integer pageSize;
	private Integer PageNumber;
	private Long totalCount;// total elements
	private Boolean isLast;
	private Boolean isFirst;

	

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return PageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		PageNumber = pageNumber;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Boolean getIsLast() {
		return isLast;
	}

	public void setIsLast(Boolean isLast) {
		this.isLast = isLast;
	}

	public Boolean getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}

	public static <T> PaginationRule createPagination(Page <T> page) {
		 PaginationRule paginationRule=new PaginationRule();
		 paginationRule.setIsFirst(page.isFirst());
		 paginationRule.setIsLast(page.isLast());
		 paginationRule.setPageNumber(page.getNumber());
		 paginationRule.setPageSize(page.getSize());
		 paginationRule.setTotalCount(page.getTotalElements());
		 paginationRule.setTotalPages(page.getTotalPages());
		return paginationRule;
		
	}


}
