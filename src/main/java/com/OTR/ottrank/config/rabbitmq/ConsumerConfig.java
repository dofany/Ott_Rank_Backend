package com.OTR.ottrank.config.rabbitmq;

import com.OTR.ottrank.rabbitmq.dto.RabbitMqDto;
import com.OTR.ottrank.rankbas.service.RankBasService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableRabbit
@Configuration
public class ConsumerConfig {

    @Autowired
    RankBasService rankBasService;

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());

        return factory;
    }

    @RabbitListener(queues = "response_q")
    public void receiveMessage(String outputMessage) {
        String a = StringEscapeUtils.unescapeJava(outputMessage);
        Gson gson = new Gson();
        RabbitMqDto.Response.Message message = gson.fromJson(a, RabbitMqDto.Response.Message.class);

        List<String> list = message.getMovieRankList();

        String[] b = list.get(0).split("\n");

        if(b.length > 1) {
            rankBasService.rankDelete(b[4]);

            for(int i = 0; i < b.length; i++) {
                RabbitMqDto.Response.Message vo = new RabbitMqDto.Response.Message();
                // 파이선에서 크롤링한 결과 메세지를 rabbitmq에서 받아 db에 insert
                vo.setRank(Long.parseLong(b[i]));
                vo.setTitle(b[i+1]);
                vo.setChangeRank(b[i+2]);
                vo.setImgSrc(b[i+3]);
                vo.setCategory(b[i+4]);

                rankBasService.rankInsert(vo);

                log.info("rank : {} " ,b[i]);
                log.info("title : {} " ,b[i+1]);
                log.info("change rank : {}" ,b[i+2]);
                log.info("img src : {}" ,b[i+3]);
                log.info("category : {}" ,b[i+4]);

                i+=4;
            }
        }

    }
}

