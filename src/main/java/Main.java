import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.util.UUID;
import java.util.concurrent.TimeUnit;



public class Main {

    private static String publisherId = UUID.randomUUID().toString();

    public static void main(String[] args) throws Exception {

        if(!SettingProg.openFileConfig()) System.exit(1);

        publisherId = SettingProg.getConfig().getClientID();

        IMqttClient publisher = new MqttClient("tcp://" +
                SettingProg.getConfig().getServIp() + ":" +
                SettingProg.getConfig().getServPort(), publisherId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setUserName(SettingProg.getConfig().getUserName());
        options.setPassword(SettingProg.getConfig().getPass().toCharArray());

        try {
            publisher.connect(options);
            System.out.println("Подключено к серверу MQTT");
            System.out.println("Client ID - " + publisherId);
        } catch ( Exception e) {
            System.err.println(e);
            System.out.println("Не возможно подключится к серверу MQTT");
            System.out.println("Client ID - " + publisherId);
            System.exit(1);
        }

        while (true) {
            try {
                EngineClock engineClock = new EngineClock(publisher);
                engineClock.call();
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }

            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
