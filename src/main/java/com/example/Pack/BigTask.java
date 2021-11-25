package com.example.Pack;

import akka.actor.typed.ActorRef;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class BigTask implements IMesssage {
    private int lenth;
    private double[][] matrix;
    private ActorRef<IMesssage> sendToF;
    private ActorRef<IMesssage> sendToS;
    private ActorRef<IMesssage> sendToThr;
    private ActorRef<IMesssage> replyTo;
}
