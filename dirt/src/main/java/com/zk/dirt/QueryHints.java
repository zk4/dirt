package com.zk.dirt;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

interface QueryHints extends Iterable<Map.Entry<String, Object>> {

	/**
	 * Creates and returns a new {@link QueryHints} instance including {@link javax.persistence.EntityGraph}.
	 *
	 * @param em must not be {@literal null}.
	 * @return new instance of {@link QueryHints}.
	 */
	QueryHints withFetchGraphs(EntityManager em);

	/**
	 * Creates and returns a new {@link QueryHints} instance that will contain only those hints applicable for count
	 * queries.
	 *
	 * @return new instance of {@link QueryHints}.
	 * @since 2.2
	 */
	QueryHints forCounts();

	/**
	 * Get the query hints as a {@link Map}.
	 *
	 * @return never {@literal null}.
	 */
	Map<String, Object> asMap();

	/**
	 * Null object implementation of {@link QueryHints}.
	 *
	 * @author Oliver Gierke
	 * @since 2.0
	 */
	static enum NoHints implements QueryHints {

		INSTANCE;

		/*
		 * (non-Javadoc)
		 * @see org.springframework.data.jpa.repository.support.QueryHints#asMap()
		 */
		@Override
		public Map<String, Object> asMap() {
			return Collections.emptyMap();
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Iterable#iterator()
		 */
		@Override
		public Iterator<Map.Entry<String, Object>> iterator() {
			return Collections.emptyIterator();
		}

		/*
		 * (non-Javadoc)
		 * @see org.springframework.data.jpa.repository.support.QueryHints#withFetchGraphs(javax.persistence.EntityManager)
		 */
		@Override
		public QueryHints withFetchGraphs(EntityManager em) {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see org.springframework.data.jpa.repository.support.QueryHints#forCounts(javax.persistence.EntityManager)
		 */
		@Override
		public QueryHints forCounts() {
			return this;
		}
	}
}