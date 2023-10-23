package com.OTR.ottrank.rankbas.service;

import com.OTR.ottrank.rabbitmq.dto.RabbitMqDto;
import com.OTR.ottrank.rankbas.dto.RankBasDto;
import com.OTR.ottrank.rankbas.repository.RankBasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankBasService {

    @Autowired
    RankBasRepository rankBasRepository;

    public List<RankBasDto.Response.Message> getRankBas(RankBasDto.Request.Message message) {
        return rankBasRepository.getRankBas(message);
    }

    public void rankDelete(String category) {
        rankBasRepository.rankDelete(category);
    }

    public void rankInsert(RabbitMqDto.Response.Message vo) {
        rankBasRepository.rankInsert(vo);
    }
}
