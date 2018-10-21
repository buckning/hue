import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Lights {

    private Map<String, Light> nameToLightMap = new HashMap<>();
    private Map<String, Light> idToLightMap = new HashMap<>();

    public Lights(Bridge bridge) {
        List<Device> lights = bridge.getBridgeState().getDevices(DomainType.LIGHT_POINT);

        for (Device device: lights) {
            nameToLightMap.put(device.getName(), new Light((LightPoint) device));
            idToLightMap.put(device.getIdentifier(), new Light((LightPoint) device));
        }
    }

    public Optional<Light> getByIdentifier(String id) {
        return Optional.ofNullable(idToLightMap.get(id));
    }

    public Optional<Light> getByName(String name) {
        return Optional.ofNullable(nameToLightMap.get(name));
    }

    public Set<String> getAllNames() {
        return nameToLightMap.keySet();
    }
}
