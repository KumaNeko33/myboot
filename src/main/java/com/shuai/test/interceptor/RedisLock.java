package com.shuai.test.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: MiaoHongShuai
 * @Description: Redis distributed lock implementation.分布式锁的基本原理：用一个状态值表示锁，对锁的占用和释放通过状态值来标识。
 * 使用redis的setNX命令实现分布式锁　　
1、实现的原理
　　Redis为单进程单线程模式，采用队列模式将并发访问变成串行访问，且多客户端对Redis的连接并不存在竞争关系。redis的SETNX命令可以方便的实现分布式锁。
 * @Date: Created on 2017/8/31
 * @Modified By:
 */
public class RedisLock {
    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private RedisTemplate redisTemplate;
//    private Random random;

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;
//    private static int DEFAULT_ACQUIRY_RESOLUTION_MILLIS_RANDOM = new Random(200).nextInt();

    /**
     * Lock key path.需要加锁的标志，如商品的id
     */
    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;

    private volatile boolean locked = false;//用volatile修饰，使locked布尔状态变成 可见性的，当前线程对locked的修改对于其他线程是可见的，实现互斥，
    //互斥即一次只允许一个线程持有某个特定的锁，因此可使用该特性实现对共享数据的协调访问协议，这样，一次就只有一个线程能够使用该共享数据。

    /**
     * Detailed constructor with default acquire timeout 10000 msecs and lock expiration of 60000 msecs.
     *
     * @param lockKey lock key (ex. account:1, ...)
     */
    public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    /**
     * Detailed constructor with default lock expiration of 60000 msecs.
     *
     */
    public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs) {
        this(redisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    /**
     * Detailed constructor.
     *
     */
    public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    /**
     * @return lock key
     */
    public String getLockKey() {
        return lockKey;
    }

/**    返回值：
        　　当 key 不存在时，返回 null ，否则，返回 key 的值。
        　　如果 key 不是字符串类型，那么返回一个错误
*/
    private String get(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] data = connection.get(serializer.serialize(key));
                    connection.close();
                    if (data == null) {
                        return null;
                    }
                    return serializer.deserialize(data);
                }
            });
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return obj != null ? obj.toString() : null;
    }

    /**
    将 key 的值设为 value ，当且仅当 key 不存在。
    若给定的 key 已经存在，则 SETNX 不做任何动作。
    SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写
返回值：
　　设置成功，返回 1 。
　　设置失败，返回 0 。*/
    private boolean setNX(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (Boolean) obj : false;
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     　　当 key 存在但不是字符串类型时，返回一个错误。
     返回值：
     　　返回给定 key 的旧值。
     　　当 key 没有旧值时，也即是， key 不存在时，返回 null 。
     */
    private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return serializer.deserialize(ret);
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (String) obj : null;
    }

    /**
     * 获得 lock.
     * 实现思路: 主要是使用了redis 的setnx命令,缓存了锁.
     * reids缓存的key是锁的key,所有的共享, value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间)
     * 执行过程:
     * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    public synchronized boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;//锁等待时间，防止线程饥饿，锁等待时间即线程尝试获得锁的轮询时间
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); //锁到期时间
            if (this.setNX(lockKey, expiresStr)) { //取锁，setNX返回1的话说明：lockKey不存在，锁未被占用，成功获得锁
                // lock acquired
                locked = true;
                return true;//获得锁成功，返回true
            }
            //lockKey存在，即锁已存在，被占用，则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
            String currentValueStr = this.get(lockKey); //将redis里的lockKey对应的值即字符串：时间 赋值给currentValueStr
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //判断是否为空且是否过期
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                // lock is expired

                String oldValueStr = this.getSet(lockKey, expiresStr);//这个方法并不会改变currentValueStr的值，它的值是字面量，存在常量池中
                //获取上一个锁到期时间，并设置现在的锁到期时间，返回之前redis中lockKey对应的值给oldValueStr
                    //注意：这时如何有另一个线程也接着当前线程 调用this.getSet了,但是它返回的旧值 将是当前线程新设置的锁到期时间，将无法通过下面的if判断
                    //即只要第一个在之前锁超时后设置新的锁到期时间的线程才能 真正上锁
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受

                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置新值后返回的旧值和当前值相同，他才有权利获取锁
                    // lock acquired
                    locked = true;
                    return true;//获得锁成功，返回true
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;//等待锁的时间减100毫秒

        /*
            延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
            只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
            使用随机的等待时间可以一定程度上保证公平性
         */
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);//其他线程为获得锁，休眠100毫秒后重新轮询锁

        }
        return false;
    }

    /**
     * 解锁之前检查锁的是否已过期:过期的话就不用再解锁了，因为已经被其他线程获得锁------该方法待检测是否可行
     */
    public synchronized boolean checkLocktime(){
        String currentValueStr = this.get(lockKey);
        if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            return false;
        }
        return true;
    }

    /**
     * Acqurired lock release.解锁，释放锁，redis中删除lockKey对应的键值对
     */
    public synchronized void unlock() {
        if (locked) {
            redisTemplate.delete(lockKey);
            locked = false;
        }
    }

//业务层调用：
    public void test(String key) {
        //在进行获取锁lock()之前可以 先进行应用层首次过滤，通过 队列来限制过滤请求数，比如商品数为100，因为考虑到处理的失败，我们就给队列开的总数为500。
        //秒杀开始后，我们的队列只接受前面500个请求，当数量满500后，后面的请求都 返回秒杀结束。如果一瞬间的并发数大于500，我们就随机取500个到队列
        /*如果是分布式集群服务器，就需要有一个或多个多层专门的队列服务器，或者配置缓存队列共享。如redis队列，value使用list，
        redis的list对象blpop brpop接口以及Pub/Sub(发布/订阅)的某些接口。他们都是阻塞版的，所以可以用来做消息队列
此方案成立的前提是并发量很大，能接近或者超过放出的数量。如果商品库存很足，而且并发量不大，反倒影响了用户体验。*/
        RedisLock lock = new RedisLock(redisTemplate, key, 10000, 20000);
        try {
            if(lock.lock()) {//为true则说明获得锁
                //需要加锁的代码
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，
            // 因为可能客户端因为某个耗时的操作而挂起，操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。
            if(lock.checkLocktime()){
                lock.unlock();
            }
        }
    }
    /*、解决死锁
　　上面的锁定逻辑有一个问题：如果一个持有锁的客户端失败或崩溃了不能释放锁，该怎么解决？

我们可以通过锁的键对应的时间戳来判断这种情况是否发生了，如果当前的时间已经大于lock.foo的值，说明该锁已失效，可以被重新使用。
　　发生这种情况时，可不能简单的通过DEL来删除锁，然后再SETNX一次（讲道理，删除锁的操作应该是锁拥有这执行的，这里只需要等它超时即可），
当多个客户端检测到锁超时后都会尝试去释放它，这里就可能出现一个竞态条件,让我们模拟一下这个场景：

C0操作超时了，但它还持有着锁，C1和C2读取lock.foo检查时间戳，先后发现超时了。
C1 发送DEL lock.foo
C1 发送SETNX lock.foo 并且成功了。
C2 发送DEL lock.foo
C2 发送SETNX lock.foo 并且成功了。
这样一来，C1，C2都拿到了锁！问题大了！

幸好这种问题是可以避免的，让我们来看看C3这个客户端是怎样做的：

C3发送SETNX lock.foo 想要获得锁，由于C0还持有锁，所以Redis返回给C3一个0
C3发送GET lock.foo 以检查锁是否超时了，如果没超时，则等待或重试。
反之，如果已超时，C3通过下面的操作来尝试获得锁：
GETSET lock.foo <current Unix time + lock timeout + 1>
通过GETSET，C3拿到的时间戳如果仍然是超时的，那就说明(没有其他线程在GET和GETSET操作之间执行SETNX拿到锁)，C3如愿以偿拿到锁了。
如果在C3之前，有个叫C4的客户端比C3快一步执行了上面的操作，那么C3拿到的时间戳是个未超时的值，这时，C3没有如期获得锁，需要再次等待或重试。
    留意一下，尽管C3没拿到锁，但它改写了C4设置的锁的超时值（即延长了C4的锁的超时值，时间为C4执行SETNX操作至C3执行GETSET操作之间的时间），不过这一点非常微小的误差带来的影响可以忽略不计。
注意：为了让分布式锁的算法更稳键些，持有锁的客户端 在解锁之前 应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
    操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。


    一些问题

1、为什么不直接使用expire设置超时时间，而将时间的毫秒数其作为value放在redis中？

如下面的方式，把超时的交给redis处理：

lock(key, expireSec){
isSuccess = setnx key
if (isSuccess)
    expire key expireSec
}
　　这种方式貌似没什么问题，但是假如在setnx后，redis崩溃了，expire就没有执行，结果就是死锁了。锁永远不会超时。
而使用将 时间的毫秒数作为value放在redis中，redis集群中一个redis崩溃后还有从节点【备用的redis称为从节点（slave）】

 2、为什么前面的锁已经超时了，还要用getSet去设置新的时间戳的时间获取旧的值，然后和外面的判断超时时间的时间戳比较呢？
 因为是分布式的环境下，可以在前一个锁失效的时候，有两个进程进入到锁超时的判断。如：

C0超时了，还持有锁,C1/C2都C1/C2获取到了C0的超时时间(即相同的currentValueStr)，然后同时请求进入了方法里面

C1先使用getSet方法-----设置新的超时时间，返回的旧的C0的超时时间oldValueStr==currentValueStr

C2也执行了getSet方法-----设置新的超时时间，返回的旧的C1的设置的超值时间oldValueStr!=currentValueStr

假如我们不加 oldValueStr.equals(currentValueStr) 的判断，将会C1/C2都将获得锁（即使locked=true)，加了之后，能保证C1和C2只能一个能获得锁，一个只能继续等待。

注意：这里可能导致超时时间不是其原本的超时时间，C1的超时时间可能被C2覆盖了，但是他们相差的毫秒极其小，这里忽略了。

*/
}