package com.docum.domain;

public enum ContainerStateEnum {
	NOT_HANDLED("Не отработан"),
	HANDLED("Отработан"),
	READY("Подготовлен"),
	REPORT_READY("Отчет готов"),
	FINISHED("Отчет отправлен"),
	ABANDONED("Невозможно отработать");

	private final String name;
	
	private ContainerStateEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}
	
	public static ContainerStateEnum[] getFinalStates() {
		return new ContainerStateEnum[]{FINISHED, ABANDONED};
	}

	public static ContainerStateEnum[] getNotFinalStates() {
		return new ContainerStateEnum[]{NOT_HANDLED, HANDLED, READY, REPORT_READY};
	}
}
