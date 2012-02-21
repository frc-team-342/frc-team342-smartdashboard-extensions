/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.first.team342.smartdashboard.camera;

import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.properties.NumberProperty;
import edu.wpi.first.wpijavacv.WPIBinaryImage;
import edu.wpi.first.wpijavacv.WPIColorImage;
import edu.wpi.first.wpijavacv.WPIImage;

/**
 *
 * @author Team 342
 */
public class CameraExtension extends WPICameraExtension {

    private final NumberProperty redMax;
    private final NumberProperty redMin;
    private final NumberProperty greenMax;
    private final NumberProperty greenMin;
    private final NumberProperty blueMax;
    private final NumberProperty blueMin;
    private final NumberProperty erosion;
    private final NumberProperty dialation;
    
    private WPIImage outputImage;

    public CameraExtension () {
        super();
        this.redMax = new NumberProperty(this, "red Max", 160);
        this.redMin = new NumberProperty(this, "red Min", 0);
        this.greenMax = new NumberProperty(this, "green Max", 255);
        this.greenMin = new NumberProperty(this, "green Min", 200);
        this.blueMax = new NumberProperty(this, "blue Max", 255);
        this.blueMin = new NumberProperty(this, "blue Min", 0);
        this.erosion = new NumberProperty(this, "Erosion", 0);
        this.dialation = new NumberProperty(this,"dialation", 1);
        this.ipProperty.setDefault("10.3.42.11");

    }

    @Override
    public WPIImage processImage(WPIColorImage rawImage) {
        WPIBinaryImage tempBinaryImage = (WPIBinaryImage) outputImage;
        tempBinaryImage.erode(erosion.getValue().intValue());
        tempBinaryImage.dilate(dialation.getValue().intValue());
        outputImage = (WPIImage) tempBinaryImage;
        return outputImage;
    }

    private void thresholdRGB() {
        WPIColorImage tempColorImage =(WPIColorImage) outputImage;
        WPIBinaryImage redMinImage = tempColorImage.getRedChannel().getThreshold(redMin.getValue().intValue());
        WPIBinaryImage redMaxImage = tempColorImage.getRedChannel().getThresholdInverted(redMax.getValue().intValue());
        WPIBinaryImage greenMinImage = tempColorImage.getGreenChannel().getThreshold(greenMin.getValue().intValue());
        WPIBinaryImage greenMaxImage = tempColorImage.getGreenChannel().getThresholdInverted(greenMax.getValue().intValue());
        WPIBinaryImage blueMinImage = tempColorImage.getBlueChannel().getThreshold(blueMin.getValue().intValue());
        WPIBinaryImage blueMaxImage = tempColorImage.getBlueChannel().getThresholdInverted(blueMax.getValue().intValue());

        outputImage = redMinImage.getAnd(redMaxImage).getAnd(greenMinImage).getAnd(greenMaxImage).getAnd(blueMinImage).getAnd(blueMaxImage);
        
        tempColorImage.dispose();
        redMinImage.dispose();
        redMaxImage.dispose();
        greenMinImage.dispose();
        greenMaxImage.dispose();
        blueMinImage.dispose();
        blueMaxImage.dispose();
    }
}
