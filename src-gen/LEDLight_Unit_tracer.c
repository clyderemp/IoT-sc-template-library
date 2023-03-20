/** Generated by itemis CREATE code generator. */
/* Implements a standard statechart trace handler which adapts to the
 * YET tracing library.
 */


#include "LEDLight_Unit_tracer.h"
#include "LEDLight_Unit_meta.h"

#include <string.h>
#include <stdlib.h>


#ifndef SC_UNUSED
#define SC_UNUSED(P) (void)(P)
#endif

static sc_string default_scope_name = "LEDLight_Unit";

static yet_value_serializer feature_value_serializer[8];



static void init_feature_value_serializer() {
	feature_value_serializer[0] = yet_serialize_void;
	feature_value_serializer[1] = yet_serialize_void;
	feature_value_serializer[2] = yet_serialize_void;
	feature_value_serializer[3] = yet_serialize_bool;
	feature_value_serializer[4] = yet_serialize_bool;
	feature_value_serializer[5] = yet_int_serializer_function(sizeof(sc_integer), true);
	feature_value_serializer[6] = yet_serialize_void;
	feature_value_serializer[7] = yet_serialize_void;
}

static char* featureName(sc_integer featureId)
{
	return LEDLight_Unit_meta_feature_names[featureId];
}

static char* featureValue(sc_integer featureId, const void* valuePtr, char* buf)
{
	return (feature_value_serializer[featureId])(valuePtr, buf);
}

static char* stateName(sc_integer stateId)
{
	return LEDLight_Unit_meta_state_names[stateId];
}

static yet_error dispatchMessage(yet_scope *scope, yet_message * msg, char *fqn);

void lEDLight_Unit_init_sc_tracer(yet_sc_tracer *tracer, LEDLight_Unit* machine, sc_string name)
{
	if (tracer != sc_null) {
		yet_init_sc_tracer(tracer, machine, &dispatchMessage);

		tracer->scope.name = default_scope_name;
		if (name != sc_null) {
			tracer->scope.name = name;
		}
		tracer->traceinfoProvider.featureName = featureName;
		tracer->traceinfoProvider.featureValue = featureValue;
		tracer->traceinfoProvider.stateName = stateName;
		
		machine->trace_handler = &(tracer->traceEventHandler);
	}
	init_feature_value_serializer();
}


/* Implementation of yet_handler callback function. Handles incoming stimuli and call appropriate state machine function. */
static yet_error dispatchMessage(yet_scope *scope, yet_message * msg, char *fqn)
{
	yet_sc_tracer* tracer = scope->instance;
	char* member;
	member = fqn;
	
	
	if(strcmp(member, "device.on") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		lEDLight_Unit_device_raise_on(tracer->machine);
		return 0;
	}
	if(strcmp(member, "device.off") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		lEDLight_Unit_device_raise_off(tracer->machine);
		return 0;
	}
	if(strcmp(member, "Brightness.up") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		lEDLight_Unit_Brightness_raise_up(tracer->machine);
		return 0;
	}
	if(strcmp(member, "Brightness.down") == 0) {
		/* Skip one message; this should not be re-raised on host */
		/* TODO: this is not optimal check if we can get rid of it */
		tracer->skip_raised_in_event++; 
		lEDLight_Unit_Brightness_raise_down(tracer->machine);
		return 0;
	}
	if(strcmp(member, "device.isOn") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		lEDLight_Unit_device_set_isOn(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Bulb.isOn") == 0) {
		sc_boolean converted = (msg->value[0] == 't');
		lEDLight_Unit_Bulb_set_isOn(tracer->machine, converted);
		return 0;
	}
	if(strcmp(member, "Brightness.level") == 0) {
		sc_integer converted = (sc_integer)atol(msg->value);
		lEDLight_Unit_Brightness_set_level(tracer->machine, converted);
		return 0;
	}
	
	
	return YET_ERR_INVALID_KEY;
}
