package com.OTR.ottrank.rabbitmq.service;
;
import com.OTR.ottrank.rabbitmq.dto.RabbitMqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RabbitMqService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${ottrankmq.req_exchanges}")
    private String EXCHANGE_NAME;

    @Value("${ottrankmq.route_key}")
    private String ROUTING_KEY;
    public List<RabbitMqDto.Response.Message> provider(RabbitMqDto.Request.Message inputMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(inputMessage);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);

        List<RabbitMqDto.Response.Message> list = new ArrayList<>();
        // consumer
        try {
            String pythonScriptPath = "/Users/dofany/Desktop/ottrank/rabbitmq/consumer.py";
            String[] command = {"python3", pythonScriptPath, message}; // 실행할 파일과 인수를 배열로 전달
            Process process = Runtime.getRuntime().exec(command, null, new File("/Users/dofany/Desktop/ottrank/rabbitmq/")); // 작업 디렉토리 설정
//            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

//            String line;
//
//            //schedulerRepository.rankDelete(inputMessage.getCategory());
//
//            while ((line = br.readLine()) != null) {
//                // 파이썬 프로세스의 출력을 처리
//                RabbitMqDto.Response.Message vo = new RabbitMqDto.Response.Message();
//                vo.setTitle(line);
//                // 다음 줄을 읽어 change rank 값을 설정
//                line = br.readLine();
//                vo.setChangeRank(line);
//                vo.setCategory(inputMessage.getCategory());
//
//                list.add(vo);
//
//                //schedulerRepository.rankInsert(vo);
//
//                System.out.println(list);
//            }

            // 파이썬 프로세스 종료까지 대기
            int exitCode = process.waitFor();
            log.info("Python Process exited with code: {}" ,exitCode);

        } catch (IOException e) {
            log.error("Error executing Python process: {}" ,e.getMessage());
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting for Python process: {}" ,e.getMessage());
            Thread.currentThread().interrupt();
        }

        return list;
    }


}
