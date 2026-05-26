package me.herry.zombieAC;

import java.util.Random;

public enum MUTATION_TYPE {
    NORMAL, BOOMER, JOCKEY, SHOOTER, JUMPER, SPRINT, TANKER, PARASITE;

    private static final Random RANDOM = new Random();

    public static MUTATION_TYPE getRandom() {
        return values()[RANDOM.nextInt(values().length)];
    }
}