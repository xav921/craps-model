package edu.cnm.deepdive.craps.controller;

import edu.cnm.deepdive.craps.model.Game;
import edu.cnm.deepdive.craps.model.Game.Roll;
import edu.cnm.deepdive.craps.model.Game.Round;
import java.security.SecureRandom;
import java.util.Random;

public class Main {

  public static void main(String[] args) {
    int rounds = (args.length > 0) ? Integer.parseInt(args[0]) : 1;
    play(rounds);
  }

  private static void play(int rounds) {
    Random rng = new SecureRandom();
    Game game = new Game(rng);
    Round round = null;
    for (int i  = 0; i < rounds; i++) {
      round = game.play();
    }
    if (rounds == 1) {
      System.out.println("Rolls");
      for (Roll roll : round.getRolls()) {
        System.out.printf("\t%s%n", roll);
      }
      System.out.printf("Outcome: %s%n", round.getState());
    } else {
      System.out.printf("Total plays: %,d%n", game.getPlays());
      System.out.printf("Wins: %,d%n", game.getWins());
      System.out.printf("Winning Percentage: %.2f%%%n", 100 * game.getPercentage());
    }
  }
 }
