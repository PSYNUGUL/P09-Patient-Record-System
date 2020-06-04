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
 * This class implements a binary search tree (BST) which stores a set of patient records. The left
 * subtree contains the patient records of all the patients who are older than the patient who's
 * PatientRecord is stored at a parent node. The right subtree contains the patient records of all
 * the patients who are younger than the patient who's PatientRecord is stored at a parent node.
 *
 */
public class PatientRecordTree {
  private PatientRecordNode root; // root of this binary search tree
  private int size; // total number of patient records stored in this tree.

  /**
   * Checks whether this binary search tree (BST) is empty
   * 
   * @return true if this PatientRecordTree is empty, false otherwise
   */
  public boolean isEmpty() {
    if (size() == 0) {
      return true;
    }
    return false; // a default return statement added to let this code compile.
  }

  /**
   * Returns the number of patient records stored in this BST.
   * 
   * @return the size of this PatientRecordTree
   */
  public int size() {
    return size; // remove this statement. A default return statement added to let this code
                 // compile.
  }

  /**
   * Recursive helper method to add a new PatientRecord to a PatientRecordTree rooted at current.
   * 
   * @param current   The "root" of the subtree we are inserting newRecord into.
   * @param newRecord The PatientRecord to be added to a BST rooted at current.
   * @return true if the newRecord was successfully added to this PatientRecordTree, false otherwise
   */
  public static boolean addPatientRecordHelper(PatientRecord newRecord, PatientRecordNode current) {
    if (current == null) {
      current = new PatientRecordNode(newRecord);
      return true;
    } else if (newRecord.compareTo(current.getPatientRecord()) > 0) {
      if (current.getRightChild() == null) {
        current.setRightChild(new PatientRecordNode(newRecord));
        return true;
      } else {
        addPatientRecordHelper(newRecord, current.getRightChild());
        return true;
      }
    } else if (newRecord.compareTo(current.getPatientRecord()) < 0) {
      if (current.getLeftChild() == null) {
        current.setLeftChild(new PatientRecordNode(newRecord));
        return true;
      } else {
        addPatientRecordHelper(newRecord, current.getLeftChild());
        return true;
      }
    }
    return false;
  }


  /**
   * Adds a new PatientRecord to this PatientRecordTree
   * 
   * @param newRecord a new PatientRecord to add to this BST.
   * @return true if the newRecord was successfully added to this BST, and returns false if there is
   *         a match with this PatientRecord already already stored in this BST.
   */
  public boolean addPatientRecord(PatientRecord newRecord) {
    if (isEmpty()) { // Add newRecord to an empty PatientRecordTree
      root = new PatientRecordNode(newRecord);
      size++;
      return true;

    }
    if (newRecord == root.getPatientRecord()) {
      return false;
    }

    size++;
    return addPatientRecordHelper(newRecord, root);
  }


  /**
   * Recursive helper method which returns a String representation of the BST rooted at current. An
   * example of the String representation of the contents of a PatientRecordTree is provided in the
   * description of the above toString() method.
   * 
   * @param current reference to the current PatientRecordNode within this BST.
   * @return a String representation of all the PatientRecords stored in the sub-tree
   *         PatientRecordTree rooted at current in increasing order with respect to the patients
   *         dates of birth. Returns an empty String "" if current is null.
   */
  public static String toStringHelper(PatientRecordNode current) { // LVR(In Order Traversal
    String recordString = "";
    if (current == null) {
      return "";
    } else {
      recordString = toStringHelper(current.getLeftChild()) + current.getPatientRecord().getName()
          + "(" + current.getPatientRecord().getStringDateOfBirth() + ") \n"
          + toStringHelper(current.getRightChild());
    }

    return recordString;
  }


  /**
   * Returns a String representation of all the PatientRecords stored within this BST in the
   * increasing order, separated by a newline "\n". For instance: "Sarah(1/2/1935)" + "\n" +
   * "George(5/27/1943)" + "\n" + "Adam(8/12/1972)" + "\n" + "Norah(11/23/1985)" + "\n" +
   * "William(6/4/1998)" + "\n" + "Nancy(9/12/2003)" + "\n" + "Sam(4/20/2019)" + "\n"
   * 
   * @return a String representation of all the PatientRecords stored within this BST sorted in an
   *         increasing order with respect to the dates of birth of the patients (i.e. from the
   *         oldest patient to the youngest patient). Returns an empty string "" if this BST is
   *         empty.
   */
  public String toString() {
    return toStringHelper(root);
  }

  /**
   * Search for a patient record (PatientRecord) given the date of birth as lookup key.
   * 
   * @param date a String representation of the date of birth of a patient in the format mm/dd/yyyy
   * @return the PatientRecord of the patient born on date.
   * @throws a NoSuchElementException with a descriptive error message if there is no PatientRecord
   *           found in this BST having the provided date of birth
   */
  public PatientRecord lookup(String date) {
    if (date == null) {
      throw new NoSuchElementException(
          "No PatientRecord found in this BST having the provided date of birth.");
    }
    PatientRecord findRecord = new PatientRecord("", date);
    return this.lookupHelper(findRecord, root);
  }

  /**
   * Recursive helper method to lookup a PatientRecord given a reference PatientRecord with the same
   * date of birth in the subtree rooted at current
   * 
   * @param findRecord a reference to a PatientRecord target we are lookup for a match in the BST
   *                   rooted at current.
   * @param current    "root" of the subtree we are looking for a match to findRecord within it.
   * @return reference to the PatientRecord stored stored in this BST which matches findRecord.
   * @throws NoSuchElementException with a descriptive error message if there is no patient record
   *                                whose date of birth matches date, stored in this BST.
   */
  private PatientRecord lookupHelper(PatientRecord findRecord, PatientRecordNode current) {
    if (current != null) {
      if (findRecord.compareTo(current.getPatientRecord()) == 0) {
        return current.getPatientRecord();
      }
      if (findRecord.compareTo(current.getPatientRecord()) > 0) {
        if (current.getRightChild() != null) {
          return lookupHelper(findRecord, current.getRightChild());
        }
      }
      if (findRecord.compareTo(current.getPatientRecord()) < 0) {
        if (current.getLeftChild() != null) {
          return lookupHelper(findRecord, current.getLeftChild());
        }

      }

    }
    throw new NoSuchElementException(
        "No PatientRecord found in this BST having the provided date of birth.");
  }


  /**
   * Computes and returns the height of this BST, counting the number of nodes (PatientRecordNodes)
   * from root to the deepest leaf.
   * 
   * @return the height of this Binary Search Tree
   */
  public int height() {
    return heightHelper(root);
  }

  /**
   * Recursive helper method that computes the height of the subtree rooted at current
   * 
   * @param current pointer to the current PatientRecordNode within a PatientRecordTree
   * @return height of the subtree rooted at current, counting the number of PatientRecordNodes
   */
  public static int heightHelper(PatientRecordNode current) {
    int height = 0;
    if (current == null) {
      return height;
    } else {
      int left = heightHelper(current.getLeftChild());
      int right = heightHelper(current.getRightChild());
      height = Math.max(left, right) + 1;
    }
    return height;
  }


  /**
   * Returns the PatientRecord of the youngest patient in this BST.
   * 
   * @return the PatientRecord of the youngest patient in this BST and null if this tree is empty.
   */
  public PatientRecord getRecordOfYoungestPatient() {
    if (isEmpty()) {
      return null;
    }
    PatientRecordNode youngestNode = root;
    while (youngestNode.getRightChild() != null) {
      youngestNode = youngestNode.getRightChild();
    }
    return youngestNode.getPatientRecord();
  }



  /**
   * Returns the PatientRecord of the oldest patient in this BST.
   * 
   * @return the PatientRecord of the oldest patient in this BST, and null if this tree is empty.
   */
  public PatientRecord getRecordOfOldestPatient() {
    if (isEmpty()) {
      return null;
    }
    PatientRecordNode oldestNode = root;
    while (oldestNode.getLeftChild() != null) {
      oldestNode = oldestNode.getLeftChild();
    }
    return oldestNode.getPatientRecord();
  }


}
