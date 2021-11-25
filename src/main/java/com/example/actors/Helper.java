package com.example.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.example.Pack.*;

public class Helper extends AbstractBehavior<IMesssage> {
    public static Behavior<IMesssage> create() {
        return Behaviors.setup(context -> new Helper(context));
    }

    private Helper(ActorContext<IMesssage> context) {
        super(context);
    }

    @Override
    public Receive<IMesssage> createReceive() {
        return newReceiveBuilder()
                .onMessage(ReverseArray.class, this::onReverseArray)
                .onMessage(PostStop.class, this::onPostStop)
                .build();
    }

    private Behavior<IMesssage> onReverseArray(ReverseArray message) {
        getContext().getLog().info("Центральная строка переворачивается");

        int len = message.getArr().length;
        double[] centreRow = message.getArr();

        for (int i = len-1, j = 0; i >= len/2 ; i--,j++) {
            double temp = centreRow[j];
            centreRow[j] = centreRow[i];
            centreRow[i] = temp;
        }
        message.getReplyTo().tell(new ResultReserveArray(centreRow));
        return this;
    }

    private Behavior<IMesssage> onPostStop(PostStop message) {
        getContext().getSystem().log().info("Helper stopped");
        return Behaviors.stopped();
    }
}
