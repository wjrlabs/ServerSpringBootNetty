package br.com.wjrlabs.core;

import java.io.Serializable;
import java.nio.ByteBuffer;

import br.com.wjrlabs.commom.types.MessageType;
import lombok.Getter;
import lombok.Setter;

/**
 * Message Identification
 * 
 * <p>
 * <table border="1">
 * 	<th>
 * 		<tr>
 * 			<td><b>Size (bytes)</b></td>
 * 			<td><b>Description</b></td>
 * 		</tr>
 * 	</th>
 * 	<tb>
 * 		<tr>
 * 			<td>1</td>
 * 			<td>Message ID</td>
 * 		</tr>
 * 		<tr>
 * 			<td>1</td>
 * 			<td>Protocol version</td>
 * 		</tr>
 * 	</tb>
 * </table>
 * <p>
 * 
 */
@Getter
@Setter
public class MessageKey implements Serializable {

	private static final long serialVersionUID = -5130447995235323090L;

	public static final int LENGTH = 2;
	private MessageType type;
	private byte version;
	
	public MessageKey(MessageType type, byte version) {
		this.type		= type;
		this.version	= version;
	}

	public byte[] encode() {
		ByteBuffer buffer = ByteBuffer.allocate(LENGTH);

		buffer.put(type.getValue());
		buffer.put(version);
		
		buffer.flip();
		return buffer.array();
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageKey other = (MessageKey) obj;
		if (type != other.type)
			return false;
		if (version != other.version)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(this.getClass().getName());
		result.append("[");
		result.append("type=").append(type).append(",");
		result.append("version=").append(version);
		result.append("]");
		
		return result.toString();
	}
}
