package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Person {

  private String name;
  private String age;
  private ArrayList<Policy> policies = new ArrayList<Policy>();
  private Boolean hasLifePolicy = false;
  private double discount;

  public Person(String name, String age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public String getAge() {
    return age;
  }

  public void addPolicy(Policy policy) {
    policies.add(policy);
  }

  public void updateLifePolicy() {
    // Set the person to have a life policy
    hasLifePolicy = true;
  }

  public Boolean checkLifePolicy() {
    return hasLifePolicy;
  }

  public double getDiscount() {
    if (policies.size() == 2) {
      discount = 0.1;
    } else if (policies.size() >= 3) {
      discount = 0.2;
    }
    return discount;
  }

  public ArrayList<Policy> getPolicies() {
    return policies;
  }

  public int getNumberOfPolicies() {
    return policies.size();
  }
}
