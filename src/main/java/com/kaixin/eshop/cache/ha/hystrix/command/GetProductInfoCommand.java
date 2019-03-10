package com.kaixin.eshop.cache.ha.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.kaixin.eshop.cache.ha.cache.local.LocationCache;
import com.kaixin.eshop.cache.ha.http.HttpClientUtils;
import com.kaixin.eshop.cache.ha.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @description: 获取商品信息
 * @author: weikailong on 2019-03-10 15:12
 **/
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    private Long productId;
    
    public GetProductInfoCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetProductInfoCommand"))
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
        return JSONObject.parseObject(response, ProductInfo.class);
    }
}
