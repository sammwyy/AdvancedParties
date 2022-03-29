package dev._2lstudios.advancedparties.errors;

public class BadArgumentException extends Exception {
  private String arg;
  private String waiting;

  public BadArgumentException(String arg, String waiting) {
      super(arg + " isn't a valid " + waiting);
      this.arg = arg;
      this.waiting = waiting;
  }

  public String getArg() {
      return this.arg;
  }

  public String getWaiting() {
      return this.waiting;
  }
}