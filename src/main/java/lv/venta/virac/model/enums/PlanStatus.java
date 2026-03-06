package lv.venta.virac.model.enums;

public enum PlanStatus {
	/*
	 plan_open => kad tiko plāns atvērts, visu iespējams rediģēt
	 planned_frozen => plāna "PLANNED" sadaļu vairs nav iespējas rediģēt
	 done_frozen => plāns tiek aizvērts un tiek atvērts nākamā gada plāns ar statusu plan_open
	*/
	plan_open, planned_frozen, done_frozen
}
