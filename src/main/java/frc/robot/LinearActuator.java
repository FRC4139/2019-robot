/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class LinearActuator {
    // Limit switch
    private DigitalInput switch1;
    private DigitalInput switch2;

    // private boolean toggleOn = false;

    private WPI_TalonSRX actuator;

    public LinearActuator(int actPort) {
        actuator = new WPI_TalonSRX(actPort);
        switch1 = new DigitalInput(0);
        switch2 = new DigitalInput(1);

    }

    // public void updateToggle() {
    // if (Joystick.getAButtonPressed()) {
    // toggleOn = !toggleOn;
    // }
    // }

    public void set(boolean toggleOn) {

        // updateToggle();
        double output = 0;

        if (toggleOn) {
            output = .18;//DO NOT GO OVER 0.20
            if (switch2.get()) {
                output = 0;
            }
        } else {
            output = -.18;
            if (switch1.get()) {
                output = 0;
            }
        }
        actuator.set(output);
    }
}
