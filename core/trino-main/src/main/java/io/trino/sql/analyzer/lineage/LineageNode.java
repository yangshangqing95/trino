/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.sql.analyzer.lineage;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

import static io.trino.sql.analyzer.lineage.LineageNode.NodeType.VIRTUAL;
import static java.util.Objects.requireNonNull;

public record LineageNode(
        String id,
        NodeType nodeType,
        Optional<String> catalogName,
        Optional<String> schemaName,
        Optional<String> tableName,
        Optional<String> columnName,
        Optional<String> expression,
        boolean isPhysicalTable)
{
    public static final LineageNode VIRTUAL_SOURCE = new LineageNode(
            "virtual_source",
            VIRTUAL,
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            false);
    public static final LineageNode VIRTUAL_TARGET = new LineageNode(
            "virtual_target",
            VIRTUAL,
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            false);

    public LineageNode(
            String id,
            NodeType nodeType,
            Optional<String> catalogName,
            Optional<String> schemaName,
            Optional<String> tableName,
            Optional<String> columnName,
            Optional<String> expression,
            boolean isPhysicalTable) {
        this.id = requireNonNull(id, "id is null");
        this.nodeType = requireNonNull(nodeType, "nodeType is null");
        this.catalogName = requireNonNull(catalogName, "catalogName is null");
        this.schemaName = requireNonNull(schemaName, "schemaName is null");
        this.tableName = requireNonNull(tableName, "tableName is null");
        this.columnName = requireNonNull(columnName, "columnName is null");
        this.expression = requireNonNull(expression, "expression is null");
        this.isPhysicalTable = isPhysicalTable;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LineageNode that = (LineageNode) o;
        return isPhysicalTable == that.isPhysicalTable &&
                Objects.equals(id, that.id) &&
                nodeType == that.nodeType &&
                Objects.equals(catalogName, that.catalogName) &&
                Objects.equals(schemaName, that.schemaName) &&
                Objects.equals(tableName, that.tableName) &&
                Objects.equals(columnName, that.columnName) &&
                Objects.equals(expression, that.expression);
    }

    @Override
    public @NotNull String toString() {
        return "LineageNode{" +
                "id='" + id + '\'' +
                ", nodeType=" + nodeType +
                ", catalogName=" + catalogName.orElse("null") +
                ", schemaName=" + schemaName.orElse("null") +
                ", tableName=" + tableName.orElse("null") +
                ", columnName=" + columnName.orElse("null") +
                ", expression=" + expression.orElse("null") +
                ", isPhysicalTable=" + isPhysicalTable +
                '}';
    }

    public enum NodeType
    {
        TABLE,
        COLUMN,
        INTERMEDIATE,
        CONSTANT,
        VIRTUAL
    }
}
