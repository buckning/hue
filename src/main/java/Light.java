import com.philips.lighting.hue.sdk.wrapper.domain.clip.Effect;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import java.awt.*;

public class Light {
    private LightPoint lightPoint;

    public Light(LightPoint lightPoint) {
        this.lightPoint = lightPoint;
    }

    public void turnOn() {
        LightState lightState = new LightState();
        lightState.setOn(true);
        lightPoint.updateState(lightState);
    }

    public void turnOff() {
        LightState lightState = new LightState();
        lightState.setOn(false);
        lightPoint.updateState(lightState);
    }

    /***
     * Set the brightness between 0 - 100
     * @param brightness
     */
    public void setBrightness(int brightness) {
        LightState lightState = new LightState();
        lightState.setBrightness(brightness);
        lightPoint.updateState(lightState);
    }

    public void setEffect(Effect effect) {
        LightState lightState = new LightState();
        lightState.setEffect(effect);
        lightPoint.updateState(lightState);
    }

    public void setColor(Color color) {
        LightConfiguration lightConfiguration = lightPoint.getLightConfiguration();

        HueColor hueColor = new HueColor(
                new HueColor.RGB(color.getRed(), color.getGreen(), color.getBlue()),
                lightConfiguration.getModelIdentifier(),
                lightConfiguration.getSwVersion());

        LightState lightState = new LightState();
        lightState.setXY(hueColor.getXY().x, hueColor.getXY().y);

        lightPoint.updateState(lightState);
    }
}
