package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public abstract class Policy {
  protected int sumInsured;
  protected int basePremium;
  protected int discountPremium = basePremium;
  protected PolicyType type;

  public int getSumInsured() {
    return sumInsured;
  }

  public int getBasePremium() {
    return basePremium;
  }

  public int getDiscountPremium(int basePremium, double discount) {
    this.discountPremium = (int) (basePremium * (1 - discount));
    return discountPremium;
  }

  public abstract String getDetail();

  public abstract PolicyType getType();
}
