/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.commands.MecanumDriveForwardCommand;
import org.firstinspires.ftc.teamcode.commands.MecanumDriveStrafeCommand;
import org.firstinspires.ftc.teamcode.commands.ParkingPos1CommandGroup;
import org.firstinspires.ftc.teamcode.commands.ParkingPos2CommandGroup;
import org.firstinspires.ftc.teamcode.commands.ParkingPos3CommandGroup;
import org.firstinspires.ftc.teamcode.commands.ScoreConeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.WiggleCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.List;

/**
 * This 2022-2023 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine which image is being presented to the robot.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "Tensor Flow Autonomous Testing")
public class TensorFlowCommandAutonomousOpMode extends CommandOpMode {

    /*
     * Specify the source for the Tensor Flow Model.
     * If the TensorFlowLite object model is included in the Robot Controller App as an "asset",
     * the OpMode must to load it using loadModelFromAsset().  However, if a team generated model
     * has been downloaded to the Robot Controller's SD FLASH memory, it must to be loaded using loadModelFromFile()
     * Here we assume it's an Asset.    Also see method initTfod() below .
     */
    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";
    // private static final String TFOD_MODEL_FILE  = "/sdcard/FIRST/tflitemodels/CustomTeamModel.tflite";


    private static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AQVfuhD/////AAABmUy+JtFnQEiah9c4g6jT5ABkNyf+UcCjdUkk685smR3JTL3Xn07fHq7m+Wjc8Z9L8LoD8DbXuu/5OA8uF5cHtWJMuuGAP3x1S9kyWC08QEymNBmCDmORuefPZNeM8LxUirkls/n5wXwnWZd4cUN5FGSky2YHBN+T9729s0ADq2RUyh8OoVSmwVkEC3smW3/BxXxvw5TcWRUzEeljx56AX8y3OhuALR4mFF/xv93o0bEUKN6RYhbXRZd3lJWuPtCDGrgIN/hUWgHh3mu+9iUqxBsXOhOaEpm6UgxEp7zwg2e5Gy7mPuI+3dWyntMaw+zf2b0YNXxcb0oyF+jyjIpN4cDWC8ZiGLDq1d047suOUWDb";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    public static String parkingPosition = "0";
    public static boolean completedPark = false;

    //Subsystems
    DriveSubsystem m_drive;
    IntakeSubsystem m_intake;
    ElevatorSubsystem m_elevator;

            //Commands
    MecanumDriveForwardCommand m_noDriveCommand;

    ParkingPos1CommandGroup m_pos1CommandGroup;
    ParkingPos2CommandGroup m_pos2CommandGroup;
    ParkingPos3CommandGroup m_pos3CommandGroup;

    WiggleCommandGroup m_wiggleCommandGroup;

    ScoreConeCommandGroup m_scoreConeCommandGroup;

    Crowbot m_crowbot;

    CommandBase decision;
    boolean decisionMade = false;

    @Override
    public void initialize() {
        m_crowbot = new Crowbot(Crowbot.OpModeType.AUTO);

        //Subsystems
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

        //Command assignments
        m_noDriveCommand = new MecanumDriveForwardCommand(m_drive, 0.0, 10, telemetry);

        m_pos1CommandGroup = new ParkingPos1CommandGroup(m_drive, telemetry);
        m_pos2CommandGroup = new ParkingPos2CommandGroup(m_drive, telemetry);
        m_pos3CommandGroup = new ParkingPos3CommandGroup(m_drive, telemetry);

        m_wiggleCommandGroup = new WiggleCommandGroup(m_drive, telemetry);

        m_scoreConeCommandGroup = new ScoreConeCommandGroup(m_drive, m_elevator, m_intake, telemetry);


        //Set default commands
//        m_drive.setDefaultCommand(m_noDriveCommand);

    }

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        initialize();
        waitForStart();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can increase the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.1, 16.0 / 9.0);
            telemetry.addData(">>", "tfod is good");
            telemetry.update();
        } else {
            telemetry.addData(">>", "tfod is null");
            telemetry.update();
        }

        /* Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                m_crowbot.run();

                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Objects Detected", updatedRecognitions.size());

                        // step through the list of recognitions and display image position/size information for each one
                        // Note: "Image number" refers to the randomized image orientation/number
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData("", " ");
                            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                        }
                    }

                    if (!decisionMade && (updatedRecognitions == null || updatedRecognitions.size() == 0)) {
//                        if(!CommandScheduler.getInstance().isScheduled(m_wiggleCommandGroup))
                        schedule(m_wiggleCommandGroup);

                        telemetry.addData("Wiggle..", "wiggle yeah!");
                    }

                    if (updatedRecognitions != null && !decisionMade && updatedRecognitions.size() > 0) {
                        parkingPosition = updatedRecognitions.get(0).getLabel().substring(0, 1);

                        if (parkingPosition.equals("1")) {
                            decision = m_pos1CommandGroup;
                            decisionMade = true;
                        } else if (parkingPosition.equals("2")) {
                            decision = m_pos2CommandGroup;
                            decisionMade = true;
                        } else if (parkingPosition.equals("3")) {
                            decision = m_pos3CommandGroup;
                            decisionMade = true;
                        }

                        CommandScheduler.getInstance().cancelAll();
                        schedule(decision);
                    }

                        telemetry.addData("Parking Position: ", parkingPosition);

                        telemetry.update();

                        //schedule(m_scoreConeCommandGroup);

                    }


                }
            }
            m_crowbot.reset();
        }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        // tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }
}
