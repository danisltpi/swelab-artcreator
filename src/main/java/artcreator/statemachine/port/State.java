package artcreator.statemachine.port;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface State {

	boolean isSubStateOf(State state);
	
	boolean isSuperStateOf(State state);

	public enum S implements State {

		CREATE_TEMPLATE, //Todo: Replace with better name?

		// SysOp: Import image
		IMPORT_IMAGE,
		INVALID_IMPORT,

		// SysOp: Choose templateType
		CHOOSE_TEMPLATE_TYPE,

		//Creating Template
		TEMPLATE_CREATED,

		START_PROCESS

		;

		private List<State> subStates;

		public static final S INITIAL_STATE = CREATE_TEMPLATE;

		private S(State... subS) {
			this.subStates = new ArrayList<>(Arrays.asList(subS));
		}
		
		@Override
		public boolean isSuperStateOf(State s) {
			boolean result = (s == null) || (this == s); // self contained
			for (State state : this.subStates) // or
				result |= state.isSuperStateOf(s); // contained in a substate!
			return result;
		}

		@Override
		public boolean isSubStateOf(State state) {
			return (state != null) && state.isSuperStateOf(this);
		}
	}

}