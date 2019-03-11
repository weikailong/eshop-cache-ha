package com.kaixin.eshop.cache.ha.controller;

import com.kaixin.eshop.cache.ha.http.HttpClientUtils;
import com.kaixin.eshop.cache.ha.hystrix.command.GetBrandNameCommand;
import com.kaixin.eshop.cache.ha.hystrix.command.GetCityNameCommand;
import com.kaixin.eshop.cache.ha.hystrix.command.GetProductInfoCommand;
import com.kaixin.eshop.cache.ha.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 缓存controller
 * @author: weikailong on 2019-03-10 14:25
 **/
@Controller
public class CacheController {

    /**
     * 模拟从消息队列中消费数据
     * @param productId 商品id
     * @return 
     */
    @RequestMapping("/change/product")
    @ResponseBody 
    public String changeProduct(Long productId){
        // 拿到一个商品id
        // 调用商品服务的接口，获取商品id对应的商品的最新数据
        // 用HttpClient去调用商品服务的http接口
        String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        System.out.println(response);
        
        return "success";
    }

    /**
     * nginx开始，各级缓存都失效了，nginx发送很多的请求直接到缓存服务要求拉取最原始都数据
     * @param productId 商品id
     * @return
     */
    @RequestMapping("/getProductInfo")
    @ResponseBody
    public String getProductInfo(Long productId){
        // 拿到一个商品id
        // 调用商品服务的接口，获取商品id对应的商品的最新数据
        // 用HttpClient去调用商品服务的http接口
        HystrixCommand<ProductInfo> getProductInfoCommand = new GetProductInfoCommand(productId);
        ProductInfo productInfo = getProductInfoCommand.execute();
 
        // 城市名称(基于信号量来实现本地缓存都限流）
        Long cityId = productInfo.getCityId();
        GetCityNameCommand getCityNameCommand = new GetCityNameCommand(cityId);
        String cityName = getCityNameCommand.execute();
        productInfo.setCityName(cityName);

        Long brandId = productInfo.getBrandId();
        GetBrandNameCommand getBrandNameCommand = new GetBrandNameCommand(brandId);
        String brandName = getBrandNameCommand.execute();
        productInfo.setBrandName(brandName);

        // 使用异步都方式获取
//        Future<ProductInfo> future = getProductInfoCommand.queue();
//        try {
//            Thread.sleep(1000);
//            System.out.println(future.get());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
 
        System.out.println(productInfo);
        return "success";
    }

    /**
     * nginx开始，各级缓存都失效了，nginx发送很多的请求直接到缓存服务要求拉取最原始都数据
     * @param productIds 商品id字符串数组
     * @return
     */
    @RequestMapping("/getProductInfos")
    @ResponseBody
    public String getProductInfos(String productIds){
        
//        HystrixObservableCommand<ProductInfo> getProductInfosCommand = 
//                new GetProductInfosCommand(productIds.split(","));
//        Observable<ProductInfo> observable = getProductInfosCommand.observe();
//        
////        observable = getProductInfosCommand.toObservable(); // 还没有执行
//        
//        observable.subscribe(new Observer<ProductInfo>() { // 等到调用subscribe然后才会执行
//            @Override
//            public void onCompleted() {
//                System.out.println("获取完了所有都商品数据");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(ProductInfo productInfo) {
//                System.out.println(productInfo);
//            }
//        });

        for (String productId : productIds.split(",")) {
            GetProductInfoCommand getProductInfoCommand = new GetProductInfoCommand(
                    Long.valueOf(productId));

            ProductInfo productInfo = getProductInfoCommand.execute();
            System.out.println(productInfo);
            System.out.println(getProductInfoCommand.isResponseFromCache());
            
        }
        
        return "success";
    }
    
}
