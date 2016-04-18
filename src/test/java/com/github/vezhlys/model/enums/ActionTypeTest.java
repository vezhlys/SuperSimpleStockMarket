package com.github.vezhlys.model.enums;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class ActionTypeTest {

	@Test
	public void nullArgumentGivesUnkownType() {
		Assert.assertEquals(ActionType.UNKOWN, ActionType.getAction(null));
	}

	@Test
	public void unknownArgumentGivesUnkownType() {
		Assert.assertEquals(ActionType.UNKOWN, ActionType.getAction(""));
		Assert.assertEquals(ActionType.UNKOWN, ActionType.getAction("neveragrument"));
	}

	@Test
	public void knownArgumentsGiveCorrespondingTypes() {
		String[] arguments = new String[] { "trade", "quit", "show", "help", "" };
		Assert.assertArrayEquals(ActionType.values(), getActionTypesFromArguments(arguments));
	}

	@Test
	public void knownShortArgumentsGivesCorrespondingTypes() {
		String[] arguments = new String[] { "t", "q", "s", "h", "" };
		Assert.assertArrayEquals(ActionType.values(), getActionTypesFromArguments(arguments));
	}

	@Test
	public void knownArgumentsAreTrimmedAndGivesCorrespondingTypes() {
		String[] arguments = new String[] { " t ", "quit ", " show", " h", " " };
		Assert.assertArrayEquals(ActionType.values(), getActionTypesFromArguments(arguments));
	}

	private ActionType[] getActionTypesFromArguments(String[] arguments) {
		return Arrays.stream(arguments).map(ActionType::getAction).toArray(ActionType[]::new);
	}

}
