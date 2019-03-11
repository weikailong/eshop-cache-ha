package com.kaixin.eshop.cache.ha.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @description: 更新商品信息command
 * @author: weikailong on 2019-03-11 13:50
 **/
public class UpdateProductInfoCommand extends HystrixCommand<Boolean> {

    private Long productId;
    
    protected UpdateProductInfoCommand(Long productId) {
        super(HystrixCommandGroupKey.Factory.asKey("UpdateProductInfoGroup"));
        this.productId = productId;
    }

    @Override
    protected Boolean run() throws Exception {
        // 执行一次商品更新
        GetProductInfoCommand.flushCache(productId);
        return true;
    }
}
