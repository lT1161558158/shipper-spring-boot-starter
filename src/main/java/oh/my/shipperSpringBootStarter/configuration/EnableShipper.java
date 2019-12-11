package oh.my.shipperSpringBootStarter.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 使用该注解将自动装配shipper运行所需要的环境
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ShipperAutoConfiguration.class)
public @interface EnableShipper {
}
