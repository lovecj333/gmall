package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuImage;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuImageMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import java.util.List;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService{

    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        //skuInfo
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        long skuId = pmsSkuInfo.getId();
        //skuAttrValue
        List<PmsSkuAttrValue> attrValues = pmsSkuInfo.getSkuAttrValueList();
        for(PmsSkuAttrValue attrValue : attrValues){
            attrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(attrValue);
        }
        //skuSaleAttrValue
        List<PmsSkuSaleAttrValue> saleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
        for(PmsSkuSaleAttrValue saleAttrValue : saleAttrValues){
            saleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(saleAttrValue);
        }
        //skuImage
        List<PmsSkuImage> skuImages = pmsSkuInfo.getSkuImageList();
        for(PmsSkuImage skuImage : skuImages){
            skuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(skuImage);
        }
    }

    public PmsSkuInfo getSkuInfoByIdFromDb(long skuId) {
        //skuInfo
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
        //skuImage
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> skuImages = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(skuImages);
        return skuInfo;
    }

    @Override
    public PmsSkuInfo getSkuInfoById(long skuId) {
        PmsSkuInfo pmsSkuInfo;
        Jedis jedis = redisUtil.getJedis();
        String skuLockKey = "sku:"+skuId+":lock";
        String skuKey = "sku:"+skuId+":info";
        String skuJson = jedis.get(skuKey);
        if(StringUtils.isNoneBlank(skuJson)){
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        }else{
            //为了解决缓存击穿问题设置分布式锁
            String token = UUID.randomUUID().toString();
            String getLock = jedis.set(skuLockKey, token, "nx", "px", 10*1000);//锁失效时间10秒
            if(StringUtils.isNoneBlank(getLock) && getLock.equals("OK")){
                pmsSkuInfo = getSkuInfoByIdFromDb(skuId);
                if(pmsSkuInfo != null){
                    //查询结构存入redis
                    jedis.set(skuKey, JSON.toJSONString(pmsSkuInfo));
                }else{
                    //数据库中不存在此sku信息、防止缓存穿透将空字符串值设置给redis、3分钟失效
                    jedis.setex(skuKey, 60*3, "");
                }
                //解锁分布式锁
                String lockToken = jedis.get(skuLockKey);
                if(StringUtils.equals(token, lockToken)){
                    jedis.del(skuLockKey);
                }
            }else{
                //没有拿到分布式锁 自旋 3秒之后再请求一次
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getSkuInfoById(skuId);
            }
        }
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(long productId) {
        return pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
    }

    @Override
    public List<PmsSkuInfo> getAllSku() {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectAll();
        for(PmsSkuInfo pmsSkuInfo : pmsSkuInfos){
            long skuId = pmsSkuInfo.getId();
            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);
            List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);
            pmsSkuInfo.setSkuAttrValueList(pmsSkuAttrValues);
        }
        return pmsSkuInfos;
    }
}
