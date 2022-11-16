package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TensorFlowCommandAutonomousOpMode;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class ScoreConeCommandGroup extends SequentialCommandGroup {

    public ScoreConeCommandGroup(DriveSubsystem drive, ElevatorSubsystem elevator, IntakeSubsystem intake, Telemetry telemetry){
        addCommands(
                new MecanumDriveForwardCommand(drive, 0.25, 50, telemetry),
                new MecanumDriveStrafeCommand(drive, 0.25, 60, telemetry),
                new MecanumDriveForwardCommand(drive, 0.25, 50, telemetry),
                new ElevatorAutoCommand(elevator, 1.0, 0.0, 200, telemetry),
                new MecanumDriveForwardCommand(drive, 0.5, 5, telemetry),
                new IntakeAutoCommand(intake, IntakeAutoCommand.Mode.OUT, 75),
                new IntakeAutoCommand(intake, IntakeAutoCommand.Mode.IN, 75),
                new ElevatorAutoCommand(elevator, 0.0, 1.0, 180, telemetry)
//                new MecanumDriveForwardCommand(drive, -0.5, 20, telemetry)
        );
        addRequirements(drive, elevator, intake);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
//        TensorFlowCommandAutonomousOpMode.completedPark = true;
    }
}
