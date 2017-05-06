package cn.jikon.domain.vo;

import java.util.List;

/**
 * 分页显示的标准类,基本操作,是先给予-当前页数一共的数据条数-每页显示的条数, 然后在初始化该类,得到总共页数,和开始序号和结束序号,
 * 然后数据库分页用到开始序号和结束序号,得到数据集合后赋值给该类的list属性,
 * 
 * 
 * 
 * @author admin
 *
 * @param <T>
 */
public class PageVo<T> {
	private int pageIndex;// 当前页数

	private int pageSize;// 一共的页数

	private int count;// 数据条数

	private int pageCount;// 每页的数据条数

	private int start;// 起始数据位置

	private int end;// 结束

	private boolean useCount = false;//是否统计总行数

	private boolean pagination = false;// 是否分页

	private String sortBy = "1&asc";

	private String gridFilter = "";

	private List<T> list = null;

	//放在拦截器里面做了
	public void init() {
		/*
		 * 根count 和pageCount计算页数pageSize
		 */
		int pageSize_x = count / pageCount;
		if (count >= pageCount) {
			this.pageSize = count % pageCount == 0 ? pageSize_x : pageSize_x + 1;
		} else {
			this.pageSize = 1;
		}
		// 判断页数和当前页数
		if (pageIndex > pageSize) {
			pageIndex = pageSize;
		}
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		// 根据当前页计算起始和结束条目
		this.start = (pageIndex - 1) * pageCount;
		this.end = pageIndex * pageCount-1;
	}

	public PageVo(int pageIndex, int count, int pageCount) {
		super();
		this.pageIndex = pageIndex;
		this.count = count;
		this.pageCount = pageCount;
	}

	/**
	 * wjp
	 * 新增入参
	 * @param filter 过滤条件
	 * @param sort 排序
	 * @param pagination 分页
	 * @param pageIndex 当前页数
	 * @param pageCount 页面上的数据条数
	 */
	public PageVo(String filter, String sort, boolean pagination,
				  int pageIndex, int pageCount){
		super();
		this.pageIndex = pageIndex;
		this.pageCount = pageCount;
		this.gridFilter = filter;
		this.pagination = pagination;
//		if (StringUtils.isNotEmpty(sort)) {
//			this.sortBy = sort;
//		}
	}

	public PageVo(int pageIndex, int count, int pageCount, List<T> list) {
		super();
		this.pageIndex = pageIndex;
		this.count = count;
		this.pageCount = pageCount;
		this.pagination = true;
		this.list = list;
	}

	public PageVo() {
		super();

	}

	@Override
	public String toString() {
		return "PageVo [count=" + count + ", end=" + end + ", list=" + list + ", pageCount=" + pageCount + ", pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", start=" + start + "]";
	}

	public boolean isUseCount() {
		return useCount;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getGridFilter() {
		return gridFilter;
	}

	public void setGridFilter(String gridFilter) {
		this.gridFilter = gridFilter;
	}

	public void setUseCount(boolean useCount) {
		this.useCount = useCount;
	}

	public boolean isPagination() {
		return pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
