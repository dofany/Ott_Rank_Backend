package com.OTR.ottrank.rankbas.controller;

import com.OTR.ottrank.rankbas.dto.RankBasDto;
import com.OTR.ottrank.rankbas.service.RankBasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("랭킹조회")
@RestController
@RequestMapping("${property.api.end-point}")
@Slf4j
public class RankBasController {

    @Autowired
    RankBasService rankBasService;

    @ApiOperation("추천 영화 조회")
    @PostMapping("/ott/rank")
    public List<RankBasDto.Response.Message> getRankBas(RankBasDto.Request.Message massage) {
        return rankBasService.getRankBas(massage);
    }
}
