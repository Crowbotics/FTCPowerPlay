package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeSubsystem extends SubsystemBase {

    private Telemetry telemetry;
    private ServoEx intakeServo;

    public IntakeSubsystem(HardwareMap hMap,
                           final String servoName,
                           Telemetry t) {
        this.intakeServo = new SimpleServo(hMap, servoName, 0, 360);
        telemetry = t;
    }

    public void in() {
        this.intakeServo.rotateBy(10);
    }

    public void out() {
        this.intakeServo.rotateBy(-10);
    }

    public void periodic()
    {
//        telemetry.addData("Servo", 0);

    }

}