package oh.my.shipperSpringBootStarter.builder;

import oh.my.shipper.core.api.Handler;
import oh.my.shipper.core.builder.HandlerBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * spring 的 handlerBuilder
 */
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
    public Handler builderHandler(String s) {
        try {
            return beanFactory.getBean(s, Handler.class);
        } catch (RuntimeException ignore) {
        }
        return handlerBuilder.builderHandler(s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


}
