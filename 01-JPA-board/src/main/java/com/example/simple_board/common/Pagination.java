package com.example.simple_board.common;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    private Integer page;

    private Integer size;

    private  Integer currentElements;

    private Integer totalPage;

    private Long totalElements;

}
