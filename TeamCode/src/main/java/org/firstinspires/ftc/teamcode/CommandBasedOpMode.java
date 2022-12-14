package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.commands.ElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.commands.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.ScoreConeCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name="Command Based Op Mode")
public class CommandBasedOpMode extends CommandOpMode {

    //Subsystem declarations
    DriveSubsystem m_drive;
    IntakeSubsystem m_intake;
    ElevatorSubsystem m_elevator;

    //Command declarations
    MecanumDriveCommand m_mecanumDriveCommand, m_mecanumDriveSlowCommand;
    IntakeCommand m_intakeInCommand, m_intakeOutCommand, m_intakeStopCommand;
    ElevatorCommand m_elevatorCommand;

    ScoreConeCommandGroup m_scoreConeCommandGroup;

    //Button declarations
    GamepadEx m_driverGamepad, m_auxGamepad;
    GamepadButton m_slow_button, m_intakeInButton, m_intakeOutButton, m_testingButton;

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

        //Button assignments
        m_driverGamepad = new GamepadEx(gamepad1);
        m_auxGamepad = new GamepadEx(gamepad2);
        m_slow_button =  new GamepadButton(m_driverGamepad, GamepadKeys.Button.RIGHT_BUMPER);
        m_intakeInButton = new GamepadButton(m_auxGamepad, GamepadKeys.Button.A);
        m_intakeOutButton = new GamepadButton(m_auxGamepad, GamepadKeys.Button.B);

        m_testingButton = new GamepadButton(m_driverGamepad, GamepadKeys.Button.X);


        //Command assignments
        m_mecanumDriveCommand = new MecanumDriveCommand(m_drive,
                ()->m_driverGamepad.getLeftX(),
                ()->m_driverGamepad.getLeftY(),
                ()->m_driverGamepad.getRightX(),
                1.0);

        m_mecanumDriveSlowCommand = new MecanumDriveCommand(m_drive,
                ()->m_driverGamepad.getLeftX(),
                ()->m_driverGamepad.getLeftY(),
                ()->m_driverGamepad.getRightX(),
                0.5);

        m_intakeInCommand = new IntakeCommand(m_intake, IntakeCommand.Mode.IN);
        m_intakeOutCommand = new IntakeCommand(m_intake, IntakeCommand.Mode.OUT);
        m_intakeStopCommand = new IntakeCommand(m_intake, IntakeCommand.Mode.STOP);

        m_elevatorCommand = new ElevatorCommand(m_elevator,
                ()->m_auxGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                ()->m_auxGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));

        m_scoreConeCommandGroup = new ScoreConeCommandGroup(m_drive, m_elevator, m_intake, telemetry);


        //Default commands
        m_drive.setDefaultCommand(m_mecanumDriveSlowCommand);
        m_intake.setDefaultCommand(m_intakeStopCommand);
        m_elevator.setDefaultCommand(m_elevatorCommand);

        //Button bindings
        m_slow_button.whenHeld(m_mecanumDriveCommand);
        m_intakeInButton.whenHeld(m_intakeInCommand);
        m_intakeOutButton.whenHeld(m_intakeOutCommand);

        m_driverGamepad.getGamepadButton(GamepadKeys.Button.A).
                whenPressed(new InstantCommand(() ->
                {
                    m_drive.resetGyro();
                }));

//        m_testingButton.whenPressed(m_scoreConeCommandGroup);

//        m_driverGamepad.getGamepadButton(GamepadKeys.Button.X).whileHeld(
//                new InstantCommand(() -> {
//                    elevator.setPower(1);
//                    telemetry.addData("Yup?", "Yup!");
//                }));
//
//        m_driverGamepad.getGamepadButton(GamepadKeys.Button.A).whileHeld(
//                new InstantCommand(() -> {
//                    elevator.setPower(0);
//                    telemetry.addData("Yup?", "Yup!");
//                }));
//
//        m_driverGamepad.getGamepadButton(GamepadKeys.Button.B).whileHeld(
//                new InstantCommand(() -> {
//                    elevator.setPower(-1);
//                    telemetry.addData("Yup?", "Yup!");
//                }));


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
