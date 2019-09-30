package arina.maplab.camel.mapforce;

import org.apache.camel.Exchange;
import org.apache.camel.spi.HeaderFilterStrategy;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class HeadersStrategy implements HeaderFilterStrategy
{
	private Set<Pattern> includeInHeaders = new HashSet<>();
	private Set<Pattern> excludeInHeaders = new HashSet<>();
	private Set<Pattern> includeOutHeaders = new HashSet<>();
	private Set<Pattern> excludeOutHeaders = new HashSet<>();

	public void setIncludeInHeaders(Set<String> values)
	{
		if(excludeInHeaders.size() > 0)
            throw new IllegalArgumentException("excludeInHeaders already specified!");

		values.forEach(v -> includeInHeaders.add(Pattern.compile(v)));
	}

	public void setExcludeInHeaders(Set<String> values)
	{
		if(includeInHeaders.size() > 0)
			throw new IllegalArgumentException("includeInHeaders already specified!");

		values.forEach(v -> excludeInHeaders.add(Pattern.compile(v)));
	}

	public void setIncludeOutHeaders(Set<String> values)
	{
		if(excludeOutHeaders.size() > 0)
			throw new IllegalArgumentException("excludeOutHeaders already specified!");

		values.forEach(v -> includeOutHeaders.add(Pattern.compile(v)));
	}

	public void setExcludeOutHeaders(Set<String> values)
	{
		if(includeOutHeaders.size() > 0)
			throw new IllegalArgumentException("includeOutHeaders already specified!");

		values.forEach(v -> excludeOutHeaders.add(Pattern.compile(v)));
	}


	@Override
	public boolean applyFilterToCamelHeaders(String headerName, Object headerValue, Exchange exchange)
	{
		return doFiltering(Direction.OUT, headerName, headerValue, exchange);
	}

	@Override
	public boolean applyFilterToExternalHeaders(String headerName, Object headerValue, Exchange exchange)
	{
		return doFiltering(Direction.IN, headerName, headerValue, exchange);
	}

	private boolean doFiltering(Direction direction, String headerName, Object headerValue, Exchange exchange)
	{
		if(headerName == null)
			return true;

		Set<Pattern> headers = null;
		Boolean result = null;

		if (Direction.OUT == direction)
		{
			if(includeOutHeaders.size() > 0)
			{
				headers = includeOutHeaders;
				result = false;
			}
			else if(excludeOutHeaders.size() > 0)
			{
				headers = excludeOutHeaders;
				result = true;
			}
		}
		else if (Direction.IN == direction)
		{
			if(includeInHeaders.size() > 0)
			{
				headers = includeInHeaders;
				result = false;
			}
			else if(excludeInHeaders.size() > 0)
			{
				headers = excludeInHeaders;
				result = true;
			}
		}

		if(headers == null)
			return false;

		for(Pattern pattern : headers)
		{
			if(pattern.matcher(headerName).matches())
				return result;
		}

		return ! result;
	}
}
