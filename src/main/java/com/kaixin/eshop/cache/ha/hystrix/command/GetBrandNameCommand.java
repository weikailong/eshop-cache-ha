package com.kaixin.eshop.cache.ha.hystrix.command;

import com.kaixin.eshop.cache.ha.cache.local.BrandCache;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @description: 获取品牌名称command
 * @author: weikailong on 2019-03-11 22:48
 **/
public class GetBrandNameCommand extends HystrixCommand<String> {
    
    private Long brandId;

    public GetBrandNameCommand(Long brandId){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BrandInfoService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetBrandNameCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetBrandInfoPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(15)
                        .withQueueSizeRejectionThreshold(10))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(15)));
        this.brandId = brandId;
    }
    
     
    @Override
    protected String run() throws Exception {
        // 调用一个品牌服务都接口
        // 如果调用失败了，报错了，就会去调用fallback降级机制
        throw new Exception();
    }

    @Override
    protected String getFallback() {
        System.out.println("从本地缓存获取过期都品牌数据，brandId=" + brandId);
        return BrandCache.getBrandName(brandId);
    }
}
