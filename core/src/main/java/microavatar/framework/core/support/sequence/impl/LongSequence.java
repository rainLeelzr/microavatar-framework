package microavatar.framework.core.support.sequence.impl;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.support.SpringContextUtil;
import microavatar.framework.core.support.sequence.Sequence;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.env.Environment;

/**
 * @author rain
 */
@Slf4j
public class LongSequence implements Sequence<Long> {

    private static LongIdWorker longIdWorker;

    @Override
    public Long next() {
        return get();
    }

    public static Long get() {
        if (longIdWorker == null) {
            newLongIdWorker();
        }
        return longIdWorker.nextId();
    }

    private static synchronized void newLongIdWorker() {
        if (longIdWorker != null) {
            return;
        }

        Environment env = null;
        try {
            env = SpringContextUtil.getBean(Environment.class);
        } catch (Exception e) {
            log.error("获取 Environment 失败:{}", e);
        }

        long workId;
        long dataCenterId;

        if (env == null) {
            workId = RandomUtil.randomLong(0, 32);
            dataCenterId = RandomUtil.randomLong(0, 32);
        } else {
            String workIdStr = env.getProperty("app.sequence.workId");
            if (StringUtils.isBlank(workIdStr)) {
                workId = RandomUtil.randomLong(0, 32);
            } else {
                workId = Long.valueOf(workIdStr);
            }

            String dataCenterIdStr = env.getProperty("app.sequence.dataCenterId");
            if (StringUtils.isBlank(dataCenterIdStr)) {
                dataCenterId = RandomUtil.randomLong(0, 32);
            } else {
                dataCenterId = Long.valueOf(dataCenterIdStr);
            }
        }

        longIdWorker = new LongIdWorker(workId, dataCenterId);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            System.out.println(LongSequence.get());
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
