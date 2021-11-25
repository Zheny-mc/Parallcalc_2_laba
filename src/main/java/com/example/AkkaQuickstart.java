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


/*
        int value = 1;
        int l = 9;
        double[][] result = new double[l][l];
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result.length; col++) {
                result[row][col] = value++;
            }
        }

        System.out.println("\n========================================\n");

        int len = result.length;
        // поворот 1
        //поток 1
        int numStr1 = ((len/2) / 2) * 2;
        double[][] res1 = new double[numStr1][];

        for (int i = 0; i < numStr1-i; ++i) {
            res1[i] = result[i].clone();
            res1[res1.length-1-i] = result[len-1-i].clone();
        }

        for (int row = 0; row < res1.length-row; row++) {  // row строки
            for (int col = 0; col < len; col++) {
                // обмен местами елементов
                Double temp = res1[row][col];
                res1[row][col] = res1[res1.length-row-1][len-col-1];
                res1[res1.length-row-1][len-col-1] = temp;
            }
        }

        // поворот 2
        //поток 2
        int numStr2 = ((len/2) - ((len/2) / 2)) * 2;
        double[][] res2 = new double[numStr2][];

        int indent = (len/2) / 2;
        for (int i = 0, j = indent; i < numStr2-i; i++, j++) {
            res2[i] = result[j].clone();
            res2[res2.length-1-i] = result[(len-1)-indent-i].clone();
        }

        for (int row = 0; row < res2.length-row; row++) {  // row строки
            for (int col = 0; col < len; col++) {
                // обмен местами елементов
                Double temp = res2[row][col];
                res2[row][col] = res2[res2.length-row-1][len-col-1];
                res2[res2.length-row-1][len-col-1] = temp;
            }
        }

        System.out.println("\n========================================\n");

        int row = len / 2;
        double[] centreRow = result[row].clone();

        if (len % 2 == 1) {

            for (int i = len-1, j = 0; i >= len/2 ; i--,j++) {
                double temp = centreRow[j];
                centreRow[j] = centreRow[i];
                centreRow[i] = temp;
            }
        }

        System.out.println("\n========================================\n");

        int l2 = 9;
        double[][] result2 = new double[l][l];

        for (int i = 0; i < res1.length-i; ++i) {
            result2[i] = res1[i].clone();
            result2[len-1-i] = res1[res1.length-1-i].clone();
        }

        int indent2 = (len/2) / 2;
        for (int i = 0, j = indent2; i < res2.length-i; i++, j++) {
            result2[j] = res2[i].clone();
            result2[(len-1)-indent2-i] = res2[res2.length-1-i].clone();
        }
        if (len % 2 == 1) {
            result2[len / 2] = centreRow;
        }

        System.out.println("\n========================================\n");
        */

    }
}
