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
//    private JButton setValues;
//    private JTextField redMaxField;
//    private JTextField redMinField;
//    private JTextField greenMaxField;
//    private JTextField greenMinField;
//    private JTextField blueMaxField;
//    private JTextField blueMinField;
//    private int redMax;
//    private int redMin;
//    private int greenMax;
//    private int greenMin;
//    private int blueMin;
    private WPIBinaryImage outputThreshold;
    private WPIBinaryImage outputProcess;

    public CameraExtension() {
        super();
//        this.setValues = new JButton("Update");
//        this.setValues.setActionCommand("update");
//        this.setValues.addActionListener(new UpdateButtonListener(this));
//        
//        this.redMax = 100;
//        this.redMin = 0;
//        this.greenMax = 255;
//        this.greenMin = 0;
//        this.blueMax = 100;
//        this.blueMin = 0;
//        this.redMaxField = new JTextField(10);
//        this.redMinField = new JTextField(10);
//        this.greenMaxField = new JTextField(10);
//        this.greenMinField = new JTextField(10);
//        this.blueMaxField = new JTextField(10);
//        this.blueMinField = new JTextField(10);
//        this.redMaxField.setText(String.valueOf(redMax));
//        this.redMinField.setText(String.valueOf(redMin));
//        this.greenMaxField.setText(String.valueOf(greenMax));
//        this.greenMinField.setText(String.valueOf(greenMin));
//        this.blueMaxField.setText(String.valueOf(blueMax));
//        this.blueMinField.setText(String.valueOf(blueMin));
//        
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
        outputProcess = thresholdRGB(rawImage,
                redMin.getValue().intValue(), redMax.getValue().intValue(),
                greenMin.getValue().intValue(), greenMax.getValue().intValue(),
                blueMin.getValue().intValue(), blueMax.getValue().intValue());
//        WPIImage output = rawImage.getRedChannel().getThresholdInverted(100).getAnd(rawImage.getRedChannel().getThreshold(0));
//        int cX = rawImage.getWidth() / 2;
//        rawImage.drawLine(new WPIPoint(cX, 0), new WPIPoint(cX, rawImage.getHeight()), new WPIColor(0, 255, 0), 3);
       // rawImage = (WPIColorImage) output;
        //output.dispose();
        outputProcess.erode(erosion.getValue().intValue());
        outputProcess.dilate(dialation.getValue().intValue());
        return outputProcess;
    }

    private WPIBinaryImage thresholdRGB(WPIColorImage input, int rLow, int rMax, int gLow, int gMax, int bLow, int bMax) {
        WPIBinaryImage rL = input.getRedChannel().getThreshold(rLow);
        WPIBinaryImage rM = input.getRedChannel().getThresholdInverted(rMax);
        WPIBinaryImage gL = input.getGreenChannel().getThreshold(gLow);
        WPIBinaryImage gM = input.getGreenChannel().getThresholdInverted(gMax);
        WPIBinaryImage bL = input.getBlueChannel().getThreshold(bLow);
        WPIBinaryImage bM = input.getBlueChannel().getThresholdInverted(bMax);

        outputThreshold = rL.getAnd(rM).getAnd(gL).getAnd(gM).getAnd(bL).getAnd(bM);

        rL.dispose();
        rM.dispose();
        gL.dispose();
        gM.dispose();
        bL.dispose();
        bM.dispose();
        
        
        input.dispose();
        
        return outputThreshold;
    }
//    
//    public void updateColors() {
//        this.redMax = Integer.parseInt(this.redMaxField.getText());
//        this.redMin = Integer.parseInt(this.redMinField.getText());
//        this.greenMax = Integer.parseInt(this.greenMaxField.getText());
//        this.greenMin = Integer.parseInt(this.greenMinField.getText());
//        this.blueMax = Integer.parseInt(this.blueMaxField.getText());
//        this.blueMin = Integer.parseInt(this.blueMinField.getText());
//    }
//    
//    private class UpdateButtonListener implements ActionListener {
//
//        private JafeIt jafeIt;
//        
//        public UpdateButtonListener(JafeIt jafeIt) {
//            this.jafeIt = jafeIt;
//        }
//        
//        @Override
//        public void actionPerformed(ActionEvent e) {
//           this.jafeIt.updateColors();
//        }
//    }
}
