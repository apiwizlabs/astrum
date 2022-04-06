package io.apiwiz.astrum.api.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PaginationRuleTest {
	public Integer totalPages;
	public Integer pageSize;
	public Integer PageNumber;
	public Long totalCount;// total elements
	public Boolean isLast;
	public Boolean isFirst;
	PaginationRule paginationRule=new PaginationRule();


	@Test
	void testGetTotalPages() {
		paginationRule.getTotalPages();
	}

	@Test
	void testSetTotalPages() {
		paginationRule.setTotalPages(totalPages);
	}

	@Test
	void testGetPageSize() {
		paginationRule.getPageSize();
	}

	@Test
	void testSetPageSize() {
		paginationRule.setPageSize(pageSize);
	}

	@Test
	void testGetPageNumber() {
		paginationRule.getPageNumber();
	}

	@Test
	void testSetPageNumber() {
		paginationRule.setPageNumber(PageNumber);
	}

	@Test
	void testGetTotalCount() {
		paginationRule.getTotalCount();
	}

	@Test
	void testSetTotalCount() {
		paginationRule.setTotalCount(totalCount);
	}

	@Test
	void testGetIsLast() {
		paginationRule.getIsLast();
	}

	@Test
	void testSetIsLast() {
		paginationRule.setIsLast(isLast);
	}

	@Test
	void testGetIsFirst() {
		paginationRule.getIsFirst();
	}

	@Test
	void testSetIsFirst() {
		paginationRule.setIsFirst(isFirst);
	}

	@Test
	void testCreatePagination() {
		fail("Not yet implemented");
	}

}
