package com.game.exception;
/**
 * @author ramamohan.gogula
 *
 */
public class PlayerWonException extends GameException {
	String msg;

	public PlayerWonException(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "msg='" + msg;
	}
}
