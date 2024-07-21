package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class Life extends Policy {

  public Life(PolicyType type, int sumInsured, int age) {
    this.sumInsured = sumInsured;
    this.basePremium = getPremium(age);
  }

  public int getPremium(int age) {
    basePremium = (int) (sumInsured * (1 + age / 100.0) / 100);
    return basePremium;
  }

  @Override
  public PolicyType getType() {
    return PolicyType.LIFE;
  }

  @Override
  public String getDetail() {
    return null;
  }
}
