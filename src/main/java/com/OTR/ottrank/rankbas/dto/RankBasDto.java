package com.OTR.ottrank.rankbas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

public class RankBasDto {
    public static class Request {
        @Getter
        @Setter
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Message implements Serializable {

            @ApiModelProperty(value="카테고리")
            private String category;
        }
    }

    public static class Response {
        @Getter
        @Setter
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Message implements Serializable {
            @ApiModelProperty(value="추천영화")
            private Long rank;
            private List<String> movieRankList;
            private String title;
            private String changeRank;
            private String category;
            private String imgSrc;
        }
    }
}
