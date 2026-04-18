package com.recipeapp.model;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager implements ExpiryObserver {
    private List<String> notificationHistory;
    
    public NotificationManager() {
        this.notificationHistory = new ArrayList<>();
    }
    
    @Override
    public void update(PantryItem item) {
        String message = "ALERT: " + item.getIngredient().getName() + " expires on " + 
                         item.getExpiryDate() + "! Use it soon to avoid waste.";
        sendPushNotification(message);
        notificationHistory.add(message);
    }
    
    public void sendPushNotification(String message) {
        System.out.println("📱 NOTIFICATION: " + message);
        // In a real app, this would send to mobile device
    }
    
    public void checkExpiringItems(Pantry pantry) {
        List<PantryItem> expiringItems = pantry.getExpiringItems(2);
        for (PantryItem item : expiringItems) {
            update(item);
        }
    }
    
    public List<String> getNotificationHistory() {
        return new ArrayList<>(notificationHistory);
    }
}