package com.mint.other.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author cw
 */
@Component
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置普通key value
     * @param key
     * @param value
     */
    public void setKeyValue(String key,String value){
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key,value);
    }

    /**
     * 设置带有失效时间的key value，单位为秒钟
     * @param key
     * @param value
     * @param expireTime
     */
    public void setKeyValueWithExpire(String key, Object value, Long expireTime) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 获取value通过key
     * @param key
     * @return object对象，不存在则为null
     */
    public Object getValueByKey(String key){
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 删除缓存值 根据key key参数可以为多个
     * @param key
     * @AddDescription 删除成功返回"1"，没有这个key删除则返回"0"
     */
    public void delValuesByKey(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 增加某个key的数量
     * @param key
     * @param number
     * @Rerutn 新增后的值
     */
    public Long incrByKey(String key, Long number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    /**
     * 减少某个key的数量
     * @param key
     * @param number
     * @Rerutn 减少后的值
     */
    public Long decrByKey(String key, Long number) {
        return redisTemplate.opsForValue().decrement(key, number);
    }

    /**
     * 新增key HashMap中的Item
     * @param key
     * @param item
     * @param value
     */
    public void setHMapItem(String key,String item,Object value){
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 新增key HashMap中的Item 并设置HashMap的失效时间
     * @param key
     * @param item
     * @param value
     * @param time
     * @Description 将替换原有的失效时间 秒
     */
    public void setHMapItem(String key,String item,Object value,long time){
        redisTemplate.opsForHash().put(key, item, value);
        redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    /**
     * 设置key HashMap集合
     * @param key
     * @param map
     */
    public void setHMap(String key,Map<String,Object> map){
        redisTemplate.opsForHash().putAll(key,map);
    }

    /**
     * 设置key HashMap集合 带有失效时间秒
     * @param key
     * @param map
     * @param time
     */
    public void setHMapWithExpire(String key,Map<String,Object> map,long time){
        redisTemplate.opsForHash().putAll(key,map);
        redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    /**
     * 判断hashMap的item字段是否存在
     * @param key
     * @param item
     * @return true or false
     */
    public boolean hasHashItem(String key,String item){
        return redisTemplate.opsForHash().hasKey(key,item);
    }

    /**
     * 获取HashMap的具体Item的值
     * @param key
     * @param item
     * @return
     */
    public Object getHMapItem(String key,String item){
        return redisTemplate.opsForHash().get(key,item);
    }

    /**
     * 获取map集合
     * @param key
     * @return
     */
    public Map<Object,Object> getHMap(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 原子操作新增hashMap的item的数值
     * @param key
     * @param item
     * @param num
     * @return 新增后的数值
     */
    public Double hMapItemIncr(String key,String item,Double num){
        return redisTemplate.opsForHash().increment(key,item,num);
    }

    /**
     * 原子操作减少hashMap的item的数值
     * @param key
     * @param item
     * @param num
     * @return 减少后的数值
     */
    public Long hMapItemDecr(String key,String item,Long num){
        return redisTemplate.opsForHash().increment(key,item,-num);
    }

    /**
     * 向zSet中增加成员
     * @param key
     * @param value
     * @param score
     */
    public void zSetAdd(String key, Object value, Double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 向zSet中增加多个成员信息
     * @param key key
     * @param sets sets
     */
    public Long zSetAddMore(String key, Set<ZSetOperations.TypedTuple<Object>> sets) {
        return redisTemplate.opsForZSet().add(key, sets);
    }

    /**
     * 增加zSet中成员的score
     * @param key key
     * @param value value
     * @param score score
     */
    public void zSetIncrementScore(String key, Object value, Double score) {
        redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * 获取该value在key中的排名
     * @param key key
     * @param value value
     * @return long
     */
    public Long zSetReverseRank(String key, Object value) {
        Long rank = redisTemplate.opsForZSet().reverseRank(key, value);
        if (rank == null) {
            return null;
        }
        return rank + 1;
    }


    /**
     * 获取该set中的排名数据
     * @param key key
     * @return set
     */
    public Set<Object> zSetReverseRange(String key, long start, long end) {
        // 一次最多获取35条数据
        // int limit = 35;
        // 开始
        // int start = (page - 1) * limit
        // 结束
        // int end = page * limit -1
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取该set中的排名数据
     * @param key key
     * @param page 第几页
     * @return set
     */
    public List<Map> zSetReverseRangeWithScore(String key, Integer page) {
        // 一次最多获取20条数据
        List<Map> setList = new ArrayList<>();
        int limit = 4;
        // 开始
        int start = (page - 1) * limit;
        // 结束
        int end = page * limit -1;
        Set< ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        System.out.println("typedTuples: " + typedTuples);

        // 如果获取的数据不为空值得话
        if (typedTuples != null) {
            if (typedTuples.size() > 0) {
                for (ZSetOperations.TypedTuple<Object> typedTuple : typedTuples) {
                    Map<String, Object> itemMap = new HashMap<>(2);
                    Object value = typedTuple.getValue();
                    Double score = typedTuple.getScore();
                    itemMap.put("value", value);
                    itemMap.put("score", score);
                    setList.add(itemMap);
                }
            }
        }

        return setList;
    }

    /**
     * 将多个用户加入队列中
     * @param key key
     * @param value typedTuple
     * @return long
     */
    public Long zSetMultiAdd(String key, Set<ZSetOperations.TypedTuple<Object>> value) {
        return redisTemplate.opsForZSet().add(key, value);
    }

    /**
     * 将多个值加入到set中
     * @param key key
     * @param value 数组value
     * @return long
     */
    public Long setAddMore(String key, Object... value) {
        if (value.length == 0) {
            return 0L;
        }

        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 从set中随机删除一个
     * @param key key
     * @return object
     */
    public Object setPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * list队列中加入一个值
     * @param key key
     * @param value 值
     */
    public Long listAddOne(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将数组加入到队列中
     * @param key key
     * @param arr 数组
     */
    public void listAddMore(String key, Object... arr) {
        redisTemplate.opsForList().leftPushAll(key, arr);
    }

    /**
     * 将数组加入到队列中
     * @param key key
     */
    public List<Object> listGetAll(String key) {
        return redisTemplate.opsForList().range(key, 0L, -1L);
    }

    /**
     * 右出队多个数据
     * @param key
     * @param number
     * @return
     */
    public Object listPopMore(String key, Long number) {
        return redisTemplate.opsForList().rightPop(key, number, TimeUnit.SECONDS);
    }

    /**
     * 右边出队一个数据
     * @param key key
     * @return object null
     */
    public Object listRightPopOne(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }


}
