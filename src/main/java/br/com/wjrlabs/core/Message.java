package br.com.wjrlabs.core;

public interface Message {

	public static final byte VERSION = 1;

	public abstract MessageKey getKey();

	public abstract byte[] encode();

	public abstract void decode(byte[] buffer);
}