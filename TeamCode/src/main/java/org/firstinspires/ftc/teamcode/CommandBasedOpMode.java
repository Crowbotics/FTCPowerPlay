package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@TeleOp(name="Command Based Op Mode")
public class CommandBasedOpMode extends CommandOpMode {

    //Subsystem declarations
    DriveSubsystem m_drive;


    //Command declarations
    MecanumDriveCommand m_mecanumDriveCommand, m_mecanumDriveSlowCommand;

    //Button declarations
    GamepadEx m_driverGamepad, m_auxGamepad;
    GamepadButton m_slow_button;

    RevIMU gyro;

    Crowbot m_crowbot;

    @Override
    public void initialize() {

        m_crowbot = new Crowbot(Crowbot.OpModeType.TELEOP);

        gyro = new RevIMU(hardwareMap, "imu");

        gyro.init();

        //Subsystem assignments
        m_drive = new DriveSubsystem(hardwareMap,
                "frontLeft",
                "rearLeft",
                "frontRight",
                "rearRight",
                3.5,
                telemetry);

        //Subsystem registrations
        register(m_drive);

        //Button assignments
        m_driverGamepad = new GamepadEx(gamepad1);
        m_auxGamepad = new GamepadEx(gamepad2);
        m_slow_button =  new GamepadButton(m_driverGamepad, GamepadKeys.Button.RIGHT_BUMPER);

        //Command assignments
        m_mecanumDriveCommand = new MecanumDriveCommand(m_drive,
                ()->m_driverGamepad.getLeftX(),
                ()->m_driverGamepad.getLeftY(),
                ()->m_driverGamepad.getRightX(),
                ()->gyro.getHeading(),
                1.0);
        m_mecanumDriveSlowCommand = new MecanumDriveCommand(m_drive,
                ()->m_driverGamepad.getLeftX(),
                ()->m_driverGamepad.getLeftY(),
                ()->m_driverGamepad.getRightX(),
                ()->gyro.getHeading(),
                0.7);


        //Default commands
        m_drive.setDefaultCommand(m_mecanumDriveCommand);

        //Button bindings
        m_slow_button.whenHeld(m_mecanumDriveCommand);

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
