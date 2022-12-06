package com.wizeline.baz.utils;

import com.wizeline.baz.model.OperationData;

@FunctionalInterface
public interface BuildOperationData {
	OperationData operationData();
}
