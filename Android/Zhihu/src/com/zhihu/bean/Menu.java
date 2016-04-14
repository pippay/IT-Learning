package com.zhihu.bean;

public class Menu implements Comparable<Menu> {

	private Integer id;
	private String name;
	private boolean focus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	@Override
	public int compareTo(Menu another) {
		return this.getId().compareTo(another.getId());
	}

}
