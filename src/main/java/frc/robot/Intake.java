/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {
    private WPI_TalonSRX intake1; 
    private WPI_TalonSRX intake2;
    
    public Intake(int inPort1, int inPort2)
    {
        intake1 = new WPI_TalonSRX(inPort1);
        intake2 = new WPI_TalonSRX(inPort2);
    }

    public void set(double direction)
    {
        if(direction == 1)
        {
            intake1.set(.45);
            intake2.set(-.45);
        }
        else if (direction == 0){
            intake1.set(0);
            intake2.set(0);
        }
        else
        {
            intake1.set(-.25);
            intake2.set(.25);        
        }
    }
}
