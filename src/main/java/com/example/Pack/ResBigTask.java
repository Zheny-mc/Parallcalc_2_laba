package com.example.Pack;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class ResBigTask implements IMesssage {
    private double[][] matrix;
}
