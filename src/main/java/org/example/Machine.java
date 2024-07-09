/*
    6510405806
    Weeranut Chayakul
*/

package org.example;

import java.util.ArrayList;

public class Machine {
    private MachineStatus status;
    private final ArrayList<Gumball> gumballs;
    private final ArrayList<Money> totalQuarters;
    private Money currentMoney;

    public enum MachineStatus {
        NO_QUARTER, HAS_QUARTER, GUMBALL_SOLD, OUT_OF_GUMBALL;
    }

    public Machine(ArrayList<Gumball> gumballs) {
        if (gumballs.size() == 0) {
            this.status = MachineStatus.OUT_OF_GUMBALL;
        } else {
            this.status = MachineStatus.NO_QUARTER;
        }

        this.gumballs = gumballs;
        totalQuarters = new ArrayList<>();
    }

    public void insertGumBalls(ArrayList<Gumball> gumballs) {
        this.gumballs.addAll(gumballs);
        if (status == MachineStatus.OUT_OF_GUMBALL) {
            status = MachineStatus.NO_QUARTER;
        }
    }

    public void insertsQuarter(Money quarter) throws InvalidQuarterInput, IllegalStateException {
        if (stateCheck(status, MachineStatus.NO_QUARTER)) {
            if (quarter.getValue() == 0.25) {
                status = MachineStatus.HAS_QUARTER;
                currentMoney = quarter;
            } else {
                throw new InvalidQuarterInput("You inserted the wrong coin!");
            }
        }
    }

    public double ejectsQuarter() throws IllegalStateException {
        if (stateCheck(status, MachineStatus.HAS_QUARTER)) {
            status = MachineStatus.NO_QUARTER;
            double money = currentMoney.getValue();
            currentMoney = null;
            return money;
        }
        return 0;
    }

    public Gumball turnsCrank() throws IllegalStateException {
        if (stateCheck(status, MachineStatus.HAS_QUARTER)) {
            status = MachineStatus.GUMBALL_SOLD;
            totalQuarters.add(currentMoney);
            currentMoney = null;
            return gumballs.remove(0);
        }
        return null;
    }

    public boolean gumBallSold() throws IllegalStateException {
        if (stateCheck(status, MachineStatus.GUMBALL_SOLD)) {
            if (gumballs.size() == 0) {
                status = MachineStatus.OUT_OF_GUMBALL;
                return true;
            }
        }
        status = MachineStatus.NO_QUARTER;
        return false;
    }

    private boolean stateCheck(MachineStatus currentState, MachineStatus expected) throws IllegalStateException {
        if (currentState == expected) {
            return true;
        } else {
            throw new IllegalStateException("Cannot change state from " + currentState + " to " + expected + ".");
        }
    }

    public double getMoneyFromMachine() {
        double money = 0;
        for (Money each : totalQuarters) {
            money += each.getValue();
        }
        return money;
    }

    public String getCurrentState() {
        return switch (status) {
            case NO_QUARTER -> "Machine is waiting for quarter";
            case HAS_QUARTER -> "Machine still has quarter";
            case GUMBALL_SOLD -> "There is gumball in the tray";
            case OUT_OF_GUMBALL -> "Machine is sold out";
        };
    }

    @Override
    public String toString() {
        return "Mighty Gumball, Inc.\nJava-enabled Standing Gumball Model #2004\n" +
                "Inventory: " + gumballs.size() + " gumball(s)\n" +
                "Machine Stored Money: $" + getMoneyFromMachine() + "\n" +
                "Current State: " + getCurrentState() + "\n";
    }
}
