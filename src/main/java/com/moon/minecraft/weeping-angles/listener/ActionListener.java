import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerDemo implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the action event here
        System.out.println("Button clicked!");
    }

    public static void main(String[] args) {
        // Create an instance of the ActionListenerDemo class
        ActionListenerDemo listener = new ActionListenerDemo();

        // Simulate an action event (e.g., button click)
        ActionEvent actionEvent = new ActionEvent(new Object(), ActionEvent.ACTION_PERFORMED, "");

        // Call the actionPerformed method manually
        listener.actionPerformed(actionEvent);
    }
}
