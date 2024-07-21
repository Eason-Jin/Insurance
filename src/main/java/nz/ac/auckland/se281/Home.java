package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class Home extends Policy {
  private String address;
  private Boolean rental;

  public Home(PolicyType type, int sumInsured, String address, Boolean rental) {
    this.sumInsured = sumInsured;
    this.address = address;
    this.rental = rental;
    this.basePremium = getPremium(this.rental);
  }

  public int getPremium(Boolean rental) {
    // Check if the house is rented
    if (rental) {
      basePremium = (int) (sumInsured * 0.02);
    } else {
      basePremium = (int) (sumInsured * 0.01);
    }

    return basePremium;
  }

  @Override
  public String getDetail() {
    return address;
  }

  @Override
  public PolicyType getType() {
    return PolicyType.HOME;
  }
}
