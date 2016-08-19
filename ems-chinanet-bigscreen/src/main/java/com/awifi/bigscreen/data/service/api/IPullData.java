package com.awifi.bigscreen.data.service.api;

public interface IPullData<T> {
	
	public boolean isExist();
	
	public T Pull();

}
