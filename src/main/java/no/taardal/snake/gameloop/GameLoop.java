package no.taardal.snake.gameloop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class GameLoop implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);
    private static final long ONE_SECOND_IN_MILLISECONDS = TimeUnit.SECONDS.toMillis(1);
    private static final float NANOSECONDS_PER_UPDATE = TimeUnit.SECONDS.toNanos(1) / 15;
    private static final int UPDATE_THRESHOLD = 1;

    private Listener listener;
    private float delta;
    private int frames;
    private int updates;
    private boolean running;

    public GameLoop(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        running = true;
        long lastTimeMillis = System.currentTimeMillis();
        long lastTimeNano = System.nanoTime();
        while (running) {
            long currentTimeNano = System.nanoTime();
            long nanosecondsSinceLastPass = currentTimeNano - lastTimeNano;
            delta += nanosecondsSinceLastPass / NANOSECONDS_PER_UPDATE;
            lastTimeNano = currentTimeNano;
            if (delta >= UPDATE_THRESHOLD) {
                listener.onHandleInput();
                listener.onUpdate();
                listener.onDraw();
                updates++;
                frames++;
                delta--;
            }
            if (System.currentTimeMillis() - lastTimeMillis > ONE_SECOND_IN_MILLISECONDS) {
                lastTimeMillis += ONE_SECOND_IN_MILLISECONDS;
                LOGGER.info(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }

    public void stop() {
        running = false;
    }

    public interface Listener {

        void onHandleInput();
        void onUpdate();
        void onDraw();

    }

}
