/*
 * DISCLAIMER
 * Copyright 2019 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 *
 */

package com.arangodb.graphql.context;

import com.arangodb.graphql.schema.ArangoEdgeDirective;
import graphql.schema.SelectedField;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a group of filters
 * @author Colin Findlay
 * @see ArangoFilter
 */
public class ArangoFilterGroup {

    private List<ArangoFilter> filters;

    private int depth;

    public ArangoFilterGroup(List<ArangoFilter> filters, int depth) {
        this.filters = filters;
        this.depth = depth;
    }

    public ArangoFilterGroup(SelectedField field, int depth){
        ArangoEdgeDirective edgeDirective = new ArangoEdgeDirective(field.getFieldDefinition());
        this.filters = field.getArguments().entrySet()
                .stream()
                .map(e -> new ArangoFilter(e.getKey(), e.getValue(), edgeDirective.getCollection()))
                .collect(Collectors.toList());
        this.depth = depth;
    }

    public ArangoFilterGroup(Map<String, Object> arguments, int depth) {
        this.filters = arguments.entrySet()
                .stream()
                .map(e -> new ArangoFilter(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public List<ArangoFilter> getFilters() {
        return filters;
    }

}
