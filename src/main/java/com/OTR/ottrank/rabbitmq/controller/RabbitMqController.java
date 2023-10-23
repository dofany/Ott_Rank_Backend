package com.OTR.ottrank.rabbitmq.controller;

import com.OTR.ottrank.rabbitmq.dto.RabbitMqDto;
import com.OTR.ottrank.rabbitmq.service.RabbitMqService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("${property.api.end-point}")
public class RabbitMqController {
    @Autowired
    RabbitMqService rabbitService;

    @PostMapping("/rabbit/ott/rank")
    public List<RabbitMqDto.Response.Message> provider(@RequestBody RabbitMqDto.Request.Message inputMessage) throws JsonProcessingException {
        return rabbitService.provider(inputMessage);
    }
}

