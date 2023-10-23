package com.OTR.ottrank.scheduler.service;

import com.OTR.ottrank.rabbitmq.dto.RabbitMqDto;
import com.OTR.ottrank.rabbitmq.service.RabbitMqService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SchedulerService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    @Autowired
    RabbitMqService rabbitMqService;

    @Async
    public void runTask(RabbitMqDto.Request.Message category) {
        try {
            rabbitMqService.provider(category);
            System.out.println(category + " 스케줄러 성공");
        } catch (JsonProcessingException e) {
            System.err.println("Error executing task for " + category + ": " + e.getMessage());
        }
    }

    @Scheduled(cron = "* * */6 * * *")
    public void init() {
        RabbitMqDto.Request.Message message = new RabbitMqDto.Request.Message();
        executorService.submit(() -> {
            RabbitMqDto.Request.Message category = new RabbitMqDto.Request.Message();
            category.setCategory("통합");
            runTask(category);
        });
        executorService.submit(() -> {
            RabbitMqDto.Request.Message category = new RabbitMqDto.Request.Message();
            category.setCategory("넷플릭스");
            runTask(category);
        });
        executorService.submit(() -> {
            RabbitMqDto.Request.Message category = new RabbitMqDto.Request.Message();
            category.setCategory("티빙");
            runTask(category);
        });
        executorService.submit(() -> {
            RabbitMqDto.Request.Message category = new RabbitMqDto.Request.Message();
            category.setCategory("쿠팡플레이");
            runTask(category);
        });
        executorService.submit(() -> {
            RabbitMqDto.Request.Message category = new RabbitMqDto.Request.Message();
            category.setCategory("웨이브");
            runTask(category);
        });
        executorService.submit(() -> {
            RabbitMqDto.Request.Message category = new RabbitMqDto.Request.Message();
            category.setCategory("디즈니");
            runTask(category);
        });
        executorService.submit(() -> {
            RabbitMqDto.Request.Message category = new RabbitMqDto.Request.Message();
            category.setCategory("왓차");
            runTask(category);
        });
        executorService.submit(() -> {
            RabbitMqDto.Request.Message category = new RabbitMqDto.Request.Message();
            category.setCategory("박스오피스");
            runTask(category);
        });
    }
}
