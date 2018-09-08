/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count.cache;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author orlei
 *
 */
@RedisHash("Numbers")
public class Counter implements Serializable {

	private static final long serialVersionUID = 5454208998230659228L;
	
	private int index;
	
	@Id
	private String number;
	
	private long quantity = 0;

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public Counter setNumber(String number) {
		if (Objects.nonNull(number) && !number.isEmpty()) {
			index = Integer.parseInt(number.trim());

			this.number = number.trim();
		}
		
		return this;
	}

	/**
	 * @return the index
	 */
	@JsonIgnore
	public int getIndex() {
		return index;
	}

	/**
	 * @return the quantity
	 */
	public long getQuantity() {
		return quantity;
	}
	
	public Counter increase() {
		this.quantity++;
		
		return this;
	}
	
	public Counter decrease() {
		this.quantity--;
		
		return this;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Counter)) {
			return false;
		}
		Counter other = (Counter) obj;
		if (index != other.index) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Counter [index=" + index + ", number=" + number + ", quantity=" + quantity + "]";
	}
}