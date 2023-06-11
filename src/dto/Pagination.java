package dto;

public class Pagination {
	private int current;
	private int rowPerPage;
	private int totalPage;
	private int totalRow;
	private int blockSize;
	private int pageStartsAt;
	private int pageEndsAt;
	private int offset;
	private boolean hasPrev;
	private boolean hasNext;
	private boolean hasNextBlock;
	private boolean hasPrevBlock;

	public Pagination(int current) {
		this(current, 10, 5, 1000);
	}
	public Pagination(int current, int totalRow) {
		this(current, 10, 5, totalRow);
	}
//15320
	public Pagination(int current, int rowPerPage, int blockSize, int totalRow) {
		this.current = current;
		this.rowPerPage = rowPerPage;
		this.totalRow = totalRow;
		this.blockSize = blockSize;
		init();
	}
	
	public int getOffset() {
		if (current <= 0) {
			current = 1;
		}
		return (current - 1) * rowPerPage;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
		init();
	}

	public int getRowPerPage() {
		init();
		return rowPerPage;
	}

	public int getTotalPage() {
		init();
		return totalPage;
	}
	
	public int getTotalRow() {
		init();
		return totalRow;
	}
	
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		init();
	}

	public int getBlockSize() {
		return blockSize;
	}

	public boolean isHasPrev() {
		return hasPrev;
	}

	public boolean isHasNext() {
		return hasNext;
	}
	
	public boolean isHasNextBlock() {
		return hasNextBlock;
	}

	public boolean isHasPrevBlock() {
		return hasPrevBlock;
	}

	public int getPageStartsAt() {
		return pageStartsAt;
	}

	public int getPageEndsAt() {
		return pageEndsAt;
	}

	private void init() {
		// validation
		if (rowPerPage <= 0) {
			rowPerPage = 1;
		}

		if (current < 1) {
			current = 1;
		}

		if (blockSize < 2) {
			blockSize = 5;
		}

		// page row calculation
		totalPage = totalRow / rowPerPage;
		if ((totalRow % rowPerPage) != 0) {
			totalPage++;
		}

		if (current > totalPage) {
			current = totalPage;
		}
		
		// has prev or next
		if (current < 1) {
			hasPrev = true;
		} else {
			hasPrev = false;
		}

		if (current < totalPage) {
			hasNext = true;
		} else {
			hasNext = false;
		}

		// block calculation
		int totalBlock = totalPage / blockSize;
		if ((totalPage % blockSize) != 0) {
			totalBlock++;
		}

		int currentBlock = current / blockSize;
		if (current != 0 && current % blockSize == 0) {
			currentBlock--;
		}
		
		if (currentBlock + 1 > totalBlock) {
			pageStartsAt = (currentBlock - 1) * blockSize + 1;
		} else {
			pageStartsAt = currentBlock * blockSize + 1;
		}

		// if block is last block then page end is total page
		if (currentBlock + 1 >= totalBlock) {
			pageEndsAt = totalPage;
			hasNextBlock = false;
		} else {
			pageEndsAt = (currentBlock + 1) * blockSize;
			hasNextBlock = true;
		}
		
		if (currentBlock > 0) {
			hasPrevBlock = true;
		} else {
			hasPrevBlock = false;
		}
	}
}
