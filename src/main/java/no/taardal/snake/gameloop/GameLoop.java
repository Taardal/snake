package no.taardal.snake.gameloop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class GameLoop implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);
    private static final long ONE_SECOND_IN_MILLISECONDS = TimeUnit.SECONDS.toMillis(1);
    private static final long ONE_SECOND_IN_NANOSECONDS = TimeUnit.SECONDS.toNanos(1);
    private static final float NANOSECONDS_PER_UPDATE = ONE_SECOND_IN_NANOSECONDS / 60;

    private Listener listener;
    private boolean running;
    private int frames;
    private int updates;
    private float updateThreshold;
    private float nanosecondsSinceLastUpdate;

    public GameLoop(Listener listener) {
        this.listener = listener;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        running = true;
        long lastTimeMillis = System.currentTimeMillis();
        long lastTimeNano = System.nanoTime();
        while (running) {
            long currentTimeNano = System.nanoTime();
            long nanosecondsSinceLastPass = currentTimeNano - lastTimeNano;
            nanosecondsSinceLastUpdate += nanosecondsSinceLastPass;
            updateThreshold += nanosecondsSinceLastPass / NANOSECONDS_PER_UPDATE;
            lastTimeNano = currentTimeNano;
            if (updateThreshold >= 1) {
                listener.onUpdate();
                listener.onDraw();
                updates++;
                updateThreshold--;
                nanosecondsSinceLastUpdate = 0;
                frames++;
            }
            if (System.currentTimeMillis() - lastTimeMillis > ONE_SECOND_IN_MILLISECONDS) {
                lastTimeMillis += ONE_SECOND_IN_MILLISECONDS;
                LOGGER.info(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }

    public interface Listener {

        void onUpdate();
        void onDraw();

    }

}
