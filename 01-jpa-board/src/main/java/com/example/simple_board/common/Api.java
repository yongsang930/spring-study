package com.example.simple_board.common;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Api<T> {

    private T body;

    private Pagination pagination;
}
