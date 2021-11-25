package com.example.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.example.Pack.*;

public class GreeterBot extends AbstractBehavior<IMesssage> {

    public static Behavior<IMesssage> create() {
        return Behaviors.setup(context -> new GreeterBot(context));
    }

    private GreeterBot(ActorContext<IMesssage> context) {
        super(context);
    }

    @Override
    public Receive<IMesssage> createReceive() {
        return newReceiveBuilder()
                .onMessage(Task.class, this::onResultTask)
                .onMessage(PostStop.class, this::onPostStop)
                .build();
    }

    private Behavior<IMesssage> onResultTask(Task message) {
        getContext().getLog().info("Выполнение этапа {}", message.getWorkId());

        double[][] matr = message.getMatrix().clone();
        int len = matr[0].length;

        for (int row = 0; row < matr.length-row; row++) {  // row строки
            for (int col = 0; col < len; col++) {
                // обмен местами елементов
                Double temp = matr[row][col];
                matr[row][col] = matr[matr.length-row-1][len-col-1];
                matr[matr.length-row-1][len-col-1] = temp;
            }
        }
        message.getReplyTo().tell(new ResultTask(message.getWorkId(), matr));
        return this;
    }

    private Behavior<IMesssage> onPostStop(PostStop command) {
        getContext().getSystem().log().info("GreeterBot stopped");
        return Behaviors.stopped();
    }

}
