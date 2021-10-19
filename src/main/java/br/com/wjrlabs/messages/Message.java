package br.com.wjrlabs.messages;

public interface Message {

	public byte[] encode();

	public void decode(byte[] buffer);
}
