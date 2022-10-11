package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.commands.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name="Command Based Op Mode")
public class CommandBasedOpMode extends CommandOpMode {

    private DcMotorSimple elevator;

    //Subsystem declarations
    DriveSubsystem m_drive;
    //IntakeSubsystem m_intake;

    //Command declarations
    MecanumDriveCommand m_mecanumDriveCommand, m_mecanumDriveSlowCommand;
    IntakeCommand m_intakeInCommand, m_intakeOutCommand;

    //Button declarations
    GamepadEx m_driverGamepad, m_auxGamepad;
    GamepadButton m_slow_button, m_intakeInButton, m_intakeOutButton;

    Crowbot m_crowbot;

    @Override
    public void initialize() {

        m_crowbot = new Crowbot(Crowbot.OpModeType.TELEOP);

       // elevator =  hardwareMap.get(DcMotorSimple.class, "elevator");

        //Subsystem assignments
        m_drive = new DriveSubsystem(hardwareMap,
                "frontLeft",
                "rearLeft",
                "frontRight",
                "rearRight",
                3.5,
                telemetry);

      //  m_intake = new IntakeSubsystem(hardwareMap, "intake", telemetry);

        //Subsystem registrations
        register(m_drive);
      //  register(m_intake);

        //Button assignments
        m_driverGamepad = new GamepadEx(gamepad1);
        m_auxGamepad = new GamepadEx(gamepad2);
        m_slow_button =  new GamepadButton(m_driverGamepad, GamepadKeys.Button.RIGHT_BUMPER);
       // m_intakeInButton = new GamepadButton(m_driverGamepad, GamepadKeys.Button.A);
       // m_intakeOutButton = new GamepadButton(m_driverGamepad, GamepadKeys.Button.B);

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

       // m_intakeInCommand = new IntakeCommand(m_intake, IntakeCommand.Mode.IN);
       // m_intakeOutCommand = new IntakeCommand(m_intake, IntakeCommand.Mode.OUT);

        //Default commands
        m_drive.setDefaultCommand(m_mecanumDriveSlowCommand);

        //Button bindings
        m_slow_button.whenHeld(m_mecanumDriveCommand);
      //  m_intakeInButton.whenHeld(m_intakeInCommand);
     //   m_intakeOutButton.whenHeld(m_intakeOutCommand);

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
        telemetry.addData("Upload Number: ", 1.2);//Parker's way of knowing if uploading worked
        waitForStart();

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            m_crowbot.run();
        }
        m_crowbot.reset();
    }

}
