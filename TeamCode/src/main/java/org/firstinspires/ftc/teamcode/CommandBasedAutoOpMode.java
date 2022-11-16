package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.commands.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.ParkingPos2CommandGroup;
import org.firstinspires.ftc.teamcode.commands.ScoreConeCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

//@Autonomous(name="Command Based Auto Op Mode")
public class CommandBasedAutoOpMode extends CommandOpMode {

    //Subsystem declarations
    DriveSubsystem m_drive;
    IntakeSubsystem m_intake;
    ElevatorSubsystem m_elevator;

    ScoreConeCommandGroup m_scoreConeCommandGroup;
    ParkingPos2CommandGroup m_parkingPos2CommandGroup;

    Crowbot m_crowbot;

    @Override
    public void initialize() {

        m_crowbot = new Crowbot(Crowbot.OpModeType.TELEOP);

        //Subsystem assignments
        m_drive = new DriveSubsystem(hardwareMap,
                "frontLeft",
                "rearLeft",
                "frontRight",
                "rearRight",
                3.5,
                telemetry);

        m_intake = new IntakeSubsystem(hardwareMap, "intake", telemetry);

        m_elevator = new ElevatorSubsystem(hardwareMap, "elevator", telemetry);

        //Subsystem registrations
        register(m_drive);
        register(m_intake);
        register(m_elevator);


        m_scoreConeCommandGroup = new ScoreConeCommandGroup(m_drive, m_elevator, m_intake, telemetry);
        m_parkingPos2CommandGroup = new ParkingPos2CommandGroup(m_drive, telemetry);


        //schedule(m_scoreConeCommandGroup);
        schedule(m_parkingPos2CommandGroup);

        schedule(new RunCommand(() -> telemetry.update()));

    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            m_crowbot.run();
        }
        m_crowbot.reset();
    }

}
