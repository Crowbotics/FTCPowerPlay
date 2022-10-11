package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class MecanumDriveCommand extends CommandBase {

    private final DriveSubsystem m_drive;
    private final DoubleSupplier m_strafe;
    private final DoubleSupplier m_forward;
    private final DoubleSupplier m_turn;
    private final Double m_speed;

    public MecanumDriveCommand(DriveSubsystem subsystem,
                               DoubleSupplier strafe,
                               DoubleSupplier forward,
                               DoubleSupplier turn,
                               Double speed){

        m_strafe = strafe;
        m_drive = subsystem;
        m_forward = forward;
        m_turn = turn;
        m_speed = speed;
        addRequirements(subsystem);
    }

    @Override
    public void execute(){
        m_drive.drive(m_strafe.getAsDouble() * m_speed, m_forward.getAsDouble() * m_speed, m_turn.getAsDouble() * m_speed);
    }

}
