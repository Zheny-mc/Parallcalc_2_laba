package com.example.Pack;

import akka.actor.typed.ActorRef;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class ReverseArray implements IMesssage {
    private double[] arr;
    private ActorRef<IMesssage> replyTo;
}
