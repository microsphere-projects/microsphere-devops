/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.nacos.client.common.model;

import java.util.List;

/**
 * The {@link Model} {@link Class} for page
 *
 * @param <E> the type of element
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class Page<E> implements Model {

    private static final long serialVersionUID = 5930271718830258627L;

    private final int pageNumber;

    private final int pageSize;

    private final int totalPages;

    private final int totalElements;

    private final List<E> elements;

    public Page(int pageNumber, int pageSize, int totalElements, List<E> elements) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = elements.size() == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) pageSize);
        this.totalElements = totalElements;
        this.elements = elements;
    }

    /**
     * Get the number of the current {@link Page}. Is always non-negative.
     *
     * @return the number of the current {@link Page}.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Get the size of the {@link Page}.
     *
     * @return the size of the {@link Page}.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Get the number of total pages.
     *
     * @return the number of total pages
     */
    public int getTotalPages() {
        return this.totalPages;
    }

    /**
     * Get the total amount of elements.
     *
     * @return the total amount of elements
     */
    public int getTotalElements() {
        return totalElements;
    }

    /**
     * Get the elements of current {@link Page}
     *
     * @return non-null
     */
    public List<E> getElements() {
        return elements;
    }

    /**
     * Get the number of elements currently on this {@link Page}
     *
     * @return the number of elements currently on this {@link Page}
     */
    public int getNumberOfElements() {
        return elements.size();
    }

    /**
     * Test whether the current {@link Page} is the first one.
     *
     * @return if the current {@link Page} is the first one, return <code>true</code>, otherwise <code>false</code>
     */
    public boolean isFirst() {
        return pageNumber == 0;
    }

    /**
     * Test whether the current {@link Page} is the last one.
     *
     * @return if the current {@link Page} is the last one, return <code>true</code>, otherwise <code>false</code>
     */
    public boolean isLast() {
        return pageNumber == totalPages - 1;
    }

    /**
     * Test whether the current {@link Page} has a previous one.
     *
     * @return if the current {@link Page} has a previous one, return <code>true</code>, otherwise <code>false</code>
     */
    public boolean hasPrevious() {
        return pageNumber > 0;
    }

    /**
     * Test whether the current {@link Page} has a next one.
     *
     * @return if the current {@link Page} has a next one, return <code>true</code>, otherwise <code>false</code>
     */
    public boolean hasNext() {
        return pageNumber < totalPages - 1;
    }

    /**
     * Test whether the current {@link Page} is empty.
     *
     * @return if the current {@link Page} is empty, return <code>true</code>, otherwise <code>false</code>
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page<?> page)) return false;

        return pageNumber == page.pageNumber && pageSize == page.pageSize && totalPages == page.totalPages
                && totalElements == page.totalElements && elements.equals(page.elements);
    }

    @Override
    public int hashCode() {
        int result = pageNumber;
        result = 31 * result + pageSize;
        result = 31 * result + totalPages;
        result = 31 * result + totalElements;
        result = 31 * result + elements.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", elements=" + elements +
                '}';
    }
}
