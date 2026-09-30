package me.herry.zombieAC.mutation;

import java.util.Random;

public enum MutationType {
    BOOMER, JOCKEY, JUMPER, NORMAL, PARASITE, SHOOTER, SPRINTER, TANKER;

    private static final Random RANDOM = new Random();

    // NORMAL 도 포함
    public static MutationType getRandom() {
        return values()[RANDOM.nextInt(values().length)];
    }
}