package com.pos_terminal_simulator.scheduler;

import com.pos_terminal_simulator.dto.HeartbeatRequest;
import com.pos_terminal_simulator.service.HeartbeatService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class HeartbeatScheduler {

    private final ScheduledExecutorService executorService;

    private final HeartbeatService heartbeatService;

    private ScheduledFuture<?> scheduledTask;


    public HeartbeatScheduler(
            HeartbeatService heartbeatService
    ) {

        this.heartbeatService =
                heartbeatService;

        this.executorService =
                Executors.newSingleThreadScheduledExecutor(
                        runnable -> {

                            Thread thread =
                                    new Thread(
                                            runnable,
                                            "heartbeat-scheduler"
                                    );

                            thread.setDaemon(true);

                            return thread;
                        }
                );
    }


    public synchronized void start(
            Supplier<HeartbeatRequest> heartbeatSupplier,
            long interval,
            TimeUnit timeUnit
    ) {

        stop();

        scheduledTask =
                executorService.scheduleAtFixedRate(

                        () -> {

                            try {

                                HeartbeatRequest request =
                                        heartbeatSupplier.get();

                                heartbeatService
                                        .sendHeartbeat(request);

                                System.out.println(
                                        "Automatic heartbeat sent."
                                );

                            } catch (Exception e) {

                                System.err.println(
                                        "Automatic heartbeat failed: "
                                                + e.getMessage()
                                );
                            }

                        },

                        0,
                        interval,
                        timeUnit
                );
    }


    public synchronized void stop() {

        if (scheduledTask != null) {

            scheduledTask.cancel(false);

            scheduledTask = null;
        }
    }


    public synchronized boolean isRunning() {

        return scheduledTask != null
                && !scheduledTask.isCancelled()
                && !scheduledTask.isDone();
    }


    public void shutdown() {

        stop();

        executorService.shutdownNow();
    }
}