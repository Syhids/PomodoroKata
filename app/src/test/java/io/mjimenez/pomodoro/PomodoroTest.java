package io.mjimenez.pomodoro;

import org.junit.Test;

import static io.mjimenez.pomodoro.Pomodoro.Estado.parado;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PomodoroTest {
    MockedTimeProvider mockedTimeProvider = new MockedTimeProvider();

    @Test
    public void whenInitIsCalled_interruptionShouldBeZero() throws Exception {
        assertEquals(0, new Pomodoro(mockedTimeProvider).init());
    }

    @Test
    public void whenIsJustCreated_itIsStopped() throws Exception {
        Pomodoro pomodoro = new Pomodoro(mockedTimeProvider);
        assertEquals(parado, pomodoro.estadoActual);
    }

    @Test
    public void whenInitIsCalledWithoutParams_timePomodoroIs25AsDefault() throws Exception {
        assertEquals(25, new Pomodoro(mockedTimeProvider).pomodoroTime);
    }

    @Test
    public void whenPomodoroIsCreated_initialDurationShouldBeAsDefined() throws Exception {
        assertEquals(60, new Pomodoro(mockedTimeProvider, 60).pomodoroTime);
    }

    //    Un pomodoro no termina si no ha sido arrancado previamente.
    @Test
    public void isPossibleToFinish() throws Exception {
        Pomodoro pomodoro = new Pomodoro(mockedTimeProvider);
        pomodoro.init();
        pomodoro.arrancar();

        assertEquals(true, pomodoro.isPossibleToFinish());
    }

    //    Un pomodoro no termina si no ha sido arrancado previamente.
    @Test
    public void whenPomodoroIsNotArrancado_isNotPossibleToFinish() throws Exception {
        Pomodoro pomodoro = new Pomodoro(mockedTimeProvider);
        pomodoro.init();

        assertEquals(false, pomodoro.isPossibleToFinish());
    }

    // Un pomodoro acaba cuando se agota su tiempo.
    @Test
    public void whenAnArrancadoPomodoroRuntimeIsZero_pomodoroIsFinished() throws Exception {
        Pomodoro pomodoro = new Pomodoro(mockedTimeProvider, 20);
        pomodoro.init();
        pomodoro.arrancar();

        minutesLater(26);

        assertTrue(pomodoro.isFinished());
    }

    @Test
    public void givenAnArrancadoPomodoro_whenTimePassedInLessThanPomodoroTime_pomodoroIsNotFinished() throws Exception {
        Pomodoro pomodoro = new Pomodoro(mockedTimeProvider, 20);
        pomodoro.init();
        pomodoro.arrancar();

        minutesLater(16);

        assertFalse(pomodoro.isFinished());
    }

    private void minutesLater(int minutes) {
        mockedTimeProvider.setTimeInMinutes(minutes);
    }

    class MockedTimeProvider implements TimeProvider {
        public int timeMinutes;

        public void setTimeInMinutes(int time) {
            this.timeMinutes = time;
        }

        @Override public int getCurrentTimeInMinutes() {
            return timeMinutes;
        }
    }
}