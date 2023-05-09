package com.huizhixin.safe.comm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class MyCommandLineRunner1 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        // 大华人脸识别
        // MyFaceRecognition.projectinitial();

    }
}
