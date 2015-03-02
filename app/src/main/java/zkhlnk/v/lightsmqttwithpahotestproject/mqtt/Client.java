package zkhlnk.v.lightsmqttwithpahotestproject.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class Client {

    public static final int BEST_QOS = 2;

    private MqttAsyncClient client;
    private MqttConnectOptions conOpt;

    private IMqttToken conToken;

    public Client(String brokerUrl, String clientId, boolean cleanSession) throws MqttException {
        String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

        conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(cleanSession);

        client = new MqttAsyncClient(brokerUrl, clientId, dataStore);
    }

    public void publish(String topicName, int qos, byte[] payload) throws MqttException {
        if(conToken == null) {
            conToken = client.connect(conOpt);
            conToken.waitForCompletion();
        }

        MqttMessage message = new MqttMessage(payload);
        message.setQos(qos);

        IMqttDeliveryToken pubToken = client.publish(topicName, message);
        //pubToken.waitForCompletion();
    }

    public void subscribe(String topicName, int qos) throws MqttException {
        if(conToken == null) {
            conToken = client.connect(conOpt);
            conToken.waitForCompletion();
        }

        IMqttToken subToken = client.subscribe(topicName, qos);
        //subToken.waitForCompletion();
    }

    public void unsubscribe() throws MqttException {
        IMqttToken discToken = client.disconnect();
        //discToken.waitForCompletion();
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
    }
}
