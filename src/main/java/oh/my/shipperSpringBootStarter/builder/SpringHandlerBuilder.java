package oh.my.shipperSpringBootStarter.builder;

import lombok.extern.slf4j.Slf4j;
import oh.my.shipper.core.api.Handler;
import oh.my.shipper.core.builder.HandlerBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * spring 的 handlerBuilder
 */
@Slf4j
public class SpringHandlerBuilder implements HandlerBuilder, BeanFactoryAware {
    private BeanFactory beanFactory;
    private final HandlerBuilder handlerBuilder;

    public SpringHandlerBuilder(HandlerBuilder handlerBuilder) {
        this.handlerBuilder = handlerBuilder;
    }

    @Override
    public void reLoadHandler() {
        handlerBuilder.reLoadHandler();
    }

    @Override
    public Handler builderHandler(String handleName) {
        try {
            return beanFactory.getBean(handleName, Handler.class);
        } catch (RuntimeException ignore) {
            log.debug("not find {} handler in beanFactory",handleName);
        }
        return handlerBuilder.builderHandler(handleName);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


}
