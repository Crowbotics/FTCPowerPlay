package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class MecanumDriveForwardCommand extends CommandBase {

    private final DriveSubsystem m_drive;
    private final Double m_speed;
    private final int m_distance;
    private final Telemetry telemetry;

    private boolean reachedDesiredDistance;

    public MecanumDriveForwardCommand(DriveSubsystem subsystem,
                                      Double speed,
                                      int distance, Telemetry t){

        m_speed = speed;
        m_drive = subsystem;
        m_drive.resetEncoders();
        m_distance = distance;
        telemetry = t;
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        super.initialize();
        m_drive.resetEncoders();
    }

    @Override
    public void execute(){

        if(Math.abs(m_drive.getAverageEncoderDistance()) < m_distance) {
            m_drive.drive(0, -m_speed, 0);
            telemetry.addData("AutoDrive Status: ", "Driving");
            reachedDesiredDistance = false;
        } else {
//            m_drive.resetEncoders();
            telemetry.addData("AutoDrive Status: ", "Stopping");
            m_drive.drive(0, 0, 0);
            reachedDesiredDistance = true;
        }
        telemetry.addData("AutoDrive Encoder: ", m_drive.getAverageEncoderDistance());
        telemetry.update();
    }

    @Override
    public boolean isFinished() {
        return reachedDesiredDistance;
    }
}
