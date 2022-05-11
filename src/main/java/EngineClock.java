import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public class EngineClock implements Callable<Void> {

    private IMqttClient client;
    private static SimpleDateFormat  Hour = new SimpleDateFormat("HH");
    private static SimpleDateFormat  Minutes = new SimpleDateFormat("mm");
    private static String oldHour = "";
    private static String oldMinutes = "";

    public EngineClock(IMqttClient client) {
        this.client = client;
    }
    @Override
    public Void call() throws Exception {
        if(!client.isConnected()) {
            return null;
        }
        Date dateNow = new Date();

        if(!Minutes.format(dateNow).equals(oldMinutes)) {
            oldMinutes = Minutes.format(dateNow);
            MqttMessage msg = new MqttMessage(oldMinutes.getBytes());
            msg.setQos(0);
            msg.setRetained(true);
            client.publish("/SYSTEM/Minutes", msg);
        }

        if(!Hour.format(dateNow).equals(oldHour)) {
            oldHour = Hour.format(dateNow);
            MqttMessage msg = new MqttMessage(oldHour.getBytes());
            msg.setQos(0);
            msg.setRetained(true);
            client.publish("/SYSTEM/Hour", msg);
        }
        return null;
    }

    private MqttMessage readEngineClock() {

        byte[] payload = new String("18:41").getBytes();
        return new MqttMessage(payload);
    }
}
