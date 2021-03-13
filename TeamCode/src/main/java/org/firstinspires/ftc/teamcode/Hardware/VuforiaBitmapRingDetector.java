package org.firstinspires.ftc.teamcode.Hardware;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.renderscript.Int2;
import com.qualcomm.robotcore.util.ElapsedTime;
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

    VuforiaLocalizer.CloseableFrame frame;
    private Bitmap image;

    ElapsedTime actionTimer;

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
        actionTimer = new ElapsedTime();
    }



    int getRings(double scanX, double scanY, double scanH){
        int num0 = -1; //if anybody makes this an array, I skin you
        int num1 = -1;
        int num2 = -1;
        try {
            frame = vuforia.getFrameQueue().take();
            //Convert frame to bitmap
            image = vuforia.convertFrameToBitmap(frame);

            //If multiple points on the image need to be checked, the additional checks would go here
            if  (image != null) {
                float saturation= getSaturationFromVerticalLine(image, scanX - Config.cameraSamplingDistance, scanY - scanH / 2, scanY + scanH / 2);
                num0 = getRingCountFromSaturation(saturation);
                telemetry.addData("Saturation0: ", saturation);
                saturation= getSaturationFromVerticalLine(image, scanX, scanY - scanH / 2, scanY + scanH / 2);
                num1 = getRingCountFromSaturation(saturation);
                telemetry.addData("Saturation1: ", saturation);
                saturation= getSaturationFromVerticalLine(image, scanX + Config.cameraSamplingDistance, scanY - scanH / 2, scanY + scanH / 2);
                num2 = getRingCountFromSaturation(saturation);
                telemetry.addData("Saturation2: ", saturation);
            }

        } catch (InterruptedException e){
            telemetry.addLine("ISSUE IN VUFORIA FRAME GATHERING");
            telemetry.update();
        }


        return Math.max(num0, Math.max(num1,num2));
    }

    private float getSaturationFromVerticalLine(Bitmap bitmap, double scanX, double scanMinY, double scanMaxY) {

        int x = (int) (bitmap.getWidth() * scanX);


        float[] hsv = new float[3];
        float totalSaturation = 0;

        int minY = (int) (scanMinY * (double) bitmap.getHeight());
        int maxY = (int) (scanMaxY * (double) bitmap.getHeight());


        for (int y = minY; y < maxY; y++) {
            Color.colorToHSV(bitmap.getPixel(x, y),hsv);
            totalSaturation += hsv[1];
        }

        totalSaturation /= maxY-minY;

        return totalSaturation;
    }

    private byte getRingCountFromSaturation(float saturation){
        byte num;

        //Determine the midpoint between each of the target saturation values.
        final float max0 = Config.targetSaturation0 + (Config.targetSaturation1 - Config.targetSaturation0) / 2;
        final float max1 = Config.targetSaturation1 + (Config.targetSaturation4 - Config.targetSaturation1) / 2;

        if (saturation <= max0){
            num = 0;
        } else if (saturation > max0 && saturation < max1){
            num = 1;
        } else {
            num = 4;
        }

        return num;
    }



}
