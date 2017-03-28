package io.mjimenez.pomodoro;

import static io.mjimenez.pomodoro.Pomodoro.Estado.corriendo;
import static io.mjimenez.pomodoro.Pomodoro.Estado.parado;

public class Pomodoro {
    final int TIME_POMODORO = 25;
    public int pomodoroTime;
    int numInterrupciones = 0;
    Estado estadoActual = parado;
    private TimeProvider timeProvider;
    private int startTime;

    public Pomodoro(TimeProvider timeProvider, int pomodoroTime) {
        this.timeProvider = timeProvider;
        this.pomodoroTime = pomodoroTime;
    }

    public Pomodoro(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
        this.pomodoroTime = TIME_POMODORO;
    }

    public boolean isFinished() {
        long currentTime = timeProvider.getCurrentTimeInMinutes();
        return currentTime - startTime >= TIME_POMODORO;
    }

    private void setEstadoActual(Estado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public void arrancar() {
        startTime = timeProvider.getCurrentTimeInMinutes();
        setEstadoActual(corriendo);
    }

    public boolean isPossibleToFinish() {
        return estadoActual.equals(corriendo);
    }

    public int init() {
        return numInterrupciones;
    }

    enum Estado {parado, corriendo, interrumpido}
}
