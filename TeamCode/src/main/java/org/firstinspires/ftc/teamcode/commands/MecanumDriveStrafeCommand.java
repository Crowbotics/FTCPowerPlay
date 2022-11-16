package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

public class MecanumDriveStrafeCommand extends CommandBase {

    private final DriveSubsystem m_drive;
    private final Double m_speed;
    private final int m_distance;
    private final Telemetry telemetry;

    public MecanumDriveStrafeCommand(DriveSubsystem subsystem,
                                     Double speed,
                                     int distance,
                                     Telemetry t){

        m_speed = speed;
        m_drive = subsystem;
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

        if(Math.abs(m_drive.getRightEncoderDistance()) < m_distance) {
            m_drive.drive(m_speed, 0, 0);
            telemetry.addData("Auto Strafe Encoder", m_drive.getRightEncoderDistance());
            telemetry.update();
        }
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(m_drive.getRightEncoderDistance()) >= m_distance);
    }

    @Override
    public void end(boolean interrupted)
    {
        m_drive.drive(0,0,0);
    }
}
