package com.example.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.example.Pack.*;

// #greeter
public class Greeter extends AbstractBehavior<IMesssage> {

    private int countStep;
    private int len;
    private double[][] matrix;
    private ActorRef<IMesssage> workF;
    private ActorRef<IMesssage> workS;
    private ActorRef<IMesssage> helper;
    private ActorRef<IMesssage> replyTo;

    public static Behavior<IMesssage> create() {
        return Behaviors.setup(Greeter::new);
    }

    private Greeter(ActorContext<IMesssage> context) {
        super(context);
        countStep = 0;
        len = 0;
        matrix = null;
        workF = null;
        workS = null;
        helper = null;
    }

    private void sendMatrixIfEnd() {
        if (countStep == 3) {
            getContext().getLog().info("Отправка готовой матрицы!");
            replyTo.tell(new ResBigTask(matrix));
        }
    }

    @Override
    public Receive<IMesssage> createReceive() {
        return newReceiveBuilder()
                .onMessage(BigTask.class, this::onBigTask)
                .onMessage(ResultTask.class, this::onResultTask)
                .onMessage(ResultReserveArray.class, this::onResultReserveArray)
                .onMessage(PostStop.class, this::onPostStop)
                .build();
    }

    private Behavior<IMesssage> onBigTask(BigTask message) {
        getContext().getLog().info("Начало работы!");
        //Parsing Task
        workF = message.getSendToF();
        workS = message.getSendToF();
        helper = message.getSendToThr();
        len = message.getLenth();
        matrix = message.getMatrix();
        replyTo = message.getReplyTo();

        //актор 1
        int numStr1 = ((len/2) / 2) * 2;
        double[][] task1 = new double[numStr1][];

        for (int i = 0; i < numStr1-i; ++i) {
            task1[i] = matrix[i].clone();
            task1[task1.length-1-i] = matrix[len-1-i].clone();
        }
        //#greeter-send-message
        message.getSendToF().tell(new Task(1, task1,
                getContext().getSelf()));

        //актор 2
        int numStr2 = ((len/2) - ((len/2) / 2)) * 2;
        double[][] task2 = new double[numStr2][];

        int indent = (len/2) / 2;
        for (int i = 0, j = indent; i < numStr2-i; i++, j++) {
            task2[i] = matrix[j].clone();
            task2[task2.length-1-i] = matrix[(len-1)-indent-i].clone();
        }
        //#greeter-send-message
        message.getSendToF().tell(new Task(2, task2,
                getContext().getSelf()));

        //актор 3
        if (len % 2 == 1) {
            double[] centreRow = matrix[len / 2];
            helper.tell(new ReverseArray(
                    centreRow, getContext().getSelf()));
        }

        return this;
    }

    private Behavior<IMesssage> onResultTask(ResultTask mess) {
        double[][] matr = mess.getMatrix();

        switch (mess.getWorkId()) {
            case 1:
                for (int i = 0; i < matr.length-i; ++i) {
                    matrix[i] = matr[i].clone();
                    matrix[len-1-i] = matr[matr.length-1-i].clone();
                }
                countStep++;
                sendMatrixIfEnd();

                workF.tell(new PostStop());
                break;
            case 2:
                int indent = (len/2) / 2;
                for (int i = 0, j = indent; i < matr.length-i; i++, j++) {
                    matrix[j] = matr[i].clone();
                    matrix[(len-1)-indent-i] = matr[matr.length-1-i].clone();
                }
                countStep++;
                sendMatrixIfEnd();

                workS.tell(new PostStop());
                break;
        }
        return this;
    }

    private Behavior<IMesssage> onResultReserveArray(ResultReserveArray com) {
        if (len % 2 == 1) {
            matrix[len / 2] = com.getArr().clone();
        }

        countStep++;
        sendMatrixIfEnd();

        helper.tell(new PostStop());
        getContext().getLog().info("Переворот строки завершен!");
        return this;
    }

    private Behavior<IMesssage> onPostStop(PostStop command) {
        getContext().getSystem().log().info("Greeter stopped");
        return Behaviors.stopped();
    }

}
// #greeter

