package com.example;

import akka.actor.typed.ActorSystem;
import com.example.Pack.IMesssage;
import com.example.Pack.SayHello;
import com.example.actors.GreeterMain;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.util.*;

public class AkkaQuickstart {

    public static void main(String[] args) {
        //#actor-system
        final ActorSystem<IMesssage> greeterMain =
                ActorSystem.create(GreeterMain.create(), "helloakka");
        //#actor-system

        //#main-send-messages
        greeterMain.tell(new SayHello("Charles"));
        //#main-send-messages

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ignored) {
        } finally {
            greeterMain.terminate();
        }
    }
}
