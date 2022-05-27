package dev._2lstudios.advancedparties.tasks;

import dev._2lstudios.advancedparties.AdvancedParties;

public class RedisPingTask implements Runnable {
    private AdvancedParties advancedParties;

    public RedisPingTask(AdvancedParties advancedParties) {
        this.advancedParties = advancedParties;
    }

    @Override
    public void run() {
        advancedParties.getPubSub().ping();
    }
}
