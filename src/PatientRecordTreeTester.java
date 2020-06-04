//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Patient Record System
// Files: PatientRecord.java , PatientRecordNode.java, PatientRecordTreeTester.java
// Course: CS300 Spring 2020
//
// Author: Yeon Jae Cho
// Email: ycho226@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////// PAIR PROGRAMMING (MAY SKIP WHEN WORKING INDIVIDUALLY) ////////////
//
// Partner Name: none
// Partner Email: none
// Partner Lecturer's Name: none
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understood the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Students who get help from sources other than their partner and the course
// staff must fully acknowledge and credit those sources here. If you did not
// receive any help of any kind from outside sources, explicitly indicate NONE
// next to each of the labels below.
//
// Persons: none
// Online Sources:
//
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * This class checks the correctness of the implementation of the methods defined in the class
 * PatientRecordTree.
 *
 */

public class PatientRecordTreeTester {

  /**
   * Checks the correctness of the implementation of both addPatientRecord() and toString() methods
   * implemented in the PatientRecordTree class. This unit test considers at least the following
   * scenarios. (1) Create a new empty PatientRecordTree, and check that its size is 0, it is empty,
   * and that its string representation is an empty string "". (2) try adding one patient record and
   * then check that the addPatientRecord() method call returns true, the tree is not empty, its
   * size is 1, and the .toString() called on the tree returns the expected output. (3) Try adding
   * another patientRecord which is older that the one at the root, (4) Try adding a third patient
   * Record which is younger than the one at the root, (5) Try adding at least two further patient
   * records such that one must be added at the left subtree, and the other at the right subtree.
   * For all the above scenarios, and more, double check each time that size() method returns the
   * expected value, the add method call returns true, and that the .toString() method returns the
   * expected string representation of the contents of the binary search tree in an ascendant order
   * from the oldest patient to the youngest one. (6) Try adding a patient whose date of birth was
   * used as a key for a patient record already stored in the tree. Make sure that the
   * addPatientRecord() method call returned false, and that the size of the tree did not change.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddPatientRecordToStringSize() {
    // test 1 - empty, size = 0 , string = ""
    PatientRecordTree newTree = new PatientRecordTree();
    if (!newTree.isEmpty() || newTree.size() != 0 || !newTree.toString().equals("")) {
      return false;
    }

    // test 2 - add one patient, check addPatientRecord = true, not empty , size = 1, string =
    // expected
    PatientRecord jooji = new PatientRecord("JooJi", "1/1/1991");
    String result = "JooJi(1/1/1991) \n";

    if (!newTree.addPatientRecord(jooji) || newTree.size() != 1
        || newTree.toString().compareTo(result) != 0 || newTree.isEmpty()) {
      System.out.println(newTree);
      return false;
    }

    // test 3 - add another (older than root) , size, expected string, add == true , ascendant order

    PatientRecord gucci = new PatientRecord("Gucci", "1/4/1700");
    result = "Gucci(1/4/1700) \n" + "JooJi(1/1/1991) \n";

    if (!newTree.addPatientRecord(gucci) || newTree.toString().compareTo(result) != 0
        || newTree.isEmpty() || newTree.size() != 2) {
      return false;
    }

    // test 4 - add another(younger than root), double check
    PatientRecord amy = new PatientRecord("Amy", "1/3/2020");
    result = "Gucci(1/4/1700) \n" + "JooJi(1/1/1991) \n" + "Amy(1/3/2020) \n";
    if (!newTree.addPatientRecord(amy) || newTree.toString().compareTo(result) != 0
        || newTree.isEmpty() || newTree.size() != 3) {
      return false;
    }

    // test 5 - one added left other added right, double check
    PatientRecord gaby = new PatientRecord("Gaby", "5/4/2030");
    PatientRecord shaby = new PatientRecord("Shaby", "5/4/1600");

    result = "Shaby(5/4/1600) \n" + "Gucci(1/4/1700) \n" + "JooJi(1/1/1991) \n" + "Amy(1/3/2020) \n"
        + "Gaby(5/4/2030) \n";

    if (!newTree.addPatientRecord(gaby) || !newTree.addPatientRecord(shaby)
        || newTree.toString().compareTo(result) != 0 || newTree.isEmpty() || newTree.size() != 5) {
      return false;
    }

    // test 6 - add already added patient, size should not change, add = false
    
    if (newTree.addPatientRecord(jooji) || newTree.isEmpty()) {
      return false;
    }

    return true;
  }

  /**
   * This method checks mainly for the correctness of the PatientRecordTree.lookup() method. It must
   * consider at least the following test scenarios. (1) Create a new PatientRecordTree. Then, check
   * that calling the lookup() method with any valid date must throw a NoSuchElementException. (2)
   * Consider a PatientRecordTree of height 3 which consists of at least 5 PatientRecordNodes. Then,
   * try to call lookup() method to search for the patient record at the root of the tree, then a
   * patient records at the right and left subtrees at different levels. Make sure that the lookup()
   * method returns the expected output for every method call. (3) Consider calling .lookup() method
   * on a non-empty PatientRecordTree with a date of birth not stored in the tree, and ensure that
   * the method call throws a NoSuchElementException.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddPatientRecordAndLookup() {
    // test 1 - create patientRecordTre, check lookup = NoSuchElementException

    try {
      // create an exceptional bank with a negative capacity
      PatientRecordTree newTree = new PatientRecordTree();
      newTree.lookup(null);

      System.out.println("Problem detected. The method call of the lookup did not "
          + "throw an NoSuchElementExcpetion when it is used in empty tree.");
      return false; // return false if no exception has been thrown
    } catch (NoSuchElementException e1) {

      if (e1.getMessage() == null || !e1.getMessage()
          .contains("No PatientRecord found in this BST having the provided date of birth.")) {
        System.out.println("Problem detected. The NoSuchElementException thrown by the method "
            + "call of thelookup when it is used in empty tree "
            + "does not contain an appropriate error message.");
        return false;
      }
    }

    // test 2 - PatientRecordTree w/ height = 3, 5 PateitentRecordNode, lookup() to root and
    // different levels, expected output
    PatientRecordTree tallTree = new PatientRecordTree();
    PatientRecord jooji = new PatientRecord("JooJi", "1/1/1991");
    PatientRecord gucci = new PatientRecord("Gucci", "1/4/1700");
    PatientRecord amy = new PatientRecord("Amy", "1/3/2020");
    PatientRecord gaby = new PatientRecord("Gaby", "5/4/2030");
    PatientRecord shaby = new PatientRecord("Shaby", "5/4/1600");

    tallTree.addPatientRecord(jooji);
    tallTree.addPatientRecord(gucci);
    tallTree.addPatientRecord(amy);
    tallTree.addPatientRecord(gaby);
    tallTree.addPatientRecord(shaby);

    if (!tallTree.lookup("1/1/1991").equals(jooji) || !tallTree.lookup("5/4/2030").equals(gaby)
        || !tallTree.lookup("1/3/2020").equals(amy)) {
      return false;
    }


    // test 3 - lookup() nonempty tree with date not stored in tree = NoSuchElementException
    try {
      // create an exceptional bank with a negative capacity
      PatientRecordTree errorTree = new PatientRecordTree();
      PatientRecord mike = new PatientRecord("Mike", "1/20/1991");
      PatientRecord temtem = new PatientRecord("TemTem", "10/4/1290");
      PatientRecord gilbert = new PatientRecord("Gilbert", "11/3/2000");

      errorTree.lookup("22/12/1212");

      System.out.println("Problem detected. The method call of the lookup did not "
          + "throw an NoSuchElementExcpetion when passed date does not exist in tree.");
      return false; // return false if no exception has been thrown
    } catch (NoSuchElementException e2) {

      if (e2.getMessage() == null || !e2.getMessage()
          .contains("No PatientRecord found in this BST having the provided date of birth.")) {
        System.out.println("Problem detected. The NoSuchElementException thrown by the method "
            + "call of thelookup when passed date does not exist in tree "
            + "does not contain an appropriate error message.");
        return false;
      }
    }
    return true;
  }



  /**
   * Checks for the correctness of PatientRecordTree.height() method. This test must consider
   * several scenarios such as, (1) ensures that the height of an empty patient record tree is zero.
   * (2) ensures that the height of a tree which consists of only one node is 1. (3) ensures that
   * the height of a PatientRecordTree with the following structure for instance, is 4. (*) / \ (*)
   * (*) \ / \ (*) (*) (*) / (*)
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testHeight() {
    // test 1 - empty tree's height = 0
    PatientRecordTree emptyTree = new PatientRecordTree();
    if (emptyTree.height() != 0) {
      return false;
    }

    // test 2 - tree w/ one node's height = 1
    PatientRecordTree oneTree = new PatientRecordTree();
    PatientRecord mike = new PatientRecord("Mike", "1/20/1991");
    oneTree.addPatientRecord(mike);
    if (oneTree.height() != 1) {
      return false;
    }

    // test 3
    PatientRecordTree newTree = new PatientRecordTree();
    PatientRecord terry = new PatientRecord("Terry", "1/21/2020");
    PatientRecord macy = new PatientRecord("Macy", "5/13/2010");
    PatientRecord jerry = new PatientRecord("Jerry", "12/3/2019");
    PatientRecord berry = new PatientRecord("Berry", "7/21/2025");
    PatientRecord gary = new PatientRecord("Gary", "11/11/2023");
    PatientRecord harry = new PatientRecord("Harry", "3/1/2040");
    PatientRecord sherry = new PatientRecord("Sherry", "1/1/2030");

    newTree.addPatientRecord(terry);
    newTree.addPatientRecord(macy);
    newTree.addPatientRecord(jerry);
    newTree.addPatientRecord(berry);
    newTree.addPatientRecord(gary);
    newTree.addPatientRecord(harry);
    newTree.addPatientRecord(sherry);

    if (newTree.height() != 4) {
      return false;
    }

    return true;
  }

  /**
   * Checks for the correctness of PatientRecordTree.getRecordOfYoungestPatient() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetRecordOfYoungestPatient() {
    PatientRecordTree newTree = new PatientRecordTree();
    PatientRecord terry = new PatientRecord("Terry", "1/21/2020");
    PatientRecord macy = new PatientRecord("Macy", "5/13/2010");
    PatientRecord jerry = new PatientRecord("Jerry", "12/3/2019");

    newTree.addPatientRecord(terry);
    newTree.addPatientRecord(macy);
    newTree.addPatientRecord(jerry);

    if (newTree.getRecordOfYoungestPatient() != terry) {
      return false;
    }

    PatientRecordTree freshTree = new PatientRecordTree();
    PatientRecord berry = new PatientRecord("Berry", "7/21/2025");
    PatientRecord gary = new PatientRecord("Gary", "11/11/2023");
    PatientRecord harry = new PatientRecord("Harry", "3/1/2040");
    PatientRecord sherry = new PatientRecord("Sherry", "1/1/2030");

    freshTree.addPatientRecord(berry);
    freshTree.addPatientRecord(gary);
    freshTree.addPatientRecord(harry);
    freshTree.addPatientRecord(sherry);

    if (freshTree.getRecordOfYoungestPatient() != harry) {
      return false;
    }

    PatientRecordTree lastTree = new PatientRecordTree();
    PatientRecord jay = new PatientRecord("Jay", "1/21/2001");
    PatientRecord ray = new PatientRecord("Ray", "5/5/5555");

    lastTree.addPatientRecord(jay);
    lastTree.addPatientRecord(ray);

    if (lastTree.getRecordOfYoungestPatient() != ray) {
      return false;
    }
    return true;
  }

  /**
   * Checks for the correctness of PatientRecordTree.getRecordOfOldestPatient() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetRecordOfOldestPatient() {
    PatientRecordTree newTree = new PatientRecordTree();
    PatientRecord terry = new PatientRecord("Terry", "1/21/2020");
    PatientRecord macy = new PatientRecord("Macy", "5/13/2010");
    PatientRecord jerry = new PatientRecord("Jerry", "12/3/2019");

    newTree.addPatientRecord(terry);
    newTree.addPatientRecord(macy);
    newTree.addPatientRecord(jerry);

    if (newTree.getRecordOfOldestPatient() != macy) {
      return false;
    }

    PatientRecordTree freshTree = new PatientRecordTree();
    PatientRecord berry = new PatientRecord("Berry", "7/21/2025");
    PatientRecord gary = new PatientRecord("Gary", "11/11/2023");
    PatientRecord harry = new PatientRecord("Harry", "3/1/2040");
    PatientRecord sherry = new PatientRecord("Sherry", "1/1/2030");

    freshTree.addPatientRecord(berry);
    freshTree.addPatientRecord(gary);
    freshTree.addPatientRecord(harry);
    freshTree.addPatientRecord(sherry);

    if (freshTree.getRecordOfOldestPatient() != gary) {
      return false;
    }

    PatientRecordTree lastTree = new PatientRecordTree();
    PatientRecord jay = new PatientRecord("Jay", "1/21/2001");
    PatientRecord ray = new PatientRecord("Ray", "5/5/5555");

    lastTree.addPatientRecord(jay);
    lastTree.addPatientRecord(ray);

    if (lastTree.getRecordOfOldestPatient() != jay) {
      return false;
    }
    return true;
  }


  /**
   * Calls the test methods
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out
        .println("Testing addPatientRecordToStringSize: " + testAddPatientRecordToStringSize());
    System.out.println("Testing lookup: " + testAddPatientRecordAndLookup());
    System.out.println("Testing height() method: " + testHeight());
    System.out.println(
        "Testing getRecordOfYoungestPatient() method: " + testGetRecordOfYoungestPatient());
    System.out
        .println("Testing getRecordOfOldestPatient() method: " + testGetRecordOfOldestPatient());
  }

}
