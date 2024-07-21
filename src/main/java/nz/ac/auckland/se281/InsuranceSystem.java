package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {
  private ArrayList<Person> people = new ArrayList<Person>();

  private Person loaded; // Stores the person currently being loaded

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).
  }

  public void printDatabase() {
    // The length of list names is the number of profiles in the database
    String count = Integer.toString(people.size()); // Number of profiles
    String plural; // Plural for header and policy
    String sign; // Sign for header
    String num; // Index of profile
    String name; // Name of person
    String age; // Age of person
    String prefix; // Prefix for loaded person

    // Apply appropriate plural and sign for header
    if (people.size() == 0) {
      plural = "s";
      sign = ".";
    } else {
      sign = ":";
      if (people.size() == 1) {
        plural = "";
      } else {
        plural = "s";
      }
    }

    // Print opening, count of profiles
    MessageCli.PRINT_DB_POLICY_COUNT.printMessage(count, plural, sign);

    // Loop through the arraylist to get every person in people
    for (int i = 0; i < people.size(); i++) {
      Person currentPerson = people.get(i);

      num = Integer.toString(i + 1);

      // Get user's name
      name = currentPerson.getName();

      // Get user's age
      age = currentPerson.getAge();

      // Put *** in front of loaded person
      if (currentPerson == loaded) {
        prefix = "*** ";
      } else {
        prefix = "";
      }

      // Apply appropriate plural for policies
      if (currentPerson.getNumberOfPolicies() == 1) {
        plural = "y";
      } else {
        plural = "ies";
      }
      MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
          prefix,
          num,
          name,
          age,
          Integer.toString(currentPerson.getNumberOfPolicies()),
          plural,
          Integer.toString(getTotal(currentPerson)));

      // Loop through the policies of the person i
      for (int j = 0; j < currentPerson.getNumberOfPolicies(); j++) {

        Policy currentPolicy = currentPerson.getPolicies().get(j);

        // Print the details of the policy
        switch (currentPolicy.getType()) {
          case LIFE:
            // Details for LIFE
            MessageCli.PRINT_DB_LIFE_POLICY.printMessage(
                Integer.toString(currentPolicy.getSumInsured()),
                Integer.toString(currentPolicy.getBasePremium()),
                Integer.toString(
                    currentPolicy.getDiscountPremium(
                        currentPolicy.getBasePremium(), currentPerson.getDiscount())));
            break;

          case HOME:
            // Details for HOME
            MessageCli.PRINT_DB_HOME_POLICY.printMessage(
                currentPolicy.getDetail(),
                Integer.toString(currentPolicy.getSumInsured()),
                Integer.toString(currentPolicy.getBasePremium()),
                Integer.toString(
                    currentPolicy.getDiscountPremium(
                        currentPolicy.getBasePremium(), currentPerson.getDiscount())));
            break;

          case CAR:
            // Details for CAR
            MessageCli.PRINT_DB_CAR_POLICY.printMessage(
                currentPolicy.getDetail(),
                Integer.toString(currentPolicy.getSumInsured()),
                Integer.toString(currentPolicy.getBasePremium()),
                Integer.toString(
                    currentPolicy.getDiscountPremium(
                        currentPolicy.getBasePremium(), currentPerson.getDiscount())));
            break;
        }
      }
    }
  }

  public void createNewProfile(String userName, String age) {

    userName = formatName(userName);

    if (loaded == null) {
      // No profile loaded

      // In case of age typed in as String
      try {
        // Convert age to double to so that no input is lost
        double ageDouble = Double.parseDouble(age);
        if ((ageDouble % 1) != 0 || ageDouble < 0) {
          // Check if age is integer and non-negative
          MessageCli.INVALID_AGE.printMessage(age, userName);
          return;
        }
      } catch (Exception e) {
        // In case of the age input is text
        MessageCli.INVALID_AGE.printMessage(age, userName);
        return;
      }

      // Check if user already exists
      boolean duplicate = false;

      for (int i = 0; i < people.size(); i++) {
        if (people.get(i).getName().equals(userName)) {
          duplicate = true;
        }
      }

      if (userName.length() < 3) {
        // Check if the name is too short
        MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(userName);
        return;
      } else if (duplicate) {
        // Check if the name is unique
        MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(userName);
        return;
      } else {
        // Append the user to lists
        Person p = new Person(userName, age);
        people.add(p);

        MessageCli.PROFILE_CREATED.printMessage(userName, age);
      }
    } else {
      // Profile loaded
      MessageCli.CANNOT_CREATE_WHILE_LOADED.printMessage(loaded.getName());
    }
  }

  public void loadProfile(String userName) {

    userName = formatName(userName);

    boolean found = false;

    for (int i = 0; i < people.size(); i++) {
      // Check if the user exists
      if (people.get(i).getName().equals(userName)) {
        found = true;
        loaded = people.get(i); // Set matching user to loaded
        break;
      }
    }

    if (found == true) {
      MessageCli.PROFILE_LOADED.printMessage(userName);
    } else {
      MessageCli.NO_PROFILE_FOUND_TO_LOAD.printMessage(userName);
    }
  }

  public void unloadProfile() {
    if (loaded != null) {
      // Profile is loaded
      MessageCli.PROFILE_UNLOADED.printMessage(loaded.getName());
      loaded = null; // Reset loaded
    } else {
      // Profile is not loaded
      MessageCli.NO_PROFILE_LOADED.printMessage();
    }
  }

  public void deleteProfile(String userName) {

    userName = formatName(userName);

    if (loaded != null && loaded.getName().equals(userName)) {
      // Profile is currently loaded and print error message
      MessageCli.CANNOT_DELETE_PROFILE_WHILE_LOADED.printMessage(userName);
      return;
    }

    // Profile is not loaded and carry on deleting the profile
    boolean found = false;

    for (int i = 0; i < people.size(); i++) {
      // Check if the user exists
      if (people.get(i).getName().equals(userName)) {
        found = true;
        people.remove(i); // Remove the user from the list
        break;
      }
    }

    if (found == true) {
      MessageCli.PROFILE_DELETED.printMessage(userName);
    } else {
      MessageCli.NO_PROFILE_FOUND_TO_DELETE.printMessage(userName);
    }
  }

  public void createPolicy(PolicyType type, String[] options) {
    // Check if profile is loaded
    if (loaded != null) {
      // Check for the type of policy
      switch (type) {
        case HOME:
          // Create a home policy
          {
            Home homePolicy =
                new Home(
                    type,
                    Integer.parseInt(options[0]),
                    options[1],
                    convertYesNo(options[options.length - 1]));

            MessageCli.NEW_POLICY_CREATED.printMessage(
                type.toString().toLowerCase(), loaded.getName());
            // Add the policy to the user
            loaded.addPolicy(homePolicy);
            break;
          }

        case CAR:
          // Create a car policy
          {
            Car carPolicy =
                new Car(
                    type,
                    Integer.parseInt(options[0]),
                    options[1],
                    options[2],
                    convertYesNo(options[options.length - 1]),
                    Integer.parseInt(loaded.getAge()));

            MessageCli.NEW_POLICY_CREATED.printMessage(
                type.toString().toLowerCase(), loaded.getName());

            // Add the policy to the user
            loaded.addPolicy(carPolicy);
            break;
          }

        case LIFE:
          // Create a life policy
          {
            if (Integer.parseInt(loaded.getAge()) > 100) {
              // Check if the user is over the age limit
              MessageCli.OVER_AGE_LIMIT_LIFE_POLICY.printMessage(loaded.getName());
            } else if (loaded.checkLifePolicy()) {
              // Check if the user already has a life policy
              MessageCli.ALREADY_HAS_LIFE_POLICY.printMessage(loaded.getName());
            } else {
              Life lifePolicy =
                  new Life(type, Integer.parseInt(options[0]), Integer.parseInt(loaded.getAge()));

              MessageCli.NEW_POLICY_CREATED.printMessage(
                  type.toString().toLowerCase(), loaded.getName());
              // Add the policy to the user
              loaded.addPolicy(lifePolicy);
              loaded.updateLifePolicy();
            }
            break;
          }
      }
    } else {
      // No profile loaded
      MessageCli.NO_PROFILE_FOUND_TO_CREATE_POLICY.printMessage();
    }
  }

  private String formatName(String userName) {
    // Make the first letter upper case and the rest of the name lower case
    return userName.substring(0, 1).toUpperCase()
        + userName.substring(1, userName.length()).toLowerCase();
  }

  private int getTotal(Person p) {
    // Calculate the total discounted premium
    int total = 0;
    for (int i = 0; i < p.getPolicies().size(); i++) {
      total +=
          p.getPolicies()
              .get(i)
              .getDiscountPremium(p.getPolicies().get(i).basePremium, p.getDiscount());
    }

    return total;
  }

  private Boolean convertYesNo(String input) {
    // Convert yes/no to boolean
    if (input.toLowerCase().contains("y")) {
      return true;
    } else if (input.toLowerCase().contains("n")) {
      return false;
    } else {
      return null;
    }
  }
}
