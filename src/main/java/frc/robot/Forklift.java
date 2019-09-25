
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class Forklift {
    WPI_TalonSRX lift1;
    // private DigitalInput switchTop;
    // private DigitalInput switchMiddle;
    // private DigitalInput switchBottom;

    public Forklift(int liftPort1) {
        lift1 = new WPI_TalonSRX(liftPort1);
        // switchTop = new DigitalInput(2);
        // switchMiddle = new DigitalInput(3);
        // switchBottom = new DigitalInput(4);
    }

    public void set(double direction) {
        if (direction == 1) {
            lift1.set(.375);
            SmartDashboard.putString("forklift", "forklift going UP");
        } else if (direction == 0) {
            lift1.set(0);
            SmartDashboard.putString("forklift", "forklift STOPPED");
        } else {
            lift1.set(-.375);
            SmartDashboard.putString("forklift", "forklift going DOWN");
        }
    }
    // public void setPosition(double position, double direction){
    // if(direction==0){
    // return;
    // }
    // if(switchTop.get())
    // {
    // set(-1);
    // if(switchMiddle.get()){
    // set(0);
    // }
    // }
    // else if(switchMiddle.get() && direction==1){
    // set(1);
    // if(switchTop.get()){
    // set(0);
    // }
    // }
    // else if(switchMiddle.get() && direction==-1){
    // set(-1);
    // if(switchBottom.get()){
    // set(0);
    // }
    // }
    // else if(switchBottom.get()){
    // set(1);
    // if(switchMiddle.get()){
    // set(0);
    // }
    // }
    // }
}
