package zkhlnk.v.lightsmqttwithpahotestproject.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Client implements MqttCallback {
    private MqttClient client;
    private MqttConnectOptions conOpt;

    public Client(String brokerUrl, String clientId, boolean cleanSession) {
        try {
            conOpt = new MqttConnectOptions();
            conOpt.setCleanSession(cleanSession);

            client = new MqttClient(brokerUrl, clientId);

            client.setCallback(this);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topicName, int qos, byte[] payload) throws MqttException {
        client.connect(conOpt);

        MqttMessage message = new MqttMessage(payload);
        message.setQos(qos);

        client.publish(topicName, message);

        client.disconnect();
    }

    public void subscribe(String topicName, int qos) throws MqttException {
        client.connect(conOpt);

        client.subscribe(topicName, qos);
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
    }
}
