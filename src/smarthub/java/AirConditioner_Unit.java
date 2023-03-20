/** Generated by itemis CREATE code generator. */
package smarthub.java;

import com.yakindu.core.IEventDriven;
import com.yakindu.core.IStatemachine;
import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AirConditioner_Unit implements ITimed, IEventDriven {
	public static class Metric {
		private AirConditioner_Unit parent;
		
		public Metric(AirConditioner_Unit parent) {
			this.parent = parent;
		}
		private boolean convertToFahrenheit;
		
		
		public void raiseConvertToFahrenheit() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					convertToFahrenheit = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean convertToCelsius;
		
		
		public void raiseConvertToCelsius() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					convertToCelsius = true;
				});
				parent.runCycle();
			}
		}
		
		private String temp;
		
		public synchronized String getTemp() {
			synchronized(parent) {
				return temp;
			}
		}
		
		public void setTemp(String value) {
			synchronized(parent) {
				this.temp = value;
			}
		}
		
	}
	
	public static class Heat {
		private AirConditioner_Unit parent;
		
		public Heat(AirConditioner_Unit parent) {
			this.parent = parent;
		}
		private boolean increase;
		
		
		public void raiseIncrease() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					increase = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean decrease;
		
		
		public void raiseDecrease() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					decrease = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean on;
		
		public synchronized boolean getOn() {
			synchronized(parent) {
				return on;
			}
		}
		
		public void setOn(boolean value) {
			synchronized(parent) {
				this.on = value;
			}
		}
		
	}
	
	public static class Cool {
		private AirConditioner_Unit parent;
		
		public Cool(AirConditioner_Unit parent) {
			this.parent = parent;
		}
		private boolean increase;
		
		
		public void raiseIncrease() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					increase = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean decrease;
		
		
		public void raiseDecrease() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					decrease = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean on;
		
		public synchronized boolean getOn() {
			synchronized(parent) {
				return on;
			}
		}
		
		public void setOn(boolean value) {
			synchronized(parent) {
				this.on = value;
			}
		}
		
	}
	
	protected Metric metric;
	
	protected Heat heat;
	
	protected Cool cool;
	
	public enum State {
		_AIRCONDITIONER__OFF,
		_AIRCONDITIONER__MONITORROOMTEMP,
		_AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_MAINTAINING,
		_AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING,
		_AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_HEATINGUP,
		_AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_DETERMINETEMPCHANGE,
		_AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_COOLDOWN,
		_AIRCONDITIONER__MONITORROOMTEMP_CONVERTINGTEMP_INITIALIZECONVERTER,
		_AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_MONITORING,
		_AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_INCREASETEMP,
		_AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_DECREASETEMP,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[3];
	
	private ITimerService timerService;
	
	private final boolean[] timeEvents = new boolean[3];
	
	private BlockingQueue<Runnable> inEventQueue = new LinkedBlockingQueue<Runnable>();
	private boolean completed;
	
	protected boolean getCompleted() {
		synchronized(AirConditioner_Unit.this) {
			return completed;
		}
	}
	
	protected void setCompleted(boolean value) {
		synchronized(AirConditioner_Unit.this) {
			this.completed = value;
		}
	}
	private boolean doCompletion;
	
	protected boolean getDoCompletion() {
		synchronized(AirConditioner_Unit.this) {
			return doCompletion;
		}
	}
	
	protected void setDoCompletion(boolean value) {
		synchronized(AirConditioner_Unit.this) {
			this.doCompletion = value;
		}
	}
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		synchronized(AirConditioner_Unit.this) {
			return isExecuting;
		}
	}
	
	protected void setIsExecuting(boolean value) {
		synchronized(AirConditioner_Unit.this) {
			this.isExecuting = value;
		}
	}
	private long stateConfVectorPosition;
	
	protected long getStateConfVectorPosition() {
		synchronized(AirConditioner_Unit.this) {
			return stateConfVectorPosition;
		}
	}
	
	protected void setStateConfVectorPosition(long value) {
		synchronized(AirConditioner_Unit.this) {
			this.stateConfVectorPosition = value;
		}
	}
	public AirConditioner_Unit() {
		metric = new Metric(this);
		heat = new Heat(this);
		cool = new Cool(this);
		for (int i = 0; i < 3; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		setTempChange(0l);
		
		setRoomTemp(18l);
		
		metric.setTemp("Celsius");
		
		heat.setOn(false);
		
		cool.setOn(false);
		
		isExecuting = false;
	}
	
	public synchronized void enter() {
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		enterSequence__AirConditioner__default();
		doCompletion = false;
		
		do { 
			if (getCompleted()) {
				doCompletion = true;
			}
			completed = false;
			
			microStep();
			
			doCompletion = false;
		} while (getCompleted());
		
		isExecuting = false;
	}
	
	public synchronized void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		exitSequence__AirConditioner_();
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public synchronized boolean isActive() {
		return stateVector[0] != State.$NULLSTATE$||stateVector[1] != State.$NULLSTATE$||stateVector[2] != State.$NULLSTATE$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public synchronized boolean isFinal() {
		return false;
	}
	private void clearInEvents() {
		toggle = false;
		temp_up = false;
		temp_down = false;
		metric.convertToFahrenheit = false;
		metric.convertToCelsius = false;
		heat.increase = false;
		heat.decrease = false;
		cool.increase = false;
		cool.decrease = false;
		timeEvents[0] = false;
		timeEvents[1] = false;
		timeEvents[2] = false;
	}
	
	private void microStep() {
		long transitioned = -1l;
		
		stateConfVectorPosition = 0l;
		
		switch (stateVector[0]) {
		case _AIRCONDITIONER__OFF:
			transitioned = _AirConditioner__Off_react(transitioned);
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_MAINTAINING:
			transitioned = _AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining_react(transitioned);
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_HEATINGUP:
			transitioned = _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp_react(transitioned);
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_DETERMINETEMPCHANGE:
			transitioned = _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange_react(transitioned);
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_COOLDOWN:
			transitioned = _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown_react(transitioned);
			break;
		default:
			break;
		}
		
		if (getStateConfVectorPosition()<1l) {
			switch (stateVector[1]) {
			case _AIRCONDITIONER__MONITORROOMTEMP_CONVERTINGTEMP_INITIALIZECONVERTER:
				transitioned = _AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter_react(transitioned);
				break;
			default:
				break;
			}
		}
		if (getStateConfVectorPosition()<2l) {
			switch (stateVector[2]) {
			case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_MONITORING:
				_AirConditioner__MonitorRoomTemp_ChangingTemp_Monitoring_react(transitioned);
				break;
			case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_INCREASETEMP:
				_AirConditioner__MonitorRoomTemp_ChangingTemp_IncreaseTemp_react(transitioned);
				break;
			case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_DECREASETEMP:
				_AirConditioner__MonitorRoomTemp_ChangingTemp_DecreaseTemp_react(transitioned);
				break;
			default:
				break;
			}
		}
	}
	
	private void runCycle() {
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		nextEvent();
		do { 
			doCompletion = false;
			
			do { 
				if (getCompleted()) {
					doCompletion = true;
				}
				completed = false;
				
				microStep();
				
				doCompletion = false;
			} while (getCompleted());
			
			clearInEvents();
		} while (nextEvent());
		
		isExecuting = false;
	}
	
	protected boolean nextEvent() {
		if(!inEventQueue.isEmpty()) {
			inEventQueue.poll().run();
			return true;
		}
		return false;
	}
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public synchronized boolean isStateActive(State state) {
	
		switch (state) {
		case _AIRCONDITIONER__OFF:
			return stateVector[0] == State._AIRCONDITIONER__OFF;
		case _AIRCONDITIONER__MONITORROOMTEMP:
			return stateVector[0].ordinal() >= State.
					_AIRCONDITIONER__MONITORROOMTEMP.ordinal()&& stateVector[0].ordinal() <= State._AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_DECREASETEMP.ordinal();
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_MAINTAINING:
			return stateVector[0] == State._AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_MAINTAINING;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING:
			return stateVector[0].ordinal() >= State.
					_AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING.ordinal()&& stateVector[0].ordinal() <= State._AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_COOLDOWN.ordinal();
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_HEATINGUP:
			return stateVector[0] == State._AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_HEATINGUP;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_DETERMINETEMPCHANGE:
			return stateVector[0] == State._AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_DETERMINETEMPCHANGE;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_COOLDOWN:
			return stateVector[0] == State._AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_COOLDOWN;
		case _AIRCONDITIONER__MONITORROOMTEMP_CONVERTINGTEMP_INITIALIZECONVERTER:
			return stateVector[1] == State._AIRCONDITIONER__MONITORROOMTEMP_CONVERTINGTEMP_INITIALIZECONVERTER;
		case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_MONITORING:
			return stateVector[2] == State._AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_MONITORING;
		case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_INCREASETEMP:
			return stateVector[2] == State._AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_INCREASETEMP;
		case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_DECREASETEMP:
			return stateVector[2] == State._AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_DECREASETEMP;
		default:
			return false;
		}
	}
	
	public synchronized void setTimerService(ITimerService timerService) {
		this.timerService = timerService;
	}
	
	public ITimerService getTimerService() {
		return timerService;
	}
	
	public synchronized void raiseTimeEvent(int eventID) {
		inEventQueue.add(() -> {
			timeEvents[eventID] = true;
		});
		runCycle();
	}
	
	public Metric metric() {
		return metric;
	}
	
	public Heat heat() {
		return heat;
	}
	
	public Cool cool() {
		return cool;
	}
	
	
	private boolean toggle;
	
	
	public void raiseToggle() {
		synchronized(AirConditioner_Unit.this) {
			inEventQueue.add(() -> {
				toggle = true;
			});
			runCycle();
		}
	}
	
	private boolean temp_up;
	
	
	public void raiseTemp_up() {
		synchronized(AirConditioner_Unit.this) {
			inEventQueue.add(() -> {
				temp_up = true;
			});
			runCycle();
		}
	}
	
	private boolean temp_down;
	
	
	public void raiseTemp_down() {
		synchronized(AirConditioner_Unit.this) {
			inEventQueue.add(() -> {
				temp_down = true;
			});
			runCycle();
		}
	}
	
	private long tempChange;
	
	public synchronized long getTempChange() {
		synchronized(AirConditioner_Unit.this) {
			return tempChange;
		}
	}
	
	public void setTempChange(long value) {
		synchronized(AirConditioner_Unit.this) {
			this.tempChange = value;
		}
	}
	
	private long roomTemp;
	
	public synchronized long getRoomTemp() {
		synchronized(AirConditioner_Unit.this) {
			return roomTemp;
		}
	}
	
	public void setRoomTemp(long value) {
		synchronized(AirConditioner_Unit.this) {
			this.roomTemp = value;
		}
	}
	
	/* Entry action for state 'Maintaining'. */
	private void entryAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining() {
		timerService.setTimer(this, 0, 500l, false);
	}
	
	/* Entry action for state 'RoomTempChanging'. */
	private void entryAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging() {
		timerService.setTimer(this, 1, 900l, false);
		
		timerService.setTimer(this, 2, (1l * 1000l), false);
	}
	
	/* Entry action for state 'HeatingUp'. */
	private void entryAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp() {
		roomTemp++;
		
		tempChange--;
		
		heat.setOn(true);
	}
	
	/* Entry action for state 'CoolDown'. */
	private void entryAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown() {
		roomTemp--;
		
		tempChange++;
		
		cool.setOn(true);
	}
	
	private void entryAction__AirConditioner__MonitorRoomTemp_ChangingTemp_IncreaseTemp() {
		tempChange++;
		
		completed = true;
	}
	
	private void entryAction__AirConditioner__MonitorRoomTemp_ChangingTemp_DecreaseTemp() {
		tempChange--;
		
		completed = true;
	}
	
	/* Exit action for state 'Maintaining'. */
	private void exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining() {
		timerService.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'RoomTempChanging'. */
	private void exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging() {
		timerService.unsetTimer(this, 1);
		
		timerService.unsetTimer(this, 2);
		
		heat.setOn(false);
		
		cool.setOn(false);
	}
	
	/* 'default' enter sequence for state Off */
	private void enterSequence__AirConditioner__Off_default() {
		stateVector[0] = State._AIRCONDITIONER__OFF;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state MonitorRoomTemp */
	private void enterSequence__AirConditioner__MonitorRoomTemp_default() {
		enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_default();
		enterSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_default();
		enterSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_default();
	}
	
	/* 'default' enter sequence for state Maintaining */
	private void enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining_default() {
		entryAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining();
		stateVector[0] = State._AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_MAINTAINING;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state RoomTempChanging */
	private void enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_default() {
		entryAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
		enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_default();
	}
	
	/* 'default' enter sequence for state HeatingUp */
	private void enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp_default() {
		entryAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp();
		stateVector[0] = State._AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_HEATINGUP;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state DetermineTempChange */
	private void enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange_default() {
		stateVector[0] = State._AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_DETERMINETEMPCHANGE;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state CoolDown */
	private void enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown_default() {
		entryAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown();
		stateVector[0] = State._AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_COOLDOWN;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state InitializeConverter */
	private void enterSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter_default() {
		stateVector[1] = State._AIRCONDITIONER__MONITORROOMTEMP_CONVERTINGTEMP_INITIALIZECONVERTER;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for state Monitoring */
	private void enterSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_Monitoring_default() {
		stateVector[2] = State._AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_MONITORING;
		stateConfVectorPosition = 2;
	}
	
	/* 'default' enter sequence for state IncreaseTemp */
	private void enterSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_IncreaseTemp_default() {
		entryAction__AirConditioner__MonitorRoomTemp_ChangingTemp_IncreaseTemp();
		stateVector[2] = State._AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_INCREASETEMP;
		stateConfVectorPosition = 2;
	}
	
	/* 'default' enter sequence for state DecreaseTemp */
	private void enterSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_DecreaseTemp_default() {
		entryAction__AirConditioner__MonitorRoomTemp_ChangingTemp_DecreaseTemp();
		stateVector[2] = State._AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_DECREASETEMP;
		stateConfVectorPosition = 2;
	}
	
	/* 'default' enter sequence for region <AirConditioner> */
	private void enterSequence__AirConditioner__default() {
		react__AirConditioner___entry_Default();
	}
	
	/* 'default' enter sequence for region MonitoringTemp */
	private void enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_default() {
		react__AirConditioner__MonitorRoomTemp_MonitoringTemp__entry_Default();
	}
	
	/* 'default' enter sequence for region TemperatureChange */
	private void enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_default() {
		react__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange__entry_Default();
	}
	
	/* 'default' enter sequence for region ConvertingTemp */
	private void enterSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_default() {
		react__AirConditioner__MonitorRoomTemp_ConvertingTemp__entry_Default();
	}
	
	/* 'default' enter sequence for region ChangingTemp */
	private void enterSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_default() {
		react__AirConditioner__MonitorRoomTemp_ChangingTemp__entry_Default();
	}
	
	/* Default exit sequence for state Off */
	private void exitSequence__AirConditioner__Off() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state MonitorRoomTemp */
	private void exitSequence__AirConditioner__MonitorRoomTemp() {
		exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp();
		exitSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp();
		exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp();
	}
	
	/* Default exit sequence for state Maintaining */
	private void exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
		
		exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining();
	}
	
	/* Default exit sequence for state RoomTempChanging */
	private void exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging() {
		exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange();
		exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
	}
	
	/* Default exit sequence for state HeatingUp */
	private void exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state DetermineTempChange */
	private void exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state CoolDown */
	private void exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state InitializeConverter */
	private void exitSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter() {
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for state Monitoring */
	private void exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_Monitoring() {
		stateVector[2] = State.$NULLSTATE$;
		stateConfVectorPosition = 2;
	}
	
	/* Default exit sequence for state IncreaseTemp */
	private void exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_IncreaseTemp() {
		stateVector[2] = State.$NULLSTATE$;
		stateConfVectorPosition = 2;
	}
	
	/* Default exit sequence for state DecreaseTemp */
	private void exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_DecreaseTemp() {
		stateVector[2] = State.$NULLSTATE$;
		stateConfVectorPosition = 2;
	}
	
	/* Default exit sequence for region <AirConditioner> */
	private void exitSequence__AirConditioner_() {
		switch (stateVector[0]) {
		case _AIRCONDITIONER__OFF:
			exitSequence__AirConditioner__Off();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_MAINTAINING:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_HEATINGUP:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp();
			exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_DETERMINETEMPCHANGE:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange();
			exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_COOLDOWN:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown();
			exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
			break;
		default:
			break;
		}
		
		switch (stateVector[1]) {
		case _AIRCONDITIONER__MONITORROOMTEMP_CONVERTINGTEMP_INITIALIZECONVERTER:
			exitSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter();
			break;
		default:
			break;
		}
		
		switch (stateVector[2]) {
		case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_MONITORING:
			exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_Monitoring();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_INCREASETEMP:
			exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_IncreaseTemp();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_DECREASETEMP:
			exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_DecreaseTemp();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region MonitoringTemp */
	private void exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp() {
		switch (stateVector[0]) {
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_MAINTAINING:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_HEATINGUP:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp();
			exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_DETERMINETEMPCHANGE:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange();
			exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_COOLDOWN:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown();
			exitAction__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region TemperatureChange */
	private void exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange() {
		switch (stateVector[0]) {
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_HEATINGUP:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_DETERMINETEMPCHANGE:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_MONITORINGTEMP_ROOMTEMPCHANGING_TEMPERATURECHANGE_COOLDOWN:
			exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region ConvertingTemp */
	private void exitSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp() {
		switch (stateVector[1]) {
		case _AIRCONDITIONER__MONITORROOMTEMP_CONVERTINGTEMP_INITIALIZECONVERTER:
			exitSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region ChangingTemp */
	private void exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp() {
		switch (stateVector[2]) {
		case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_MONITORING:
			exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_Monitoring();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_INCREASETEMP:
			exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_IncreaseTemp();
			break;
		case _AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_DECREASETEMP:
			exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_DecreaseTemp();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react__AirConditioner___entry_Default() {
		enterSequence__AirConditioner__Off_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__AirConditioner__MonitorRoomTemp_MonitoringTemp__entry_Default() {
		enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange__entry_Default() {
		enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__AirConditioner__MonitorRoomTemp_ConvertingTemp__entry_Default() {
		enterSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__AirConditioner__MonitorRoomTemp_ChangingTemp__entry_Default() {
		enterSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_Monitoring_default();
	}
	
	private long react(long transitioned_before) {
		return transitioned_before;
	}
	
	private long _AirConditioner__Off_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (toggle) {
					exitSequence__AirConditioner__Off();
					enterSequence__AirConditioner__MonitorRoomTemp_default();
					react(0l);
					
					transitioned_after = 0l;
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (toggle) {
					exitSequence__AirConditioner__MonitorRoomTemp();
					enterSequence__AirConditioner__Off_default();
					react(0l);
					
					transitioned_after = 2l;
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (getTempChange()!=0l) {
					exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining();
					enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_default();
					transitioned_after = 0l;
				} else {
					if (timeEvents[0]) {
						exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining();
						timeEvents[0] = false;
						enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining_default();
						transitioned_after = 0l;
					}
				}
			}
		}
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (((timeEvents[1]) && (getTempChange()==0l))) {
					exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
					timeEvents[1] = false;
					enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_Maintaining_default();
					transitioned_after = 0l;
				} else {
					if (timeEvents[2]) {
						exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging();
						timeEvents[2] = false;
						enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_default();
						transitioned_after = 0l;
					}
				}
			}
		}
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			transitioned_after = _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (getTempChange()>0l) {
					exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange();
					enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_HeatingUp_default();
					_AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_react(0l);
					
					transitioned_after = 0l;
				} else {
					if (getTempChange()<0l) {
						exitSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_DetermineTempChange();
						enterSequence__AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown_default();
						_AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_react(0l);
						
						transitioned_after = 0l;
					}
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_TemperatureChange_CoolDown_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			transitioned_after = _AirConditioner__MonitorRoomTemp_MonitoringTemp_RoomTempChanging_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<1l) {
				if (metric.convertToFahrenheit) {
					exitSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter();
					setRoomTemp((((((roomTemp * 9l)) / 5l)) + 33l));
					
					metric.setTemp("Fahrenheit");
					
					enterSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter_default();
					transitioned_after = 1l;
				} else {
					if (metric.convertToCelsius) {
						exitSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter();
						setRoomTemp((((((roomTemp - 32l)) * 5l)) / 9l));
						
						metric.setTemp("Celsius");
						
						enterSequence__AirConditioner__MonitorRoomTemp_ConvertingTemp_InitializeConverter_default();
						transitioned_after = 1l;
					}
				}
			}
		}
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_ChangingTemp_Monitoring_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<2l) {
				if (temp_up) {
					exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_Monitoring();
					enterSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_IncreaseTemp_default();
					_AirConditioner__MonitorRoomTemp_react(0l);
					
					transitioned_after = 2l;
				} else {
					if (temp_down) {
						exitSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_Monitoring();
						enterSequence__AirConditioner__MonitorRoomTemp_ChangingTemp_DecreaseTemp_default();
						_AirConditioner__MonitorRoomTemp_react(0l);
						
						transitioned_after = 2l;
					}
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = _AirConditioner__MonitorRoomTemp_react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_ChangingTemp_IncreaseTemp_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (getDoCompletion()) {
			stateVector[2] = State.$NULLSTATE$;
			stateConfVectorPosition = 2;
			
			stateVector[2] = State._AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_MONITORING;
			stateConfVectorPosition = 2;
			
			_AirConditioner__MonitorRoomTemp_react(0l);
		} else {
			transitioned_after = _AirConditioner__MonitorRoomTemp_react(transitioned_before);
		}
		
		return transitioned_after;
	}
	
	private long _AirConditioner__MonitorRoomTemp_ChangingTemp_DecreaseTemp_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (getDoCompletion()) {
			stateVector[2] = State.$NULLSTATE$;
			stateConfVectorPosition = 2;
			
			stateVector[2] = State._AIRCONDITIONER__MONITORROOMTEMP_CHANGINGTEMP_MONITORING;
			stateConfVectorPosition = 2;
			
			_AirConditioner__MonitorRoomTemp_react(0l);
		} else {
			transitioned_after = _AirConditioner__MonitorRoomTemp_react(transitioned_before);
		}
		
		return transitioned_after;
	}
	
	/* Can be used by the client code to trigger a run to completion step without raising an event. */
	public synchronized void triggerWithoutEvent() {
		runCycle();
	}
}