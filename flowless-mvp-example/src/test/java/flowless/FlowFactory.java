package flowless;

/**
 * Created by Zhuinden on 2016.12.19..
 */

public class FlowFactory {
    public static Flow createFlow(KeyManager keyManager, ServiceProvider serviceProvider, History history) {
        return new Flow(keyManager, serviceProvider, history);
    }
}
