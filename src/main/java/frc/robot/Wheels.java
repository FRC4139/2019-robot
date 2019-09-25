/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Wheels {

    private WPI_TalonSRX FL, BL, FR, BR;
    private DifferentialDrive wheels;
    public static final int TANK_DRIVE = 1;
    public static final int ARCADE_DRIVE = 2;
    private int driveMode = ARCADE_DRIVE; // the drive mode that the robot is using
    private boolean isReversed = false;

    public Wheels(int frontLeft, int backLeft, int frontRight, int backRight) {
        FL = new WPI_TalonSRX(frontLeft);
        BL = new WPI_TalonSRX(backLeft);
        FR = new WPI_TalonSRX(frontRight);
        BR = new WPI_TalonSRX(backRight);
        FL.setInverted(false);
        wheels = new DifferentialDrive(new SpeedControllerGroup(FR, BR), new SpeedControllerGroup(FL, BL));
    }

    public void drive(double speed1, double speed2) {
        switch (driveMode) {
        case TANK_DRIVE:
            wheels.tankDrive(speed1, speed2);
            break;
        case ARCADE_DRIVE:
            if (!isReversed)
                wheels.arcadeDrive(speed1 * 0.9, speed2 * 0.9);
            else {
                wheels.arcadeDrive(-speed1 * 0.9, speed2 * 0.9);
            }
            break;
        default:
            wheels.arcadeDrive(speed1, speed2);
        }
    }

    public void reverse() {
        isReversed = !isReversed;
    }

    public boolean getIsReversed() {
        return isReversed;
    }
}
