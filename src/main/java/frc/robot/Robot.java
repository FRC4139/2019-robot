/*
 * FRC Team 4139 "Easy as Pi"
 * Scripps Ranch High School, San Diego, California
 * written for 2019 robotics season - Destination: Deep Space
 */
//Controller 1 controls wheels and linear Actuator
//Controller 2 controls literally everything else

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Robot extends IterativeRobot {

  private XboxController controller1; // Controls wheels and linear actuator
  private XboxController controller2; // Controls forklift and grabber
  private Wheels wheels;
  private Forklift forklift;
  private Intake intake;
  private LinearActuator actuatorOne;
  private boolean toggle1 = true;
  private DigitalInput switchTop;
  private DigitalInput switchBottom;
  private double position; // forklift starting position. 1=low, 2=mid, 3=high
  private boolean max = true; //set this to the starting position of the forklift <<<<<<<<<<
  private Timer timer = new Timer();


  private LaneDetector laneDetector = new LaneDetector();
  private boolean lineFollow = false;

  @Override
  public void robotInit() {
    controller1 = new XboxController(0);
    controller2 = new XboxController(1);
    wheels = new Wheels(1, 8, 9, 5);
    intake = new Intake(2, 10);
    forklift = new Forklift(6);
    actuatorOne = new LinearActuator(3);
    switchTop = new DigitalInput(2);
    switchBottom = new DigitalInput(3);
    position = 1;
    CameraServer.getInstance().startAutomaticCapture(0);
    CameraServer.getInstance().startAutomaticCapture(1);
    CameraServer.getInstance().addAxisCamera("10.41.39.11");

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {
    robotControl();
  }

  @Override
  public void teleopPeriodic() {
    robotControl();
  }

  public void robotControl() {// this is where everything goes
    // forklift
    // manual control
    // if (controller2.getBumper(Hand.kLeft)) {
    //   forklift.set(1);
    // } else if (controller2.getBumper(Hand.kRight))
    //   forklift.set(-1);
    // else
    //   forklift.set(0);

    // new switches control
    if (controller2.getBumperPressed(Hand.kRight) && !switchTop.get()) {
      timer.start();
      forklift.set(1);
    } else if (controller2.getBumperPressed(Hand.kLeft) && !switchBottom.get()) {
      timer.start();
      forklift.set(-1);
    }
    if (timer.get() > 4 || (timer.get() > .7 && (switchBottom.get() || switchTop.get()))) {
      forklift.set(0);
      timer.stop();
      timer.reset();
    }
    
   
    // switches control
  //  if (!max) {// max=false, at middle position
  //     if (controller2.getBumperPressed(Hand.kLeft)) {
        
  //       timer.start();
  //       if(timer.get()<2){
  //         forklift.set(1);
  //       }
  //       if (timer.get() > 2) {
  //         forklift.set(0);
  //         max = true;
  //         timer.reset();
  //         timer.stop();
  //       }
  //     } else if (controller2.getBumperPressed(Hand.kRight)) {
        
  //       timer.start();
  //       if(timer.get()<.5){
  //         forklift.set(-1);
  //       }
  //       if (timer.get() > .5) {
  //         forklift.set(0);
  //         max = true;
  //         timer.reset();
  //         timer.stop();
  //       }
  //     }
  //   }
  // else {// max=true, at either top or bottom position
  //     if (switchTop.get() && controller2.getBumperPressed(Hand.kRight)) {// if at top, only go down
  //       timer.start();
  //       if(timer.get()<.5){
  //         forklift.set(-1);
  //       }
  //       if (timer.get() > .5 ) {
  //         forklift.set(0);
  //         max = false;
  //         timer.reset();
  //         timer.stop();
  //       }
  //     } else if (switchBottom.get() && controller2.getBumperPressed(Hand.kLeft)) {// if at bottom, only go up
  //       timer.start();
  //       if (timer.get()<.5) {
  //         forklift.set(1);
  //        }
  //       if (timer.get()>.5) {
  //          forklift.set(0);
  //          max = false;
  //          timer.reset();
  //          timer.stop();
  //        }
  //      }
  //      timer.stop();
  //    }

    // wheels
    wheels.drive(controller1.getY(Hand.kLeft), controller1.getX(Hand.kRight));

    // intake
    if (controller2.getTriggerAxis(Hand.kLeft) > .1) {
      intake.set(1);
    } else if (controller2.getTriggerAxis(Hand.kRight) > .1)
      intake.set(-1);
    else
      intake.set(0);

    // linear actuator
    if (controller1.getAButtonPressed() == true) {
      toggle1 = !toggle1;
    }
    actuatorOne.set(toggle1);

    // reverse wheels
    if (controller1.getYButtonPressed()) {
      wheels.reverse();
    }

    if(controller2.getXButtonPressed()){
      if(lineFollow) {
        lineFollow = false;
      } else {
        lineFollow = true;
      }
    }

    if(lineFollow) {
      laneDetector.visionDetect();
    }
    // displays status on SmartDashboard
    SmartDashboard.putBoolean("Robot is reversed", wheels.getIsReversed());
    SmartDashboard.putBoolean("switch top", switchTop.get());
    SmartDashboard.putBoolean("switch bottom", switchBottom.get());
    SmartDashboard.putNumber("timer", timer.get());
  }

  @Override
  public void testPeriodic() {
  }
}
