package com.backend.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.core.convert.converter.Converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Response object for a list result with parameters needed to do pagination.<p/>
 * This class intended in order to implement response with all data required for straightforward and backward
 * pagination.<p/>
 * There are two types of straightforward pagination:
 * <ul>
 * <li>cursor type of pagination which can be used in all cases when it's not required to implement granular
 * pagination with choosing a certain page or without backward pagination.</li>
 * <li>offset/limit type of pagination in case if it's necessary to provide granular pagination with choosing certain
 * page</li>
 * </ul>
 * This class overrides {@link #equals(Object)} and {@link #hashCode()} methods, so object of this class can be easily
 * used to compare objects, especially it's helpful for preparing unit tests. <p/>
 * {@link #reverseCursor} intended especially for backward pagination.
 *
 * @param <T> Class type for list items.
 */
@JsonIgnoreProperties("convertedItems")
public class ResponseList<T> implements Serializable {

	private static final long serialVersionUID = 3629349109049130928L;

	/** Response collection of objects. */
	private Collection<T> items;

	/**
	 * Value that specifies whether there are next elements on the server. Values:
	 * <ul>
	 * <li>true - if there are next items;</li>
	 * <li>false / null - if there are no any items more.</li>
	 * </ul>
	 */
	private Boolean hasNext;

	/**
	 * This is a value that identifies the key to the next portion of data. Value generates on the server on first
	 * request and should be provided in next request of next portion of data.<p/>
	 * This value should be used only if hasNext value is true. <br/>
	 * This value can contain any information which the server can pack there.
	 */
	private String cursor;

	/**
	 * Value that specifies the offset along the list of stored data.<p/>
	 * Value cannot be null in most cases.
	 */
	private Integer offset;

	/**
	 * Value that specifies the number of requested items. This value in response could be helpful for efficient
	 * pagination in case when the request was called with default value for this parameter.<p/>
	 */
	private Integer limit;

	/**
	 * Value that specifies number of entities in the storage.<p/>
	 * null - if number of values is impossible to determine, or it's not necessary according to logic. But client can
	 * analyze hasNext parameter. <br/>
	 * In case if it's necessary to do strict pagination with number of pages, with switching between pages, etc. then
	 * that function can implement that functionality.
	 */
	private Integer total;

	/**
	 * This value is for providing reverse cursor required for backward pagination.
	 * In most cases this value makes sense only if it's returned in initial request, for example to provide an ability
	 * for retrieving new items created after initial (first) request, i.e. backward from 0 page<br/>
	 * In some cases this value can be returned in all responses, not only in initial one, if business logic suppose
	 * that, in order to provide reverse pagination from every position, let's say from 4th or 5th pages etc.
	 */
	private String reverseCursor;

	/**
	 * Default constructor for deserialization purposes.
	 */
	protected ResponseList() {
	}

	/**
	 * Constructor on base of items only. hasNext and total are defined accordingly.
	 *
	 * @param items collection of items
	 */
	public ResponseList(final Collection<T> items) {
		this.items = items;
		this.hasNext = false;
		this.total = items.size();
	}

	/**
	 * Constructor on base of items using converter. Refer to {@link ResponseList#ResponseList(java.util.Collection)}
	 *
	 * @param items     collection of items to be converted
	 * @param converter converter to apply
	 * @param <S>       source class type of items
	 */
	public <S> ResponseList(final Collection<S> items, final Converter<S, T> converter) {
		this(getConvertedItems(items, converter));
	}

	/**
	 * Constructor on base of items and cursor. hasNext is applied accordingly.
	 *
	 * @param items  collection of items
	 * @param cursor cursor
	 */
	public ResponseList(final Collection<T> items, final String cursor) {
		this.items = items;
		this.hasNext = cursor != null && !cursor.isEmpty();
		this.cursor = cursor;
	}

	/**
	 * Constructor on base of items and cursor using converter. Refer to
	 * {@link ResponseList#ResponseList(java.util.Collection, String)}
	 *
	 * @param items     collection of items to be converted
	 * @param cursor    cursor
	 * @param converter converter to apply
	 * @param <S>       source class type of items
	 */
	public <S> ResponseList(final Collection<S> items, final String cursor, final Converter<S, T> converter) {
		this(getConvertedItems(items, converter), cursor);
	}

	/**
	 * Constructor on base of offset/limit type of pagination.
	 *
	 * @param items   collection of items
	 * @param hasNext flag which says whether there are more items could be requested
	 * @param offset  offset from first item in collection
	 * @param limit   number of requested items came from original request
	 */
	public ResponseList(final Collection<T> items, final boolean hasNext, final Integer offset, final Integer limit) {
		this.items = items;
		this.hasNext = hasNext;
		this.offset = offset;
		this.limit = limit;
	}

	/**
	 * Constructor on base of offset/limit/total. <br/>
	 * The value of <b>hasNext</b> will be <b>true</b> if <i>offset + limit &lt; total</i>.
	 *
	 * @param items		collection of items
	 * @param offset	offset from first item in collection
	 * @param limit		number of requested items came from original request
	 * @param total		number of all entities in the storage
	 */
	public ResponseList(final Collection<T> items, final Integer offset, final Integer limit, final Integer total) {
		this.items = items;
		this.offset = offset;
		this.limit = limit;
		this.total = total;
		int offsetValue = offset != null ? offset.intValue() : 0;
		int limitValue = limit != null ? limit.intValue() : items.size();
		this.hasNext = offsetValue + limitValue < total;
	}
	
	/**
	 * Refer to {@link ResponseList#ResponseList(java.util.Collection, boolean, Integer,
	 * Integer)}. Only difference that
	 * converter is applied to items.
	 *
	 * @param items     collection of items
	 * @param hasNext   flag which says whether there are more items could be requested
	 * @param offset    offset from first item in collection
	 * @param limit     number of requested items came from original request
	 * @param converter converter to apply
	 * @param <S>       source class type of items
	 */
    public <S> ResponseList(final Collection<S> items, final boolean hasNext, final Integer offset, final Integer limit,
            final Converter<S, T> converter) {
        this(getConvertedItems(items, converter), hasNext, offset, limit);
	}

	public Collection<T> getItems() {
		return items;
	}

	@JsonIgnore
	private static <S, T> List<T> getConvertedItems(final Collection<S> items, final Converter<S, T> converter) {
		List<T> convertedItems = new ArrayList<>(items.size());
		for (S item : items) {
			convertedItems.add(converter.convert(item));
		}
		return convertedItems;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public String getCursor() {
		return cursor;
	}

	public Integer getOffset() {
		return offset;
	}

	public ResponseList<T> withLimit(final Integer limit) {
		this.limit = limit;
		return this;
	}

	public Integer getLimit() {
		return limit;
	}

	public ResponseList<T> withTotal(final Integer total) {
		this.total = total;
		return this;
	}

	public Integer getTotal() {
		return total;
	}

	public String getReverseCursor() {
		return reverseCursor;
	}

	public ResponseList<T> withReverseCursor(final String reverseCursor) {
		this.reverseCursor = reverseCursor;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ResponseList that = (ResponseList) o;

		if (cursor != null ? !cursor.equals(that.cursor) : that.cursor != null) {
			return false;
		}
		if (hasNext != null ? !hasNext.equals(that.hasNext) : that.hasNext != null) {
			return false;
		}
		if (items != null ? !items.equals(that.items) : that.items != null) {
			return false;
		}
		if (limit != null ? !limit.equals(that.limit) : that.limit != null) {
			return false;
		}
		if (offset != null ? !offset.equals(that.offset) : that.offset != null) {
			return false;
		}
		if (reverseCursor != null ? !reverseCursor.equals(that.reverseCursor) : that.reverseCursor != null) {
			return false;
		}
		if (total != null ? !total.equals(that.total) : that.total != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = items != null ? items.hashCode() : 0;
		result = 31 * result + (hasNext != null ? hasNext.hashCode() : 0);
		result = 31 * result + (cursor != null ? cursor.hashCode() : 0);
		result = 31 * result + (offset != null ? offset.hashCode() : 0);
		result = 31 * result + (limit != null ? limit.hashCode() : 0);
		result = 31 * result + (total != null ? total.hashCode() : 0);
		result = 31 * result + (reverseCursor != null ? reverseCursor.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ResponseList{");
		sb.append("items = ").append(items);
		sb.append(", hasNext = ").append(hasNext);
		sb.append(", cursor = '").append(cursor).append('\'');
		sb.append(", offset = ").append(offset);
		sb.append(", limit = ").append(limit);
		sb.append(", total = ").append(total);
		sb.append(", reverseCursor = '").append(reverseCursor).append('\'');
		sb.append('}');
		return sb.toString();
	}
}