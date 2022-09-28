package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveSubsystem extends SubsystemBase {

    private Telemetry telemetry;

    private final MecanumDrive m_drive;
    private final MotorEx m_frontLeft, m_rearLeft, m_frontRight, m_rearRight;
    private final Motor.Encoder m_frontLeftEncoder, m_rearLeftEncoder, m_frontRightEncoder, m_rearRightEncoder;
    private final double WHEEL_DIAMETER;

    public DriveSubsystem(HardwareMap hMap,
                          final String flName,
                          final String rlName,
                          final String frName,
                          final String rrName,
                          final double diameter,
                          Telemetry t) {

        m_frontLeft = new MotorEx(hMap, flName);
        m_rearLeft= new MotorEx(hMap, rlName);
        m_frontRight = new MotorEx(hMap, frName);
        m_rearRight = new MotorEx(hMap, rrName);

        m_frontLeftEncoder= m_frontLeft.encoder;
        m_rearLeftEncoder = m_rearLeft.encoder;
        m_frontRightEncoder = m_frontRight.encoder;
        m_rearRightEncoder = m_rearRight.encoder;

        WHEEL_DIAMETER = diameter;
        telemetry = t;

        m_drive = new MecanumDrive(m_frontLeft, m_frontRight, m_rearLeft, m_rearRight);
    }

    public void drive(double strafe, double fwd, double turn, double angle) {
        m_drive.driveFieldCentric(-strafe, fwd, turn, -angle);
    }

    public double getLeftEncoderVal() {
        return m_frontLeftEncoder.getPosition();
    }

    public double getRightEncoderVal() {
        return -m_frontRightEncoder.getPosition();
    }

    public double getLeftEncoderDistance(){
        return m_frontLeftEncoder.getRevolutions() * WHEEL_DIAMETER * Math.PI;
    }

    public double getRightEncoderDistance(){
        return m_frontRightEncoder.getRevolutions() * WHEEL_DIAMETER * Math.PI;
    }

    public void resetEncoders(){
        m_frontLeftEncoder.reset();
        m_frontRightEncoder.reset();
    }

    public double getAverageEncoderDistance(){
        return(getLeftEncoderDistance() + -(getRightEncoderDistance()))/2.0;
    }

    public void periodic()
    {
        telemetry.addData("Left Encoder ", getLeftEncoderVal());
        telemetry.addData("Right Encoder ", getRightEncoderVal());
        telemetry.addData("Distance ", getAverageEncoderDistance());
    }

}