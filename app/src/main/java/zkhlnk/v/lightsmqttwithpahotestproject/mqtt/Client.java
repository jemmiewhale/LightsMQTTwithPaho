package zkhlnk.v.lightsmqttwithpahotestproject.mqtt;

import android.os.Parcel;
import android.os.Parcelable;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class Client implements Parcelable {

    public static final int BEST_QOS = 2;

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

    public Client(String brokerUrl, String clientId, boolean cleanSession) throws MqttException {
        String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

        conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(cleanSession);

        client = new MqttClient(brokerUrl, clientId, dataStore);
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

    public void unsubscribe() throws MqttException {
        client.disconnect();
    }

    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
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
