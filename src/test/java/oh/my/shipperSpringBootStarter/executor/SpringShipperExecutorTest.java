//package oh.my.shipperSpringBootStarter.executor;
//
//import oh.my.shipper.core.executor.ShipperExecutor;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.*;
//import java.net.URL;
//import java.util.stream.Collectors;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//class SpringShipperExecutorTest {
//    @Autowired
//    ShipperExecutor shipperExecutor;
//    @Test
//    void execute() throws FileNotFoundException {
//        URL url = Thread.currentThread().getContextClassLoader().getResource("test.shipper");
//        assert url != null;
//        String shipper = new BufferedReader(new FileReader(url.getFile())).lines().collect(Collectors.joining("\n"));
//        shipperExecutor.execute(shipper);
//    }
//}