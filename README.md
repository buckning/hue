Currently this application can only run on MacOS. 

This assumes that the HueBridge IP address or hostname is already identified and the user accessing the system.
These need to be defined with the following environment variables:
* **HUE_BRIDGE**
* **HUE_USERNAME** 


Install Hue jar locally
```
mvn install:install-file -Dfile=./libs/huecppsdk-wrapper.jar -DgroupId=com.philips.lighting -DartifactId=huecppsdk-wrapper -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
```

Libs were downloaded from https://github.com/PhilipsHue/HueSDK/tree/master/HueSDK/Apple/MacOS/Java
