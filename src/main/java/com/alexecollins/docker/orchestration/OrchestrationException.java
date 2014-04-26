package com.alexecollins.docker.orchestration;

public class OrchestrationException extends RuntimeException {
	OrchestrationException(Throwable cause) {
		super(cause);
	}
}
