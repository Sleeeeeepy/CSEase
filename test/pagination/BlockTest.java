package pagination;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dto.Pagination;
import junit.framework.Assert;

class BlockTest {

	@Test
	void BlockTest_7thPage() {
		Pagination page = new Pagination(7, 1000);
		assertEquals(6, page.getPageStartsAt());
		assertEquals(10, page.getPageEndsAt());
	}

	@Test
	void BlockTest_12thPage() {
		Pagination page = new Pagination(12, 1000);
		assertEquals(11, page.getPageStartsAt());
		assertEquals(15, page.getPageEndsAt());
	}

	@Test
	void BlockTest_21stPage() {
		Pagination page = new Pagination(21, 1000);
		assertEquals(21, page.getPageStartsAt());
		assertEquals(25, page.getPageEndsAt());
	}
	
	@Test
	void BlockTest_Limit100() {
		Pagination page = new Pagination(7, 100);
		assertEquals(6, page.getPageStartsAt());
		assertEquals(10, page.getPageEndsAt());
	}
	
	@Test
	void BlockTest_PageOver() {
		Pagination page = new Pagination(11, 100);
		assertEquals(6, page.getPageStartsAt());
		assertEquals(10, page.getPageEndsAt());
		assertEquals(10, page.getCurrent());
	}
	
	@Test
	void BlockTest_BlockSize6() {
		Pagination page = new Pagination(11, 10, 6, 1000);
		assertEquals(7, page.getPageStartsAt());
		assertEquals(12, page.getPageEndsAt());
	}
	
	@Test
	void BlockTest_BlockSize6_limit100() {
		Pagination page = new Pagination(11, 10, 6, 100);
		assertEquals(7, page.getPageStartsAt());
		assertEquals(10, page.getPageEndsAt());
	}
	
	@Test
	void BlockTest_PageMove() {
		Pagination page = new Pagination(11, 1000);
		page.setCurrent(21);
		assertEquals(21, page.getPageStartsAt());
		assertEquals(25, page.getPageEndsAt());
	}
	
	@Test
	void BlockTest_MinusPage() {
		Pagination page = new Pagination(-1, 10, 5, 1000);
		assertEquals(1, page.getPageStartsAt());
		assertEquals(5, page.getPageEndsAt());
	}
	
	@Test
	void BlockTest_PageOver_Block() {
		Pagination page = new Pagination(200, 10, 5, 100);
		assertEquals(6, page.getPageStartsAt());
		assertEquals(10, page.getPageEndsAt());
	}
}
