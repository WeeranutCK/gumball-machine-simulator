/*
    6510405806
    Weeranut Chayakul
*/

package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static ArrayList<Gumball> generateRandomGumball(int n) {
        ArrayList<Gumball> gumballs = new ArrayList<>();
        String[] color = {"white", "yellow", "red", "green", "blue", "orange"};

        for (int i = 0; i < n; i++) {
            Random random = new Random();
            int index = random.nextInt(color.length);
            Gumball gumBall = new Gumball(color[index]);
            gumballs.add(gumBall);
        }
        return gumballs;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input number of gumballs: ");
        int n =  Integer.parseInt(bufferedReader.readLine());

        ArrayList<Gumball> gumballs = generateRandomGumball(n);
        Machine machine = new Machine(gumballs);

        while (true) {
            System.out.println("\n" + machine);
            System.out.print("Input state [A]add gumball, [I]nsert coin, [E]ject, [T]urn Crank, [D]ispense Gumball, [Q]uit: ");
            String input = bufferedReader.readLine();

            switch (input.toUpperCase()) {
                case "A" -> {
                    System.out.print("Input number of gumballs to insert: ");
                    n =  Integer.parseInt(bufferedReader.readLine());
                    gumballs = generateRandomGumball(n);
                    machine.insertGumBalls(gumballs);
                }
                case "I" -> {
                    try {
                        System.out.print("Insert money amount: ");
                        double amount = Double.parseDouble(bufferedReader.readLine());
                        Money quarter = new Money(amount);
                        machine.insertsQuarter(quarter);
                        System.out.println("You inserted a quarter");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "E" -> {
                    try {
                        double money = machine.ejectsQuarter();
                        System.out.println("Money amount: " + money + " was returned!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "T" -> {
                    try {
                        System.out.println("You turned...");
                        Gumball gumBall = machine.turnsCrank();
                        if (gumBall != null) {
                            System.out.println("A gumball comes rolling out the slot");
                            System.out.println("The gumball color is: " + gumBall.getColor());
                        } else {
                            System.out.println("You turned, but there are no gumballs");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "D" -> {
                    try {
                        if (machine.gumBallSold()) {
                            System.out.println("Oops, out of gumballs!");
                            System.out.println("You can't insert a quarter, the machine is sold out");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "Q" -> {
                    return;
                }
            }
        }
    }
}
