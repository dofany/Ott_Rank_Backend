package com.OTR.ottrank.rankbas.repository;

import com.OTR.ottrank.rabbitmq.dto.RabbitMqDto;
import com.OTR.ottrank.rankbas.dto.RankBasDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RankBasRepository {

    List<RankBasDto.Response.Message> getRankBas(RankBasDto.Request.Message message);

    void rankDelete(String category);

    void rankInsert(RabbitMqDto.Response.Message vo);
}
