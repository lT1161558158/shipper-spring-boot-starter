package oh.my.shipperSpringBootStarter.executor;

import lombok.experimental.Delegate;
import oh.my.shipper.core.executor.ShipperExecutor;
import oh.my.shipper.core.task.ShipperTaskFuture;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PreDestroy;
import java.util.List;

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
    public List<ShipperTaskFuture> submit(String shipper) {
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
