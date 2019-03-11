package com.kaixin.eshop.cache.ha.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.kaixin.eshop.cache.ha.http.HttpClientUtils;
import com.kaixin.eshop.cache.ha.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

/**
 * @description: 获取商品信息
 * @author: weikailong on 2019-03-10 15:12
 **/
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    private static final HystrixCommandKey KEY = HystrixCommandKey.Factory.asKey("GetProductInfoCommand");
    
    private Long productId;
    
    public GetProductInfoCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
                .andCommandKey(KEY)
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                    .withCoreSize(15)
                    .withQueueSizeRejectionThreshold(10)));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        System.out.println("调用接口，查询商品数据，productId=" + productId);
        return JSONObject.parseObject(response, ProductInfo.class);
    }

    @Override
    protected String getCacheKey() {
        return "product_info_" + productId;
    }
    
    public static void flushCache(Long productId){
        HystrixRequestCache.getInstance(KEY,
                HystrixConcurrencyStrategyDefault.getInstance()).clear("product_info_" + productId);
    } 
}
