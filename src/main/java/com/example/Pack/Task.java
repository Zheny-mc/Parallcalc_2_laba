package com.example.Pack;

import akka.actor.typed.ActorRef;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Task implements IMesssage {
    private int workId;
    private double[][] matrix;
    private ActorRef<IMesssage> replyTo;
}