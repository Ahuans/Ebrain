package com.example.backend.Config;

/**
 * @author BruceChen
 * @date 2023/11/01 15:41
 **/
import com.netty_server.factory.ZookeeperFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //ZookeeperFactory.initialCreate("localhost:2181");
    }
}
