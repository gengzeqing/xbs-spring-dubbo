package com.xuebusi.xbs.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * 动态切换数据源TradingRoutingDataSource数据源切换类
 */
public class TradingRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return TradingDataSourceHolder.getDataSourceKey();
	}

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);
		afterPropertiesSet();
	}
}