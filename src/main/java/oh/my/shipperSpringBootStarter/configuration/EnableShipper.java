package oh.my.shipperSpringBootStarter.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ShipperAutoConfiguration.class)
public @interface EnableShipper {
}
