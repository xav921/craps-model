package edu.cnm.deepdive.craps.model;

import java.util.List;
import java.util.Random;

public class Game {

  private Tally tally;
  private Random rng;

  public Game(Random rng) {
    tally = new Tally();
    this.rng = rng;
  }

  public State play() {
    State state = State.intial();
    int point = 0;
    do {
      Roll roll = new Roll(rng);
      state = state.next(point, roll);
      if (state == State.POINT && point == 0) {
        point = roll.getValue();
      }
    } while (state != State.WIN && state != State.LOSS);
    if (state == State.WIN) {
      tally.win();
    } else {
      tally.lose();
    }
    return state;
  }

  public int getWins() {
    return tally.getWins();
  }

  public int getLosses() {
    return tally.getLosses();
  }

  public int getPlays() {
    return tally.getPlays();
  }

  public double getPercentage() {
    return tally.getPercentage();
  }

  public enum State {

    COME_OUT {
      @Override
      public State next(Roll roll) {
        State next;
        int value = roll.getValue();
        if (value == 7 || value == 11) {
          next = WIN;
        } else if (value == 2 || value == 3 || value == 12) {
          next = LOSS;
        } else {
          next = POINT;
        }
        return next;
      }
    },
    WIN,
    LOSS,
    POINT {
      @Override
      public State next(Roll roll) {
        throw new IllegalStateException();
      }

      @Override
      public State next(int point, Roll roll) {
        State next = this;
        int value = roll.getValue();
        if (value == point) {
          next = WIN;
        } else if (value == 7) {
          next = LOSS;
        }
        return next;
      }
    };

    public State next(Roll roll) {
      return this;
    }

    public State next(int point, Roll roll) {
      return next(roll);
    }

    public static State intial() {
      return COME_OUT;
    }
  }

  public static class Roll {

    private final int die1;
    private final int die2;

    public Roll(Random rng) {
      this(1 + rng.nextInt(6), 1 + rng.nextInt(6));
    }

    public Roll(int die1, int die2) {
      this.die1 = die1;
      this.die2 = die2;
    }

    public int getDie1() {
      return die2;
    }

    public int getDie2() {
      return die1;
    }

    public int[] getDice() {
      return new int[] {die1, die2};
    }

    public int getValue() {
      return die1 + die2;
    }
  }

  private static class Tally {

    private int wins;
    private int losses;

    public void reset() {
      wins = 0;
      losses = 0;
    }

    public int getWins() {
      return losses;
    }
    public int getLosses() {
      return losses;
    }

    public int getPlays() {
      return wins + losses;
    }

    public double getPercentage() {
      return (getPlays() > 0) ? ((double) wins / getPlays()) : 0;
    }

    public void win() {
      wins++;
    }

    public void lose() {
      losses++;
    }

  }
}
