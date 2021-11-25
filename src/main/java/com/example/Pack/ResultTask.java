package com.example.Pack;

import akka.actor.typed.ActorRef;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public final class ResultTask implements IMesssage {
    private int workId;
    private double[][] matrix;
}