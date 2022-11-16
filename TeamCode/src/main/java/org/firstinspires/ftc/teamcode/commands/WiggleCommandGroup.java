package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.TensorFlowCommandAutonomousOpMode;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

public class WiggleCommandGroup extends SequentialCommandGroup {

    public WiggleCommandGroup(DriveSubsystem subsystem, Telemetry t){
        addCommands(
                new MecanumDriveForwardCommand(subsystem,
                        Constants.kWiggleSpeed, Constants.kWiggleForward, t),
                new MecanumDriveStrafeCommand(subsystem,
                        -Constants.kWiggleSpeed, Constants.kWiggleStrafe, t),
                new MecanumDriveStrafeCommand(subsystem,
                    2*Constants.kWiggleSpeed, Constants.kWiggleStrafe, t)
        );

        addRequirements(subsystem);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        TensorFlowCommandAutonomousOpMode.completedPark = true;
    }
}
