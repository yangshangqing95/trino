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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static io.trino.sql.analyzer.lineage.LineageNode.NodeType;

public class LineageCollector
{
    private final LineageDAG dag =  new LineageDAG();
    // because there are UUID, so even for same columns the collection can still be set
    // like c1 -> c2, c1 -> c3
    // though there are two c1 but the node are two different nodes
    private final Map<LineageNode, Set<LineageNode>> outgoingDependencies = new HashMap<>();

    public LineageNode buildNode(
            NodeType nodeType,
            Optional<String> catalog,
            Optional<String> schema,
            Optional<String> table,
            Optional<String> column,
            Optional<String> expression,
            boolean isPhysicalTable)
    {

        return new LineageNode(
                UUID.randomUUID().toString(),
                nodeType,
                catalog,
                schema,
                table,
                column,
                expression,
                isPhysicalTable);
    }

    public void addDependency(LineageNode from, LineageNode to)
    {
        outgoingDependencies.computeIfAbsent(from, k -> new HashSet<>()).add(to);
    }

    // todo: @sqyang
    public LineageDAG generateDAG()
    {
        return dag;
    }
}
