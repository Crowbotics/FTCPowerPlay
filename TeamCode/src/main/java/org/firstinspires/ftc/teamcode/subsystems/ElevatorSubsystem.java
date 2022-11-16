package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ElevatorSubsystem extends SubsystemBase {

    private Telemetry telemetry;
    private DcMotorSimple elevator;

    public ElevatorSubsystem(HardwareMap hMap,
                             final String motorName,
                             Telemetry t) {
        this.elevator = hMap.get(DcMotorSimple.class, motorName);
        telemetry = t;
    }

    public void down(double power) {
        this.elevator.setPower(-1* power);
    }

    public void up(double power) {
        this.elevator.setPower(power);
    }

    public void stop() {
        this.elevator.setPower(0.1);
    }

    public void periodic()
    {
//        telemetry.addData("Servo", 0);

    }

}