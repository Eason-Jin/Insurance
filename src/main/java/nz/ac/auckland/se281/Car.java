package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class Car extends Policy {
  private String makeAndModel;
  private String rego;
  private Boolean breakdownCover;

  public Car(
      PolicyType type,
      int sumInsured,
      String makeAndModel,
      String rego,
      Boolean breakdownCover,
      int age) {
    this.sumInsured = sumInsured;
    this.makeAndModel = makeAndModel;
    this.rego = rego;
    this.breakdownCover = breakdownCover;
    this.basePremium = getPremium(age);
  }

  public int getPremium(int age) {
    // Check for client's age
    if (age < 25) {
      basePremium = (int) (sumInsured * 0.15);
    } else {
      basePremium = (int) (sumInsured * 0.1);
    }

    // Check for breakdown cover
    if (breakdownCover) {
      basePremium += 80;
    }

    return basePremium;
  }

  @Override
  public String getDetail() {
    return makeAndModel;
  }

  @Override
  public PolicyType getType() {
    return PolicyType.CAR;
  }
}
