//package stagecommand;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class MessageBoard {
//
//    private final List<MessageListener> listeners = new ArrayList<>();
//    private final List<String> commandHistory = new ArrayList<>();
//
//    public void addMessageListener(MessageListener l) {
//        listeners.add(l);
//    }
//
//    public void removeMessageListener(MessageListener l) {
//        listeners.remove(l);
//    }
//
//    public void sendMessageToTarget(String target, String message) {
//        String full = "TO: " + target + "\n" + message;
//        commandHistory.add(0, full);
//
//        for (MessageListener l : listeners) {
//            if (target.equals("ALL STATIONS") || l.getStation().equals(target)) {
//                l.onMessageReceived(full);
//            }
//        }
//    }
//
//    public List<String> getCommandHistory() {
//        return commandHistory;
//    }
//}



package stagecommand;

import java.util.*;

public class MessageBoard {
    private final List<MessageListener> listeners = new ArrayList<>();
    private final List<String> commandHistory = new ArrayList<>();
    private final Map<String, List<String>> stationHistory = new HashMap<>();
    
    public MessageBoard() {
        // Inițializează istoric pentru fiecare stație
        stationHistory.put("CURTAIN", new ArrayList<>());
        stationHistory.put("LIGHTS", new ArrayList<>());
        stationHistory.put("SOUND", new ArrayList<>());
        stationHistory.put("ALL", new ArrayList<>());
    }
    
    public void addMessageListener(MessageListener l) {
        listeners.add(l);
        // Când un departament se conectează, îi trimitem istoricul său
        sendHistoryToStation(l);
    }
    
    public void removeMessageListener(MessageListener l) {
        listeners.remove(l);
    }
    
    public void sendMessageToTarget(String target, String message) {
        String full = "TO: " + target + "\n" + message;
        commandHistory.add(0, full);
        
        // Salvează în istoricul pentru target
        if (target.equals("ALL STATIONS")) {
            // Pentru ALL, salvează la toate departamentele
            for (String station : Arrays.asList("CURTAIN", "LIGHTS", "SOUND")) {
                stationHistory.get(station).add(0, full);
            }
            stationHistory.get("ALL").add(0, full);
        } else {
            // Pentru un departament specific
            stationHistory.get(target).add(0, full);
            // De asemenea, salvează și pentru ALL (pentru istoric complet)
            stationHistory.get("ALL").add(0, full);
        }
        
        // Trimite mesajul live la departamentele conectate
        for (MessageListener l : listeners) {
            if (target.equals("ALL STATIONS") || l.getStation().equals(target)) {
                l.onMessageReceived(full);
            }
        }
    }
    
    // Trimite tot istoricul unui departament când se conectează
    private void sendHistoryToStation(MessageListener listener) {
        String station = listener.getStation();
        List<String> history = stationHistory.get(station);
        
        // Trimite mesajele istorice (de la cel mai vechi la cel mai nou)
        for (int i = history.size() - 1; i >= 0; i--) {
            String message = history.get(i);
            listener.onMessageReceived(message);
        }
    }
    
    public List<String> getCommandHistory() {
        return commandHistory;
    }
    
    // Metodă nouă pentru a obține istoricul unui departament
    public List<String> getStationHistory(String station) {
        return stationHistory.getOrDefault(station, new ArrayList<>());
    }
}