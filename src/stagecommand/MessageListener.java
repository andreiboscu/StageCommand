package stagecommand;

public interface MessageListener {
	void onMessageReceived(String message);
    String getStation();   // NOU
}
