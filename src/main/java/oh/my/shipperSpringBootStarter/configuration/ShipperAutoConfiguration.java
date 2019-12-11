package oh.my.shipperSpringBootStarter.configuration;

import lombok.extern.slf4j.Slf4j;
import oh.my.shipper.core.builder.*;
import oh.my.shipper.core.executor.ShipperExecutor;
import oh.my.shipper.core.executor.StandardShipperExecutor;
import oh.my.shipperSpringBootStarter.builder.SpringHandlerBuilder;
import oh.my.shipperSpringBootStarter.executor.SpringShipperExecutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;

//@ConditionalOnClass(ShipperExecutor.class)

/**
 * shipper的自动配置类
 */
@Slf4j
public class ShipperAutoConfiguration {
    public static final String SHIPPER_THREAD_POOL="shipperThreadPool";
    /**
     *
     * @return bean handlerBuilder 的标准实现
     */
    @Bean
    @ConditionalOnMissingBean(HandlerBuilder.class)
    public HandlerBuilder handlerBuilder() {
        HandlerBuilder handlerBuilder = new StandardHandlerBuilder();
        handlerBuilder.reLoadHandler();
        return handlerBuilder;
    }

    /**
     *
     * @param handlerBuilder handlerBuilder
     * @return ShipperBuilder 的标准实现,其使用的 handlerBuilder 是  {@link SpringHandlerBuilder}
     */
    @Bean
    @ConditionalOnMissingBean(ShipperBuilder.class)
    public ShipperBuilder shipperBuilder(HandlerBuilder handlerBuilder) {
        return new StandardShipperBuilder(new SpringHandlerBuilder(handlerBuilder));
    }

    /**
     *
     * @return ShipperTaskBuilder 的标准实现
     */
    @Bean
    @ConditionalOnMissingBean(ShipperTaskBuilder.class)
    public ShipperTaskBuilder shipperTaskBuilder() {
        return new StandardShipperTaskBuilder();
    }

    /**
     * 执行器 SHIPPER_THREAD_POOL
     * @param corePoolSize ${shipper.executor.core-pool-size:10}
     * @param maxPoolSize ${shipper.executor.max-pool-size:10}
     * @param keepAliveTime ${shipper.executor.keepalive-time:60}
     * @return ExecutorService {@see SHIPPER_THREAD_POOL}
     */
    @Bean(SHIPPER_THREAD_POOL)
    @ConditionalOnMissingBean(name = SHIPPER_THREAD_POOL)
    public ExecutorService shipperThreadPool(@Value("${shipper.executor.core-pool-size:10}") int corePoolSize,
                                             @Value("${shipper.executor.max-pool-size:10}") int maxPoolSize,
                                             @Value("${shipper.executor.keepalive-time:60}") int keepAliveTime) {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler((t, e) -> log.error("shipper executor error", e));
            return thread;
        }, (r, executor) -> log.error("runable {} rejected",r));
    }

    /**
     *
     * @param shipperBuilder shipperBuilder
     * @param shipperTaskBuilder shipperTaskBuilder
     * @param executorService executorService
     * @return 使用了 {@link SpringShipperExecutor} 代理标准实现
     */
    @Bean
    @ConditionalOnMissingBean(ShipperExecutor.class)
    public ShipperExecutor shipperExecutor(ShipperBuilder shipperBuilder, ShipperTaskBuilder shipperTaskBuilder, @Qualifier(SHIPPER_THREAD_POOL) ExecutorService executorService) {
        return new SpringShipperExecutor(new StandardShipperExecutor(shipperBuilder, shipperTaskBuilder, executorService));
    }
}
