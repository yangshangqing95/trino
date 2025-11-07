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

import io.airlift.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.trino.sql.analyzer.lineage.LineageNode.VIRTUAL_SOURCE;
import static io.trino.sql.analyzer.lineage.LineageNode.VIRTUAL_TARGET;

public class LineageDAG
{
    private static final Logger LOG = Logger.get(LineageDAG.class);

    private final Map<String, LineageNode> nodes = new HashMap<>();
    private final List<LineageEdge> edges = new ArrayList<>();
    private final Map<String, List<LineageEdge>> adjacency = new HashMap<>();
    private final Map<String, List<LineageEdge>> incoming = new HashMap<>(); // todo: Optional?

    public void addNode(LineageNode node)
    {
        if (nodes.containsKey(node.id())) {
            throw new IllegalArgumentException("Duplicate node id " + node.id() + node);
        }
        nodes.put(node.id(), node);
        adjacency.put(node.id(), new ArrayList<>());
    }

    public void addEdge(LineageEdge edge)
    {
        LineageNode from = edge.from().orElse(null);
        LineageNode to = edge.to().orElse(null);
        if (from != null && to != null) {
            LOG.warn("Invalid edge: both from and to are null");
            return;
        }
        if (from == null) {
            from = VIRTUAL_SOURCE;
        }
        if (to == null) {
            to = VIRTUAL_TARGET;
        }
        edges.add(new LineageEdge(Optional.of(from), Optional.of(to), edge.expression()));
        adjacency.computeIfAbsent(from.id(), k -> new ArrayList<>()).add(edge);
        incoming.computeIfAbsent(to.id(), k -> new ArrayList<>()).add(edge); // todo: Optional?
    }

    // todo: Implement
    public List<LineageNode> getUpstreamNodes()
    {
        return null;
    }

    // todo: Implement
    public List<LineageNode> getDownstreamNodes()
    {
        return null;
    }

    // todo: Optional?
    public List<LineageNode> getSourceTables()
    {
        return null;
    }

    // todo: Optional?
    public List<LineageNode> getTargetTables()
    {
        return null;
    }

    // todo: Optional?
    private boolean hasIncomingEdges(LineageNode node)
    {
        return incoming.containsKey(node.id());
    }
}
