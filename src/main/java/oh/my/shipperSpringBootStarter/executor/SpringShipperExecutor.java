package oh.my.shipperSpringBootStarter.executor;


import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import top.trister.shipper.core.executor.ShipperExecutor;
import top.trister.shipper.core.task.ShipperTask;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 代理了shipper的提交和执行,在执行之前先进行spel的模板解析
 */
public class SpringShipperExecutor implements ShipperExecutor, EnvironmentAware {
    private final ShipperExecutor shipperExecutor;
    private Environment environment;

    public SpringShipperExecutor(ShipperExecutor shipperExecutor) {
        this.shipperExecutor = shipperExecutor;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void execute(String shipper) {
        shipperExecutor.execute(spelEval(shipper));
    }

    @Override
    public List<CompletableFuture<ShipperTask>> submit(String shipper) {
        return shipperExecutor.submit(spelEval(shipper));
    }

    @PreDestroy//bean重载时进行关闭
    @Override
    public void close() throws Exception {
        shipperExecutor.close();
    }

    private String spelEval(String original) {
        return environment.resolvePlaceholders(original);
    }
}
