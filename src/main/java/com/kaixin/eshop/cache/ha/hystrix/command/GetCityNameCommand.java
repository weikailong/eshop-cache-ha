package com.kaixin.eshop.cache.ha.hystrix.command;

import com.kaixin.eshop.cache.ha.cache.local.LocationCache;
import com.kaixin.eshop.cache.ha.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * @description: 获取城市名称command
 * @author: weikailong on 2019-03-11 00:25
 **/
public class GetCityNameCommand extends HystrixCommand<String>{

    private Long cityId;

    public GetCityNameCommand(Long cityId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetCityNameGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetCityNameCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetCityNameThreadPool"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                    .withExecutionIsolationSemaphoreMaxConcurrentRequests(15)));
        this.cityId = cityId;
    }

    @Override
    protected String run() throws Exception {
        return LocationCache.getCityName(cityId);
    }
    
}
