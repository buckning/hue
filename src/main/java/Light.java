import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;

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

    public void setBrightness(int brightness) {
        LightState lightState = new LightState();
        lightState.setBrightness(brightness);
        lightPoint.updateState(lightState);
    }
}
