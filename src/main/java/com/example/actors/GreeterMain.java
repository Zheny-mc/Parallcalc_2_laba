package com.example.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.example.Pack.*;

public class GreeterMain extends AbstractBehavior<IMesssage> {
    private final ActorRef<IMesssage> greeter;

    public static Behavior<IMesssage> create() {
        return Behaviors.setup(GreeterMain::new);
    }

    private GreeterMain(ActorContext<IMesssage> context) {
        super(context);
        //#create-actors
        greeter = context.spawn(Greeter.create(), "greeter");
        //#create-actors
    }

    private String matrixToStr(double[][] _matrix) {
        int len = _matrix.length;
        String strMatr = "\n";
        for (int i = 0; i < len; ++i){
            for (int j = 0; j < len; ++j){
                strMatr += String.format("%3.1f ", _matrix[i][j]);
            }
            strMatr += "\n";
        }
        return strMatr;
    }

    @Override
    public Receive<IMesssage> createReceive() {
        return newReceiveBuilder()
                .onMessage(SayHello.class, this::onSayHello)
                .onMessage(ResBigTask.class, this::onResBigTask)
                .build();
    }

    private Behavior<IMesssage> onSayHello(SayHello command) {
        getContext().getLog().info("Создание задачи!");
        //#create-actors
        ActorRef<IMesssage> replyTofirst =
                getContext().spawn(GreeterBot.create(), "worker1");
        ActorRef<IMesssage> replyToSecond =
                getContext().spawn(GreeterBot.create(), "worker2");
        ActorRef<IMesssage> replyToThree =
                getContext().spawn(Helper.create(), "helper");
        //#create-actors
        //----------------Task---------------
        int num = 1;
        double[][] result = new double[9][9];
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = num++;
            }
        }
        int len = result.length;
        //------------------------------------
        //send message
        getContext().getLog().info(matrixToStr(result));
        greeter.tell(new BigTask(len, result,
                replyTofirst, replyToSecond, replyToThree, getContext().getSelf()));
        //send message
        return this;
    }

    private Behavior<IMesssage> onResBigTask(ResBigTask message) {
        getContext().getLog().info("Задача готова!");
        getContext().getLog().info(matrixToStr(message.getMatrix()));
        return this;
    }
}
