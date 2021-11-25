package com.example.Pack;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class ResultReserveArray implements IMesssage{
    private double[] arr;
}
