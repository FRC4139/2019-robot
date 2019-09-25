/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Add your docs here.
 */
public class Vision {

    private static String direction;

    public static void checkVision() {
        try {
          String testFromFile = readFile("C:\\Users\\admin\\Desktop\\Coding\\Robot\\Robot\\src\\main\\java\\frc\\robot\\Data.txt");
          System.out.println("We good? " + testFromFile + "Variable set to: " + direction);
        } catch(IOException e) {
          System.out.println("Error: ");
          e.printStackTrace();
        }
    }

    // Utility methods for the Vision class //
    // ------------------------------------ //

    public static String readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
          StringBuilder sb = new StringBuilder();
          String line = br.readLine();
    
          while(line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
          }
          direction = sb.toString();
          return sb.toString();
        } finally {
          br.close();
        }
      }

    public static String checkDirection() {
        checkVision();
        return direction;
    }

}