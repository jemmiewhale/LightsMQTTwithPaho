package zkhlnk.v.lightsmqttwithpahotestproject.mqtt;

import android.os.Parcel;
import android.os.Parcelable;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Client implements MqttCallback, Parcelable {
    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel source) {
            return new Client(source);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
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

    private Client(Parcel source) {
        client = (MqttClient) source.readValue(null);
        conOpt = (MqttConnectOptions) source.readValue(null);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(client);
        dest.writeValue(conOpt);
    }
}
