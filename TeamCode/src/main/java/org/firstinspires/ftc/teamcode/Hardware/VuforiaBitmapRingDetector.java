package org.firstinspires.ftc.teamcode.Hardware;


import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class VuforiaBitmapRingDetector {

    //TODO verify if this is still active
    private static final String VUFORIA_KEY = "AQMfl/L/////AAABmTblKFiFfUXdnoB7Ocz4UQNgHjSNJaBwlaDm9EpX0UI5ISx2EH+5IoEmxxd/FG8c31He17kM5vtS0jyAoD2ev5mXBiITmx4N8AduU/iAw/XMC5MiEB1YBgw5oSO1qd4jvCOgbzy/HcOpN3KoVVnYqKhTLc8n6/IIFGy+qyF7b8WkzscJpybOSAT5wtaZumdBu0K3lHV6n+fqGJDMvkQ5xrCS6HiBtpZScAoekd7iP3IxUik2rMFq5hqMsOYW+qlxKp0cj+x4K9CIOYEP4xZsCBt66UxtDSiNqaiC1DyONtFz4oHJf/4J5aYRjMNwC2BpsVJ/R91WIcC0H0dpP9gtL/09J0bIMjm3plo+ac+OM0H3";

    private final VuforiaLocalizer vuforia;

    private final Telemetry telemetry;


    VuforiaBitmapRingDetector(Telemetry telemetry){

        WebcamName camera = DataHub.hardwareMap.get(WebcamName.class,Config.camera);
        telemetry.addLine("Camera Found");

        int cameraMonitorViewId = DataHub.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", DataHub.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        telemetry.addLine("Camera ID Found");

        parameters.cameraName = camera;

        telemetry.addLine("Camera assigned");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        telemetry.addLine("Vuforia instantiated");



        vuforia.setFrameQueueCapacity(1);
        vuforia.enableConvertFrameToBitmap();

        telemetry.update();
        this.telemetry = telemetry;
    }



}
