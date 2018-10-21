import com.philips.lighting.hue.sdk.wrapper.Persistence;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedEvent;
import com.philips.lighting.hue.sdk.wrapper.connection.ConnectionEvent;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeBuilder;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() throws Exception {
        System.load(new File("./libs/libhuesdk.dylib").getAbsolutePath());
        String hueBridge = System.getenv("HUE_BRIDGE");
        String username = System.getenv("HUE_USERNAME");

        Persistence.setStorageLocation("./storage", "mymac");
        Bridge bridge = new BridgeBuilder("app name", "device name")
                .setIpAddress(hueBridge)
                .setUserName(username)
                .setConnectionType(BridgeConnectionType.LOCAL)
                .setBridgeConnectionCallback(bridgeConnectionCallback)
                .addBridgeStateUpdatedCallback(bridgeStateUpdatedCallback)
                .build();
        System.out.println("Connecting...");
        bridge.connect();
        Thread.sleep(1000); // allow the connection to complete. bridge.connect() is async
        Lights lights = new Lights(bridge);

        Light light = lights.getByName("Lightstrip").get();
        light.turnOn();
        light.setBrightness(0);
        for (int i = 0; i < 100; i++) {
            light.setBrightness(i);
            Thread.sleep(100);
        }

        light.turnOff();

        System.out.println("All lights: " + lights.getAllNames());
        bridge.disconnect();
    }

    private BridgeConnectionCallback bridgeConnectionCallback = new BridgeConnectionCallback() {
        @Override
        public void onConnectionEvent(BridgeConnection bridgeConnection, ConnectionEvent connectionEvent) {
            System.out.println("Connection event: " + connectionEvent);
        }

        @Override
        public void onConnectionError(BridgeConnection bridgeConnection, List<HueError> list) {
            for (HueError error : list) {
                System.out.println("Connection error: " + error.toString());
            }
        }
    };

    private BridgeStateUpdatedCallback bridgeStateUpdatedCallback = new BridgeStateUpdatedCallback() {
        @Override
        public void onBridgeStateUpdated(Bridge bridge, BridgeStateUpdatedEvent bridgeStateUpdatedEvent) {
            System.out.println("Bridge state updated event: " + bridgeStateUpdatedEvent);

            switch (bridgeStateUpdatedEvent) {
                case INITIALIZED:
                    // The bridge state was fully initialized for the first time.
                    // It is now safe to perform operations on the bridge state.
                    System.out.println("Connected!");
                    break;

                case LIGHTS_AND_GROUPS:
                    // At least one light was updated.
                    break;

                default:
                    break;
            }
        }
    };
}
