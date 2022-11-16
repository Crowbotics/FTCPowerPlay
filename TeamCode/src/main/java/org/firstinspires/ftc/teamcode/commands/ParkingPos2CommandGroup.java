package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.TensorFlowCommandAutonomousOpMode;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

public class ParkingPos2CommandGroup extends SequentialCommandGroup {

    public ParkingPos2CommandGroup(DriveSubsystem subsystem, Telemetry t){
        addCommands(
                new MecanumDriveForwardCommand(subsystem,
                        Constants.kAutoSpeed, Constants.kAutoForwardParkDistance, t)
        );

        addRequirements(subsystem);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        TensorFlowCommandAutonomousOpMode.completedPark = true;
    }
}
